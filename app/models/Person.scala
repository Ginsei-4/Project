package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.api.libs.json.Writes
import play.api.libs.json.Json
import play.api.libs.json.JsValue

case class Person(id: Pk[Long], name:String, midname:String, lastname:String, passport:String, wanted:Boolean)

object Person{
	val person = {
		get[Pk[Long]]("id") ~ 
		get[String]("name") ~ 
		get[String]("midname") ~ 
		get[String]("lastname") ~ 
		get[String]("passport") ~ 
		get[Boolean]("wanted") map {
			case id~name~midname~lastname~passport~wanted => Person(id, name, midname, lastname, passport, wanted)
		}
	}
	
	implicit val PersonWrites = new Writes[Person] {
		def writes(person: Person) = Json.obj(
			"name" -> person.name,
	    	"midname" -> person.midname,
	    	"lastname" -> person.lastname,
	    	"passport" -> person.passport,
	    	"wanted" -> person.wanted
		)
	}
  
	def all(): List[Person] = DB.withConnection { implicit c =>
		SQL("select * from person").as(person *)
	}
	
	def create(person: Person) {
		DB.withConnection { implicit c =>
			SQL("insert into person (name, midname, lastname, passport, wanted) values ({name}, {midname}, {lastname}, {passport}, {wanted})").on(
	    		'name -> person.name,
	    		'midname -> person.midname,
	    		'lastname -> person.lastname,
	    		'passport -> person.passport,
	    		'wanted -> person.wanted
			).executeUpdate()
		}
	}
	
	def update(id:Long, person: Person) {
		DB.withConnection { implicit c =>
			SQL("insert into person (id, name, midname, lastname, passport, wanted) values ({id}, {name}, {midname}, {lastname}, {passport}, {wanted})").on(
	    		'id -> id,
			    'name -> person.name,
	    		'midname -> person.midname,
	    		'lastname -> person.lastname,
	    		'passport -> person.passport,
	    		'wanted -> person.wanted
			).executeUpdate()
		}
	}
	
	def delete(id: Long) {
		DB.withConnection { implicit c =>
			SQL("delete from person where id = {id}").on(
				'id -> id
			).executeUpdate()
		}
	}
	
	def getpers(id: Long): Person = {
		val list = DB.withConnection { implicit c =>
			SQL("select * from person where id = {id}").on(
				'id -> id
			).as(person *)
		}
		list(0)
	}
	
	def find(info: JsValue) = {
		val req = Search.makeRequest("person",info)
	
		val result = DB.withConnection { implicit c =>
			SQL(req).as(person *)
		}
		Json.toJson(result)
	}
}