package lectures.part4implicits

object ImplicitsIntro extends App {

  val pair = "Eero" -> "555"
  val intPair = 1 -> 2

  case class Person(name: String) {
    def greet = s"Hi, my name is $name"
  }

  implicit def fromStringToPerson(str: String): Person = Person(str)

  println("Peter".greet) //fromStringToPerson("Peter").greet

  class A {
    def greet:Int = 42
  }

  def increment(i: Int)(implicit amount: Int) = i + amount
  implicit val defaultValue = 19

  increment(2)


}
