package lectures.part5typesystem

object PathDependentTypes extends App {

  class Outer {
    class Inner
    object Inner
    type InnerType

    def print(i: Inner) = println(i)
    def printGeneral(i: Outer#Inner) = println(i)
  }

  def aMethod: Int = {
    class HelperClass

    2
  }

  val o = new Outer
  val inner = new o.Inner //o.Inner is a TYPE

  val oo = new Outer
  val otherInner = oo.Inner

  o.print(inner)
  //oo.print(inner)

  // path dependent type

  //Outer#Inner
  o.printGeneral(inner)
  oo.printGeneral(inner)

  trait ItemLike {
    type Key
  }

  trait Item[K] extends ItemLike {
    type Key = K
  }
  trait IntItem extends Item[Int]
  trait StringItem extends Item[String]

  def get[ItemType <: ItemLike](key: ItemType#Key): ItemType = ???

  get[IntItem](42)
  get[StringItem]("hello")
  //get[IntItem]("scala")



}
