package app

object Implicits {
  def reduceLeftTest[B: Numeric](data: Seq[B])(implicit num: Numeric[B]) = {
    data.reduceLeft((a, b) => num.plus(a, b))
  }

  def reduceLeftTestOwnFunc[B: Numeric](data: Seq[B])(op: (B, B) => B)(implicit num: Numeric[B]) = {
    data.reduceLeft((a, b) => op(a, b))
  }

  def foldLeftTest[B: Numeric](data: Seq[B])(implicit num: Numeric[B]) = {
    data.foldLeft(num.zero)((a, b) => {
      println(s"Folding: $a, $b")

      num.plus(a, b)
    })
  }

  def sumTest[B: Numeric](data: Seq[B])(implicit num: Numeric[B]) = {
    data.sum(num)
  }

  def main(args: Array[String]): Unit = {
    val list = List(1, 2.1, 3.32)

    println(reduceLeftTest(list))
    println(reduceLeftTestOwnFunc(list)((x, y) => x * y))
    println(foldLeftTest(list))
    println(sumTest(list))
  }
}
