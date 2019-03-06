package app.streams

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import akka.stream.{ActorMaterializer, ThrottleMode}

import scala.concurrent.duration.DurationInt

object LinearTopologyAkkaStreams {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val mat = ActorMaterializer()

    import system.dispatcher

    val flow =
      Flow[String]
        .async
        .zip(Source.fromIterator(() => Iterator.from(0)))
        .map {
          case (s, n) =>
            s"$n. $s -> ${Thread.currentThread().getName}\n"
        }
        .throttle(1, 1.seconds, 1, ThrottleMode.shaping)


    Source
      .repeat("Hello World")
      .take(8)
      .via(flow)
      .toMat(Sink.foreach(print))(Keep.right)
      .run()
      .onComplete(_ => system.terminate())
  }

}
