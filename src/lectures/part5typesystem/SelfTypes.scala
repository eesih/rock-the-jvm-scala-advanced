package lectures.part5typesystem

object SelfTypes extends App {

  trait InstrumentList {
       def play(): Unit
  }

  trait Singer { self: InstrumentList => //SELF TYPE whoever implements singer to implement InstrumentList
    def sing(): Unit
  }

  class LeadSinger extends Singer with InstrumentList {
    override def play(): Unit = ???
    override def sing(): Unit = ???
  }

  /*class Vocalist extends Singer {

  }*/

  val jamesHetfield = new Singer with InstrumentList {
    override def play(): Unit = ???
    override def sing(): Unit = ???
  }

  class Guitarist extends InstrumentList {
    override def play(): Unit = println("guitar solo")
  }

  val ericClapton = new Guitarist with Singer {
    override def sing(): Unit = ???
  }
  // Cake pattern > dependency injection



}
