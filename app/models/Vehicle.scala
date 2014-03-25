package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.api.libs.json.Writes
import play.api.libs.json.Json
import play.api.libs.json.JsValue

case class Vehicle(id: Pk[Long], technumber:String, vType:String, producer:String, vModel:String, stolen:Boolean, wanted:Boolean, ownerID: Option[Long])

object Vehicle{
	val vehicle = {
		get[Pk[Long]]("id") ~ 
		get[String]("technumber") ~ 
		get[String]("vType") ~ 
		get[String]("producer") ~ 
		get[String]("vModel") ~ 
		get[Boolean]("stolen") ~ 
		get[Boolean]("wanted") ~ 
		get[Option[Long]]("ownerID") map {
			case id~technumber~vType~producer~vModel~stolen~wanted~ownerID => Vehicle(id, technumber, vType, producer, vModel, stolen, wanted, ownerID)
		}
	}
	
	implicit val VehicleWrites = new Writes[Vehicle] {
		def writes(vehicle: Vehicle) = Json.obj(
			"technumber" -> vehicle.technumber,
	    	"vType" -> vehicle.vType,
	    	"producer" -> vehicle.producer,
	    	"vModel" -> vehicle.vModel,
	    	"stolen" -> vehicle.stolen,
	    	"wanted" -> vehicle.wanted,
	    	"ownerID" -> vehicle.ownerID
		)
	}
  
	def all(): List[Vehicle] = DB.withConnection { implicit c =>
		SQL("select * from vehicle").as(vehicle *)
	}
	def create(vehicle: Vehicle) {
		DB.withConnection { implicit c =>
			SQL("insert into vehicle (technumber, vType, producer, vModel, stolen, wanted, ownerID) values ({technumber}, {vType}, {producer}, {vModel}, {stolen}, {wanted}, {ownerID})").on(
	    		'technumber -> vehicle.technumber,
	    		'vType -> vehicle.vType,
	    		'producer -> vehicle.producer,
	    		'vModel -> vehicle.vModel,
	    		'stolen -> vehicle.stolen,
	    		'wanted -> vehicle.wanted,
	    		'ownerID -> vehicle.ownerID
			).executeUpdate()
		}
	}
	
	def update(id:Long, vehicle: Vehicle) {
		DB.withConnection { implicit c =>
			SQL("insert into vehicle (id, technumber, vType, producer, vModel, stolen, wanted, ownerID) values ({id}, {technumber}, {vType}, {producer}, {vModel}, {stolen}, {wanted}, {ownerID})").on(
	    		'id -> id,
			    'technumber -> vehicle.technumber,
	    		'vType -> vehicle.vType,
	    		'producer -> vehicle.producer,
	    		'vModel -> vehicle.vModel,
	    		'stolen -> vehicle.stolen,
	    		'wanted -> vehicle.wanted,
	    		'ownerID -> vehicle.ownerID
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
	
	def find(info: JsValue) = {
		val req = Search.makeRequest("vehicle",info)
	
		val result = DB.withConnection { implicit c =>
			SQL(req).as(vehicle *)
		}
		result
	}
}