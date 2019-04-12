package lectures.part4implicits

import scala.concurrent.Future

object MagnetPattern extends App {


  class P2PRequest
  class P2PResponse
  class Serializer

  trait Actor {
    def receive(statusCode: Int): Int
    def receive(request: P2PRequest):Int
    def receive(response: P2PResponse): Int
   // def receive[T : Serializer](message: T): Int
    //def receive[T : Serializer](message: T, statusCode: Int): Int
    def receive(future: Future[P2PRequest]): Int
    //lots of overloads
  }

  trait MessageMagnet[Result] {
    def apply(): Result
  }

  def receive[R](magnet: MessageMagnet[R]): R = magnet()

  implicit class FromP2PRequest(request: P2PRequest) extends MessageMagnet[Int] {
     def apply(): Int = {
       println("handling p2p request")
       42
     }
  }

  implicit class FromP2PResponse(response: P2PResponse) extends MessageMagnet[Int] {
    def apply(): Int = {
      println("handling p2p response")
      55
    }
  }

  receive(new P2PResponse)
  receive(new P2PRequest)

}
