package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Vehicle
import anorm._

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
	    	"wanted" -> boolean
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
}