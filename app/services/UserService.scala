package services

import domain.User

import scala.collection.mutable.ArrayBuffer

class UserService {
  val jsonService = new JsonService

  def saveUserToJsonFile(listOfUsers: ArrayBuffer[User], newUser: User): Unit ={
    jsonService.addUserToListOfUsers(listOfUsers, newUser)
  }

  def getListOfUsers: ArrayBuffer[User] ={
    jsonService.getListOfUsers(jsonService.getStructureOfJsonFile)
  }

  def getUserByUserId(listOfUsers: ArrayBuffer[User], idFindUser: String): User = {
    for(user <- listOfUsers){
      if(idFindUser == user.getId){
        return user
      }
    }

    null
  }
}
