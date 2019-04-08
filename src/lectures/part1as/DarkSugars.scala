package lectures.part1as

import scala.util.Try

object DarkSugars extends App {

  //syntax sugar number 1# methods with single parameter

  def singleArgMethod(arg: Int): String = s"$arg little ducks..."

  val description = singleArgMethod {
    //write some complex code
    42
  }


  val aTryInstance = Try {
    throw new RuntimeException
  }

  List(1,2,3).map { x =>
    x + 1
  }

  //syntax sugar #2: single abstract method
  trait Action {
    def act(x: Int) : Int
  }

  val anFunkyInstance: Action = (x) => x + 1 //magic

  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("Hello, Scala")
  })

  val aSweetThread = new Thread(() => println("Hello Scala"))

  abstract class AbstractType {
    def implemented: Int = 23
    def f(a: Int): Unit
  }

  val anAbstractInstance: AbstractType =  (a: Int) => println("sweet")

  //syntax sugar #3: the :: and #:: methods are special (infix methods)
  val prependedList = 2 :: List(3, 4)
  //2.::(List(3,4)) ==> List(3, 4)::(2)
  //scala spec: last char decides associativity of method
  1 :: 2 :: 3 :: List(4, 5)
  List(4, 5).::(3).::(2).::(1)

  class MyStream[Int] {
    def -->:(v: Int): MyStream[Int] = {
      println(v)
      this
    }
  }

  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

  //syntax sugar number # 4 multi-word method naming

  class TeenGirl(name : String) {
    def `and then said`(gossip: String) = println(s"$name said $gossip")
  }

  val lilly = new TeenGirl("Lilly")
  lilly `and then said` "Scala is so sweet!"

  //syntax sugar #5: infix types
  class Composite[A, B]
  val composite: Int Composite String = ???

  class --> [A, B]
  val towards : Int --> String = ???

  //syntax sugar #6: update() is very special, much like apply

  val anArray = Array(1,2,3)

  anArray(2) = 7 //rewritten to anArray.update(2, 7)
  //used in mutable collections
  //remember apply and update

  //syntax sugar #7: setters for mutable containers
  class Mutable {
    private var internalMember: Int = 0
    def member: Int = internalMember //"getter"
    def member_=(value: Int): Unit =
      internalMember = value //"setter"
  }

  val aMutableContainer = new Mutable
  aMutableContainer.member = 42 //rewritten mutableContainer.member_=(42)








}
