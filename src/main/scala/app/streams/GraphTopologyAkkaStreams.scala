package app.streams

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, Merge, RunnableGraph, Sink, Source}
import akka.stream.{ActorMaterializer, ClosedShape}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object GraphTopologyAkkaStreams {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val mat = ActorMaterializer()
    implicit val executor: ExecutionContext = system.dispatcher

    val host = "0.0.0.0"
    val port = 9000

    def route = path("hello") {
      get {
        complete {
          val graph = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
            import akka.stream.scaladsl.GraphDSL.Implicits._

            val in = Source(1 to 10)
            val out = Sink.foreach(println)

            val bcast = builder.add(Broadcast[Int](2))
            val merge = builder.add(Merge[Int](2))

            val f1, f2, f3, f4 = Flow[Int].map(_ + 10)

            in ~> f1 ~> bcast ~> f2 ~> merge ~> f3 ~> out
            bcast ~> f4 ~> merge
            ClosedShape
          })

          graph.run()

          "Hello, World!"
        }
      }
    }

    Http()
      .bindAndHandle(route, host, port)
      .onComplete {
        case Success(value) => println(value.localAddress.getHostName)
        case Failure(exception) => println(exception.getMessage)
      }
  }

}
