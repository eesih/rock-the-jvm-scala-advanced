package lectures.part5typesystem

object RockingInheritance extends App {

  //convenience
  trait Writer[T] {
    def write(value: T): Unit
  }

  trait Closable {
    def close(status: Int): Unit
  }

  trait GenericStream[T] {
    def foreach(f: T => Unit): Unit
  }

  def processStream[T](stream: GenericStream[T] with Writer[T] with Closable): Unit = {
    stream.foreach(println)
    stream.close(10)
  }

  //diamond problem

  trait Animal { def name: String}
  trait Lion extends Animal {
    override def name: String = "LION"
  }
  trait Tiger extends Animal {
    override def name: String = "TIGER"
  }
  class Mutant extends Lion with Tiger {
   // override def name: String = "ALIEN"
  }

  println((new Mutant).name) //LAST OVERRIDE GETS PICKED

  trait Cold {
    def print = println("cold")
  }

  trait Green extends Cold {
    override def print: Unit = {
      println("green")
      super.print
    }
  }

  trait Blue extends Cold {
    override def print: Unit = {
      println("blue")
      super.print
    }
  }

  class Red {
    def print = println("red")
  }

  class White extends Red with Blue with Green {
    override def print: Unit = {
      println("white")
      super.print
    }
  }

  val color = new White

  color.print

}
