package lectures.part5typesystem

object TypeMembers extends App{

  class Animal
  class Dog extends Animal
  class Cat extends Animal

  class AnimalCollection {
    type AnimalType //abstract type member
    type BoundedAnimalType <: Animal
    type SuperBoundedAnimalType >: Dog <: Animal
    type AnimalC = Cat
  }

  val ac = new AnimalCollection
  val dog: ac.AnimalType = ???

  val pup: ac.SuperBoundedAnimalType = new Dog
  val cat: ac.AnimalC = new Cat

  type CatAlias = Cat
  val anotherCat: CatAlias = new Cat

  //alternative to generics
  trait MyList {
    type T
    def add(elem: T): MyList
  }

  class NonEmptyList(value: Int) extends MyList {
    type T = Int
    def add(elem: Int): MyList = ???
  }

  // .type
  type CatsType = cat.type
  //val newCat: CatsType = cat

  trait MList {
    type A
    def head: A
    def tail: MList
  }

  trait MyTypes {
    type A <: Number
  }

/*  class CustomList(hd: String, tl: CustomList) extends MList with MyTypes {
    override type A = String
    def head = hd
    def tail = tl
  }*/

 /* class CustomIntList(hd: Int, tl: CustomIntList) extends MList with MyTypes {
    type A = Int
    def head = hd
    def tail = tl
  }*/



}
