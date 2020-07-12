package services

import java.io.{File, PrintWriter}

import domain.User

import play.api.libs.json._
import play.api.libs.functional.syntax._

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class JsonService {
  private val jsonFilePath = "public/json/example.json"

  implicit val userWrites: Writes[User] = (user: User) => Json.obj(
    "id" -> user.getId,
    "phoneNumber" -> user.getPhoneNumber
  )

  implicit val userReads: Reads[User] = (
    (JsPath \\ "id").read[String] and
      (JsPath \\ "phoneNumber").read[String])(User.apply _)

  def getStructureOfJsonFile: String = {
    var structureOfJsonFile = ""
    val sourceJsonFile = Source.fromFile(jsonFilePath)

    for(lineFromJson <- sourceJsonFile.getLines()){
      structureOfJsonFile += lineFromJson
    }

    sourceJsonFile.close()

    structureOfJsonFile
  }

  def getListOfUsers(structureOfJsonFile: String): ArrayBuffer[User] = {
    var listOfUsers = new ArrayBuffer[User]()
    val json = Json.parse(structureOfJsonFile)

    for(user <- json.as[Seq[User]]) {
      listOfUsers += user
    }

    listOfUsers
  }

  def addUserToListOfUsers(listOfUsers: ArrayBuffer[User], newUser: User): Unit = {
    if(newUser != null){
      listOfUsers += newUser

      saveListOfUsers(listOfUsers)
    }
  }

  private def saveListOfUsers(listOfUsers: ArrayBuffer[User]): Unit = {
    val writerToJsonFile = new PrintWriter(new File(jsonFilePath))

    writerToJsonFile.write(
      Json.prettyPrint(
        transformListToJsonArray(listOfUsers)
      )
    )

    writerToJsonFile.close()
  }

  private def transformListToJsonArray(listOfUsers: ArrayBuffer[User]) : JsArray = {
    val arrayOfJsonValues = new ArrayBuffer[JsValue]

    for (user <- arrayOfJsonValues){
      arrayOfJsonValues.append(Json.toJson(user))
    }

    new JsArray(arrayOfJsonValues)
  }
}
