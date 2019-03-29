package app

object Implicits {
  def reduceLeftTest[B: Numeric](data: Seq[B])(implicit num: Numeric[B]) = {
    data.reduceLeft((a, b) => num.plus(a, b))
  }

  def foldLeftTest[B: Numeric](data: Seq[B])(implicit num: Numeric[B]) = {
    data.foldLeft(num.zero)((a, b) => num.plus(a, b))
  }

  def sumTest[B: Numeric](data: Seq[B])(implicit num: Numeric[B]) = {
    data.sum(num)
  }

  def main(args: Array[String]): Unit = {
    val list = List(1, 2.1, 3.32)

    println(reduceLeftTest(list))
    println(foldLeftTest(list))
    println(sumTest(list))
  }
}
