package services

import java.io.{File, PrintWriter}

import domain.Phone

import play.api.libs.json._
import play.api.libs.functional.syntax._

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class JsonService {
  private val jsonFilePath = "public/json/example.json"

  implicit val userWrites: Writes[Phone] = (phone: Phone) => Json.obj(
    "id" -> phone.getId,
    "phoneNumber" -> phone.getPhoneNumber
  )

  implicit val userReads: Reads[Phone] = (
    (JsPath \\ "id").read[String] and
      (JsPath \\ "phoneNumber").read[String])(Phone.apply _)

  def getStructureOfJsonFile: String = {
    var structureOfJsonFile = ""
    val sourceJsonFile = Source.fromFile(jsonFilePath)

    for(lineFromJson <- sourceJsonFile.getLines()){
      structureOfJsonFile += lineFromJson
    }

    sourceJsonFile.close()

    structureOfJsonFile
  }

  def getListOfPhones(structureOfJsonFile: String): ArrayBuffer[Phone] = {
    var listOfPhones = new ArrayBuffer[Phone]()
    val json = Json.parse(structureOfJsonFile)

    for(user <- json.as[Seq[Phone]]) {
      listOfPhones += user
    }

    listOfPhones
  }

  def addUserToListOfUsers(listOfPhones: ArrayBuffer[Phone], newPhone: Phone): Unit = {
    if(newPhone != null){
      listOfPhones += newPhone

      saveListOfPhones(listOfPhones)
    }
  }

  private def saveListOfPhones(listOfPhones: ArrayBuffer[Phone]): Unit = {
    val writerToJsonFile = new PrintWriter(new File(jsonFilePath))

    writerToJsonFile.write(
      Json.prettyPrint(
        transformListToJsonArray(listOfPhones)
      )
    )

    writerToJsonFile.close()
  }

  private def transformListToJsonArray(listOfPhones: ArrayBuffer[Phone]) : JsArray = {
    val arrayOfJsonValues = new ArrayBuffer[JsValue]

    for (user <- listOfPhones){
      arrayOfJsonValues.append(Json.toJson(user))
    }

    new JsArray(arrayOfJsonValues)
  }
}
