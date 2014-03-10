package models

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

case class Weapon(id: Pk[Long], wType:String, producer:String, wModel:String, status:String)

object Weapon{
	val weapon = {
		get[Pk[Long]]("id") ~ 
		get[String]("wType") ~ 
		get[String]("producer") ~ 
		get[String]("wModel") ~ 
		get[String]("status") map {
			case id~wType~producer~wModel~status => Weapon(id, wType, producer, wModel, status)
		}
	}
  
	def all(): List[Weapon] = DB.withConnection { implicit c =>
		SQL("select * from weapon").as(weapon *)
	}
	def create(weapon: Weapon) {
		DB.withConnection { implicit c =>
			SQL("insert into weapon (wType, producer, wModel, status) values ({wType}, {producer}, {wModel}, {status})").on(
	    		'wType -> weapon.wType,
	    		'producer -> weapon.producer,
	    		'wModel -> weapon.wModel,
	    		'status -> weapon.status
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
}