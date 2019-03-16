package app.cats

import cats.Show
import cats.instances.all._

object Meow {
  case class User(name: String, age: Int)

  implicit val showUser = Show.show((a: User) => s"Name: ${a.name}, age: ${a.age}")

  def showTest[T](param: T)(implicit show: Show[T]) = {
    val showStr = Show.apply[T]

    println(showStr.show(param))
  }

  def main(args: Array[String]): Unit = {
    showTest("hello")
    showTest(123)
    showTest(User("squall", 31))
  }


}
