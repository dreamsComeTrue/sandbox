import akka.actor._

class Worker extends Actor {
  override def receive = {
    case num: Int => println(s"Got number: $num")
    case any@_ => println(s"Got: $any")
  }  
}

object AkkaDemo {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("AkkaDemoSystem")
 
    val actor1 = system.actorOf(Props[Worker], name = "Worker1")
    
    actor1 ! 22 
    
    system.terminate
  }
}
