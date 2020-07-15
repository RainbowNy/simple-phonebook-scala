package controllers

import domain.Phone
import javax.inject.{Inject, Singleton}
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import services.PhoneService

import scala.collection.mutable.ArrayBuffer

@Singleton
class PhonebookController @Inject()(cc: ControllerComponents) extends AbstractController(cc){
  private val phoneService = new PhoneService

  private val phoneForm = Form(
    mapping(
      "id" -> text,
      "phoneNumber" -> text
    )(Phone.apply)(Phone.unapply)
  )

  def phonebookPage(): Action[AnyContent] = Action {
    Ok(views.html.Phonebook(phoneService.getListOfPhones))
  }

  def addPhonePage(): Action[AnyContent] = Action {
    Ok(views.html.AddPhone(null))
  }

  def findPhoneById(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val postValues = request.body.asFormUrlEncoded
    val listOfPhone = new ArrayBuffer[Phone]

    postValues.map {args =>
      val phoneId = args("id").head

      listOfPhone += phoneService.getPhoneByPhoneId(phoneService.getListOfPhones, phoneId)
      Ok(views.html.Phonebook(listOfPhone))
    }.get
  }

  def addPhone(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val phoneData = phoneForm.bindFromRequest.get

    if(phoneService.getPhoneByPhoneId(phoneService.getListOfPhones, phoneData.getId) != null){
      Ok(views.html.AddPhone("Phone already exist!"))
    }else {
      phoneService.savePhoneToJsonFile(phoneService.getListOfPhones, Phone(phoneData.getId, phoneData.getPhoneNumber))

      Redirect(routes.PhonebookController.phonebookPage())
    }
  }
}
