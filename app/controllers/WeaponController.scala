package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Weapon
import anorm._

object WeaponController extends Controller {
  
	def newWeapon = Action { implicit request =>
	  weaponForm.bindFromRequest.fold(
	    errors => BadRequest(views.html.weapon(errors)),
	    weapon => {
	      Weapon.create(weapon)
	      Redirect(routes.WeaponController.weapon)
	    }
	  )
	}
	
	val weaponForm = Form(
	    mapping(
	        "id" -> ignored(NotAssigned: Pk[Long]),
			"wType" -> nonEmptyText,
	    	"producer" -> nonEmptyText,
	    	"wModel" -> nonEmptyText,
	    	"status" -> nonEmptyText
	    )(Weapon.apply)(Weapon.unapply)
	)
	
	def viewWeapon = Action {
		Ok(views.html.weaponview(Weapon.all()))
	}

	def weapon = Action {
		Ok(views.html.weapon(weaponForm))
	}
	
	def deleteWeapon(id: Long) = Action {
		Weapon.delete(id)
		Redirect(routes.WeaponController.viewWeapon)
	}
	
	def editWeapon(id: Long) = Action {
		val bindedForm = weaponForm.fill(Weapon.getweapon(id))
		Ok(views.html.weaponedit(bindedForm))
	}
	
	def updateWeapon(id: Long) = Action { implicit request =>
		val weapon = weaponForm.bindFromRequest.get
		Weapon.delete(id)
		Weapon.update(id, weapon)
		Redirect(routes.WeaponController.viewWeapon)
	}
}