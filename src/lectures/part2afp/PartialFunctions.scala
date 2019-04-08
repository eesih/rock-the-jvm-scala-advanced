package lectures.part2afp

object PartialFunctions extends App {

  val aFunction = (x: Int) => x + 1 //Function1[Int ,Int] === Int => Int

  val aFussyFunction = (x: Int) =>
    if (x ==1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new RuntimeException

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 3 => 999
  }
  //{1,2,5} => Int

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 3 => 999
  } //partial function value

  println(aPartialFunction(2))

  // PF utilities
  println(aPartialFunction.isDefinedAt(67))

  // lift
  val lifted = aPartialFunction.lift //Int  => Option[Int]
  println(lifted(2))
  println(lifted(55))

  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 55
  }
  println(pfChain(45))

  //PF extend normal function
  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  //HOFs accept partial functions as well
  val aMappedList = List(1,2,3).map {
    case 1 => 42
    case 2 => 66
    case 3 => 44
  }

  println(aMappedList)

  /*
    Note: PF can only have 1 parameter type
   */


  val aManualFussyFunction = new PartialFunction[Int, Int] {
    override def apply(x: Int): Int = x match {
      case 1 => 42
      case 2 => 66
      case 3 => 44
    }

    override def isDefinedAt(x: Int): Boolean = x == 1 || x == 2 || x == 3
  }

  val chatBot: PartialFunction[String, String] = {
    case "hello" => "Hi, my name is HAL9000"
    case "goodbye" => "Once you start..."
    case "call mom" => "Unable to find your..."
  }


  scala.io.Source.stdin.getLines().map(chatBot).foreach(println)
}
