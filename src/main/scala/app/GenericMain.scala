import scala.collection._
import scala.reflect.ClassTag

object GenericMain extends App {
  def func1[B](data: Seq[B], f: (B, B) => B): B = {
    val operation = (a: B, b: B) => f(a, b)
    data.reduce(operation)
  }

  def func2[B <: Int](data: Seq[B]): Int = {
    data.reduce((a: Int, b: Int) => a + b)
  }

  val list = List[Int](1, 2, 3)

  println(func1[Int](list, (a, b) => a + b))
  println(func2(list))
  println
}
