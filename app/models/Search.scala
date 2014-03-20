package models

import play.api.libs.json._
import play.api.db._
import anorm._
import anorm.SqlParser._

object Search {
	//функция для составления SQL запроса
	def makeRequest(tableName: String, info:JsValue) = {
	  "select * from " + tableName + makeRequest2(info.as[Map[String, String]])
	  
	}
	
	def makeRequest2 (info: Map[String, String]): String = {
	  val parameters =  info.iterator
	  makeRequest3(false, parameters)
	}
	
	def makeRequest3(changes: Boolean, parameters: Iterator[(String, String)]): String = {
	  if (!parameters.hasNext){
	    ""
	  }
	  else{
	    val cur = parameters.next
	    if (cur._2 == "Null"){
	      makeRequest3(changes, parameters)
	    }
	    else{
	      if(changes){
	        " and " + cur._1 + " = \'" + cur._2 + "\'" + makeRequest3(changes, parameters)
	      }
	      else{
	        " where " + cur._1 + " = \'" + cur._2 + "\'" + makeRequest3(true, parameters)
	      }
	    }
	  }
	}
}