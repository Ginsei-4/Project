package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Vehicle
import anorm._
import play.api.libs.json.Json

object VehicleController extends Controller {
  
	def newVehicle = Action { implicit request =>
	  vehicleForm.bindFromRequest.fold(
	    errors => BadRequest(views.html.vehicle(errors)),
	    vehicle => {
	      Vehicle.create(vehicle)
	      Redirect(routes.VehicleController.vehicle)
	    }
	  )
	}
	
	val vehicleForm = Form(
	    mapping(
	        "id" -> ignored(NotAssigned: Pk[Long]),
			"technumber" -> nonEmptyText,
	    	"vType" -> nonEmptyText,
	    	"producer" -> nonEmptyText,
	    	"vModel" -> nonEmptyText,
	    	"stolen" -> boolean,
	    	"wanted" -> boolean,
	    	"ownerID" -> optional(longNumber)
	    )(Vehicle.apply)(Vehicle.unapply)
	)
	
	def viewVehicle = Action {
		Ok(views.html.vehicleview(Vehicle.all()))
	}

	def vehicle = Action {
		Ok(views.html.vehicle(vehicleForm))
	}
	
	def deleteVehicle(id: Long) = Action {
		Vehicle.delete(id)
		Redirect(routes.VehicleController.viewVehicle)
	}
	
	def editVehicle(id: Long) = Action {
		val bindedForm = vehicleForm.fill(Vehicle.getvehicle(id))
		Ok(views.html.vehicleedit(bindedForm))
	}
	
	def updateVehicle(id: Long) = Action { implicit request =>
		val vehicle = vehicleForm.bindFromRequest.get
		Vehicle.delete(id)
		Vehicle.update(id, vehicle)
		Redirect(routes.VehicleController.viewVehicle)
	}
	
	def findVehicle(info: String) = Action {
		Ok(Json.toJson(Vehicle.find(Json.parse(info))))
	}
}