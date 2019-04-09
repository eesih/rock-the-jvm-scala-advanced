package lectures.part2afp

object LazyEvaluation extends App {

  //lazy DELAYS the evaluation of values
  //lazy val x: Int = throw new RuntimeException

  lazy val x = {
    println("hello")
    42
  }

  println(x)
  println(x)


  //examples of implication:
  //side effects
  def sideEffectCondition: Boolean = {
    println("boo")
    true
  }
  def simpleCondition: Boolean = false

  lazy val lazyCondition = sideEffectCondition

  println( if (simpleCondition && lazyCondition) "yes" else "no")

  //in conjunction with call by name
  def byNameMethod(n: => Int) : Int = {
    //CALL BY NEED
    lazy val t = n // only evaluated once
    t + t + t + 1
  }
  def retrieveMagicValue = {
    println("waiting")
    Thread.sleep(1000)
    42
  }

  println(byNameMethod(retrieveMagicValue))

  // filtering with lazy vals
  def lessThan30(n: Int): Boolean = {
    println(s"$n is less than 30?")
    n < 30
  }

  def greaterThan20(n: Int): Boolean = {
    println(s"$n is greater than 30?")
    n > 20
  }

  val numbers = List(1, 25, 40, 5, 23)

  val lt30 = numbers.filter(lessThan30)
  val gt20 = lt30.filter(greaterThan20)

  ///println(gt20)

  lazy val lazyLt30 = numbers.withFilter(lessThan30) //(withFilter) lazy vals under the hood
  lazy val lazyGt20 = lazyLt30.withFilter(greaterThan20)
  println

  lazyGt20.foreach(println)

  // for-comprehensions use withFilter with guard
  for {
    a <- List(1,2,3) if (a % 2 == 0) //use lazy vals
  } yield a +1

  List(1,2,3).withFilter(_ % 2 == 0).map(_ + 1)

  abstract class MyStream[+A] {
    def isEmpty: Boolean
    def head : A
    def tail: MyStream[A]

    def #::[B >: A](element: B): MyStream[B] //prepend operator
    def ++[B >: A](anotherStream: MyStream[B]): MyStream[B]

    def foreach(f: A => Unit)
    def map[B](f: A => B) : MyStream[B]
    def flatMap[B](f: A => MyStream[B]): MyStream[B]
    def filter(predicate: A => Boolean): MyStream[B]

    def take(n: Int): MyStream[A] //first n elements out of this stream
    def takeAsList(n:Int): List[A]

  }

  object MyStream {
    def from[A](start: A)(generator: A => A): MyStream[A] = ???
  }


}
