package lectures.part2afp

object CurriesAndPAF extends App {

  //curried functions
  val superAdded: (Int => Int => Int) = x => y => x + y

  val add3 = superAdded(3) //Int => Int = y => 3 + y

  println(add3(5))
  println(superAdded(3)(5)) //curried function

  //METHOD!
  def curriedAdder(x: Int)(y: Int): Int = x + y //curried function

  val add4: Int => Int = curriedAdder(4)

 // println(add4)
  //lifting = ETA-EXPANSION

  //function != methods (JVM limitation)
  def inc(x: Int) = x + 1

  List(1,2,3).map(inc) //ETA-expansions >> compiler rewrites the (x => inc(x)

  //Partial function applications
  val add5 = curriedAdder(5) _

  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y


  val add7: Int => Int = curriedAddMethod(7) _
  val add7_2: Int => Int = simpleAddMethod(7, _)
  val add7_3: Int => Int = simpleAddFunction(7, _)

  println(add7(2))
  println(add7_2(2))
  println(add7_3(2))

  val first = (x: Int) => simpleAddFunction(7, x)
  val second = simpleAddFunction.curried(7)
  val third = simpleAddFunction(7, _: Int)
  val fourth = curriedAddMethod(7) _ //PAF
  val fifth = curriedAddMethod(7) (_) // alternative syntax PAF
  val sixth = simpleAddMethod(7, _: Int) //alternative syntax for turning methods into function values // y => simpleAddMethod(7, y)

  //underscores are powerful
  def concatenator(a: String, b: String, c: String) = a + b + c

  val insertName =  concatenator("Hello, I'm ", _: String, ", how are you") //x: String => concatenator(hello, x, howareyou)
  println(insertName("Eero"))

  val fillInTheBlanks = concatenator("Hello", _: String, _: String) //(x,y) => concatenator(hello, x, y)
  println(fillInTheBlanks("Eero ", " Scala is awesome"))


  def formatter(s: String)(number: Double) = s.format(number)
  val curriedFormatted = formatter("%4.2f") _
  val curriedFormatted2 = formatter("%8.6f") _
  val curriedFormatted3 = formatter("%14.12f") _

  List(Math.PI, Math.E, 23.4).map(curriedFormatted).foreach(println)
  List(Math.PI, Math.E, 23.4).map(curriedFormatted2).foreach(println)
  List(Math.PI, Math.E, 23.4).map(curriedFormatted3).foreach(println)

  def byName(n: => Int) = n + 1
  def byFunction(f: () => Int) = f() + 1

  def method: Int = 42
  def parenMethod(): Int = 42

  println(byName(42))
  println(byName(method))
  println(byName(parenMethod()))
 // byName(() => 42) //not ok
  byName((() => 32)())
  //println(byName(parenMethod _) not ok

  //println(byFunction(42))
  //println(byFunction(method)) //compiler does NOT DO the ETA-expansion
  println(byFunction(parenMethod)) //compiler does the ETA-expansion
 // byFunction(() => 42))
  println(byFunction(parenMethod _)) //also works
}
