package app

import java.util.concurrent.atomic.AtomicInteger

import akka.actor._

import scala.util.Random

case class StartMsg(msg: String)

case class ResultOK(msg: String)

case class ResultBad(msg: String)

class Worker extends Actor {
  override def receive: PartialFunction[Any, Unit] = {
    case num: Int if num > 50 => sender ! ResultOK(s"OK: $num")
    case any@_ => sender ! ResultBad(s"BAD: $any")
  }
}

class Manager(worker: ActorRef) extends Actor {
  val counter = new AtomicInteger(0)

  override def receive: PartialFunction[Any, Unit] = {
    case StartMsg(msg) if msg == "start" => {
      counter.incrementAndGet()
      worker ! Random.nextInt(100)
    }

    case obj@ResultOK(_) => {
      println(obj)
      if (counter.get() >= 5) {
        context.system.terminate()
      }
    }

    case obj@ResultBad(_) => {
      println(obj)
      if (counter.get() >= 5) {
        context.system.terminate()
      }
    }
  }
}

object AkkaDemo {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("AkkaDemoSystem")

    val worker = system.actorOf(Props[Worker], name = "Worker1")
    val manager = system.actorOf(Props(classOf[Manager], worker), name = "Manager1")

    for (i <- 0 to 5) {
      manager ! StartMsg("start")
    }
  }
}
