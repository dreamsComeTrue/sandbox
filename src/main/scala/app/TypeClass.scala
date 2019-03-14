package app

sealed trait ASTElement

final case class LangString(obj: String) extends ASTElement {
  override def toString: String = obj
}

final case class LangNumber(obj: Double) extends ASTElement {
  override def toString: String = obj.toString
}

final case class LangAssignment(obj: (LangString, ASTElement)) extends ASTElement {
  override def toString: String = {
    obj._1.toString + " = " + obj._2.toString
  }
}

case object LangNull extends ASTElement {
  override def toString: String = ""
}

trait CompilerEngine[A] {
  def compile(element: A): ASTElement
}

object CompilerEngine {
  implicit val langStringCompiler: CompilerEngine[String] = (element: String) => LangString(element)
  implicit val langNumberCompiler: CompilerEngine[Double] = (element: Double) => LangNumber(element)
  implicit val langAssignmentCompiler: CompilerEngine[LangAssignment] = (element: LangAssignment) => LangAssignment(element.obj)

  implicit def optionCompiler[A](implicit compiler: CompilerEngine[A]): CompilerEngine[Option[A]] =
    (option: Option[A]) => option match {
      case Some(aValue) => compiler.compile(aValue)
      case None => LangNull
    }
}

object Compiler {
  def compile[A](value: A)(implicit ce: CompilerEngine[A]): ASTElement =
    ce.compile(value)
}

object CompilerInterfaceSyntax {

  implicit class CompilerOps[A](value: A) {
    def compile(implicit ce: CompilerEngine[A]): ASTElement =
      ce.compile(value)
  }

}

object CompilerApp {
  def main(args: Array[String]): Unit = {
    println(Compiler.compile(LangAssignment(LangString("hello"), LangNumber(12))))
    println(Compiler.compile(LangAssignment(LangString("var"), LangString("iable"))))
    println(Compiler.compile(LangAssignment(LangString("not_work"), LangNull))(implicitly[CompilerEngine[LangAssignment]]))

    import CompilerInterfaceSyntax._

    println(LangAssignment(LangString("hello"), LangNumber(12)).compile)

    println(Compiler.compile(Option(LangAssignment(LangString("option"), LangString("Is great")))))
  }
}