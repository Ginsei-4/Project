package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Person
import anorm._

object PersonController extends Controller {
	def newPerson = Action { implicit request =>
	  persForm.bindFromRequest.fold(
	    errors => BadRequest(views.html.person(errors)),
	    person => {
	      Person.create(person)
	      Redirect(routes.PersonController.person)
	    }
	  )
	}
	
	val persForm = Form(
	    mapping(
	        "id" -> ignored(NotAssigned: Pk[Long]),
			"name" -> nonEmptyText,
	    	"midname" -> nonEmptyText,
	    	"lastname" -> nonEmptyText,
	    	"passport" -> nonEmptyText,
	    	"wanted" -> boolean
	    )(Person.apply)(Person.unapply)
	)
	
	def viewPerson = Action {
		Ok(views.html.persview(Person.all()))
	}

	def person = Action {
		Ok(views.html.person(persForm))
	}
	
	def deletePerson(id: Long) = Action {
		Person.delete(id)
		Redirect(routes.PersonController.viewPerson)
	}
}