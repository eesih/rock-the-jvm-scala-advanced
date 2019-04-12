package lectures.part4implicits

object PimpMyLibrary extends App {

  //2.isPrime

  implicit class RichInt(value: Int) {
    def isEven: Boolean = value % 2 == 0
    def sqrt: Double = Math.sqrt(value)
    def times(f: () => Unit): Unit = {
      def timesAux(n: Int): Unit =
        if(n <= 0) ()
        else {
          f()
          timesAux(n - 1)
        }
      timesAux(value)
      }

    def *[T](list: List[T]): List[T] = {
      def concanate(n: Int): List[T] =
        if (n <= 0) List()
        else concanate(n - 1) ++ list

      concanate(value)
    }

  }

  println(new RichInt(42).sqrt)

  println(42.isEven) //new RichInt(42).isEven()

  //type enrichment

  (1 to 10)

  import scala.concurrent.duration._
  3.seconds

  4.times(println)

  implicit class RichString(value: String) {
    def asInt: Int = value.toInt
    def encrypt(cypherDistance: Int): String = value.map(c => (c  + cypherDistance).asInstanceOf[Char])
  }

  println("Eero".encrypt(1))

  println(5 * List(1,2))




}
