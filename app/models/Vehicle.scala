package models

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

case class Vehicle(id: Pk[Long], technumber:String, vType:String, producer:String, vModel:String, stolen:Boolean, wanted:Boolean)

object Vehicle{
	val vehicle = {
		get[Pk[Long]]("id") ~ 
		get[String]("technumber") ~ 
		get[String]("vType") ~ 
		get[String]("producer") ~ 
		get[String]("vModel") ~ 
		get[Boolean]("stolen") ~ 
		get[Boolean]("wanted") map {
			case id~technumber~vType~producer~vModel~stolen~wanted => Vehicle(id, technumber, vType, producer, vModel, stolen, wanted)
		}
	}
  
	def all(): List[Vehicle] = DB.withConnection { implicit c =>
		SQL("select * from vehicle").as(vehicle *)
	}
	def create(vehicle: Vehicle) {
		DB.withConnection { implicit c =>
			SQL("insert into vehicle (technumber, vType, producer, vModel, stolen, wanted) values ({technumber}, {vType}, {producer}, {vModel}, {stolen}, {wanted})").on(
	    		'technumber -> vehicle.technumber,
	    		'vType -> vehicle.vType,
	    		'producer -> vehicle.producer,
	    		'vModel -> vehicle.vModel,
	    		'stolen -> vehicle.stolen,
	    		'wanted -> vehicle.wanted
			).executeUpdate()
		}
	}
	
	def update(id:Long, vehicle: Vehicle) {
		DB.withConnection { implicit c =>
			SQL("insert into vehicle (id, technumber, vType, producer, vModel, stolen, wanted) values ({id}, {technumber}, {vType}, {producer}, {vModel}, {stolen}, {wanted})").on(
	    		'id -> id,
			    'technumber -> vehicle.technumber,
	    		'vType -> vehicle.vType,
	    		'producer -> vehicle.producer,
	    		'vModel -> vehicle.vModel,
	    		'stolen -> vehicle.stolen,
	    		'wanted -> vehicle.wanted
			).executeUpdate()
		}
	}
	
	def delete(id: Long) {
		DB.withConnection { implicit c =>
			SQL("delete from vehicle where id = {id}").on(
				'id -> id
			).executeUpdate()
		}
	}
	
	def getvehicle(id: Long): Vehicle = {
		val list = DB.withConnection { implicit c =>
			SQL("select * from vehicle where id = {id}").on(
				'id -> id
			).as(vehicle *)
		}
		list(0)
	}
}