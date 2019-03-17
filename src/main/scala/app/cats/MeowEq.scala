package app.cats

import cats.Eq
import cats.instances.all._
import cats.syntax.eq._
import cats.syntax.option._

object MeowEq {

  case class User(name: String, age: Int)

  implicit val eqUser = Eq.instance((a: User, b: User) => a.name == b.name && a.age == b.age)

  def eqTest[T](x: T, y: T)(implicit eq: Eq[T]) = {
    eq.eqv(x, y)
  }

  def main(args: Array[String]): Unit = {
    assert(eqTest("hello", "hello"))
    //  123 === "hello" - Not compile
    assert(!(123 === 245))
    assert(eqTest(User("squall", 31), User("squall", 31)))
    assert(!eqTest(User("squall", 11), User("squall", 61)))
    assert(User("squall", 11) =!= User("squall", 61))

    assert(Option(User("squall", 12)) =!= Option.empty[User])
    assert(User("squall", 12).some =!= none[User])
  }
}
