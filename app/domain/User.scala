package domain

class User(newId: String, newPhoneNumber: String) {
  private var Id = newId
  private var phoneNumber = newPhoneNumber

  def setId(newId: String): Unit ={
    Id = newId
  }

  def getId: String ={
    Id
  }

  def setPhoneNumber(newPhoneNumber: String): Unit ={
    phoneNumber = newPhoneNumber
  }

  def getPhoneNumber: String ={
    phoneNumber
  }
}
