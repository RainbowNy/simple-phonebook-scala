package services

import domain.Phone

import scala.collection.mutable.ArrayBuffer

class PhoneService {
  val jsonService = new JsonService

  def savePhoneToJsonFile(listOfPhones: ArrayBuffer[Phone], newPhone: Phone): Unit ={
    jsonService.addUserToListOfUsers(listOfPhones, newPhone)
  }

  def getListOfPhones: ArrayBuffer[Phone] ={
    jsonService.getListOfPhones(jsonService.getStructureOfJsonFile)
  }

  def getPhoneByPhoneId(listOfPhones: ArrayBuffer[Phone], idFindPhone: String): Phone = {
    for(phone <- listOfPhones){
      if(idFindPhone == phone.getId){
        return phone
      }
    }

    null
  }
}
