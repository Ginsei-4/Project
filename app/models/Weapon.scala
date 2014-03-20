package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.api.libs.json.Writes
import play.api.libs.json.Json

case class Weapon(id: Pk[Long], wType:String, producer:String, wModel:String, status:String, ownerID: Option[Long])

object Weapon{
	val weapon = {
		get[Pk[Long]]("id") ~ 
		get[String]("wType") ~ 
		get[String]("producer") ~ 
		get[String]("wModel") ~ 
		get[String]("status") ~ 
		get[Option[Long]]("ownerID") map {
			case id~wType~producer~wModel~status~ownerID => Weapon(id, wType, producer, wModel, status, ownerID)
		}
	}
	
	implicit val WeaponWrites = new Writes[Weapon] {
		def writes(weapon: Weapon) = Json.obj(
			"wType" -> weapon.wType,
	    	"producer" -> weapon.producer,
	    	"wModel" -> weapon.wModel,
	    	"status" -> weapon.status,
	    	"ownerID" -> weapon.ownerID
		)
	}
  
	def all(): List[Weapon] = DB.withConnection { implicit c =>
		SQL("select * from weapon").as(weapon *)
	}
	def create(weapon: Weapon) {
		DB.withConnection { implicit c =>
			SQL("insert into weapon (wType, producer, wModel, status, ownerID) values ({wType}, {producer}, {wModel}, {status}, {ownerID})").on(
	    		'wType -> weapon.wType,
	    		'producer -> weapon.producer,
	    		'wModel -> weapon.wModel,
	    		'status -> weapon.status,
	    		'ownerID -> weapon.ownerID
			).executeUpdate()
		}
	}
	
	def update(id:Long, weapon: Weapon) {
		DB.withConnection { implicit c =>
			SQL("insert into weapon (id, wType, producer, wModel, status, ownerID) values ({id}, {wType}, {producer}, {wModel}, {status}, {ownerID})").on(
	    		'id -> id,
			    'wType -> weapon.wType,
	    		'producer -> weapon.producer,
	    		'wModel -> weapon.wModel,
	    		'status -> weapon.status,
	    		'ownerID -> weapon.ownerID
			).executeUpdate()
		}
	}
	
	def delete(id: Long) {
		DB.withConnection { implicit c =>
			SQL("delete from weapon where id = {id}").on(
				'id -> id
			).executeUpdate()
		}
	}
	
	def getweapon(id: Long): Weapon = {
		val list = DB.withConnection { implicit c =>
			SQL("select * from weapon where id = {id}").on(
				'id -> id
			).as(weapon *)
		}
		list(0)
	}
}