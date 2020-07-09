package controllers

import java.util

import domain.User
import javax.inject.{Inject, Singleton}
import play.api.mvc._

@Singleton
class PhonebookController @Inject()(cc: ControllerComponents) extends AbstractController(cc){

  def phonebookPage(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val listOfUser = new util.ArrayList[User]()
    var user = new User("1", "+7")
    var user2 = new User("2", "+20")

    listOfUser.add(user)
    listOfUser.add(user2)

    Ok(views.html.Phonebook(listOfUser))
  }
}
