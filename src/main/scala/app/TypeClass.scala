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

object CompilerEngineInstances {
  implicit val langStringCompiler: CompilerEngine[String] = (element: String) => LangString(element)
  implicit val langNumberCompiler: CompilerEngine[Double] = (element: Double) => LangNumber(element)
  implicit val langAssignmentCompiler: CompilerEngine[LangAssignment] = (element: LangAssignment) => LangAssignment(element.obj)
}

object Compiler {
  def compile[A](value: A)(implicit ce: CompilerEngine[A]): ASTElement =
    ce.compile(value)
}

object CompilerApp {

  import CompilerEngineInstances._

  def main(args: Array[String]): Unit = {
    println(Compiler.compile(LangAssignment(LangString("hello"), LangNumber(12))))
    println(Compiler.compile(LangAssignment(LangString("var"), LangString("iable"))))
    println(Compiler.compile(LangAssignment(LangString("not_work"), LangNull)))
  }
}