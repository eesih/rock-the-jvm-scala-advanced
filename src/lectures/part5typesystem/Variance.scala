package lectures.part5typesystem

object Variance extends App {

  trait Animal
  class Dog extends Animal
  class Cat extends Animal
  class Crocodile extends Animal


  class cage[T]
  //yes - covariance
  class CCage[+T]

  val ccage: CCage[Animal] = new CCage[Cat]


  //no invariance
  class ICage[T]

 // val icage: ICage[Animal] = new ICage[Cat]

  //hell no - opposite
  class XCage[-T]
  val xcage: XCage[Cat] = new XCage[Animal]


  class InvariantCage[T](val animal: T) //invariant

  //covariant positions
  class CovariantCage[+T](val animal: T) // covariant position

  //class ContravariantCage[-T](val animal: T)
 // Error:(32, 35) contravariant type T occurs in covariant position in type => T of value animal
  //val catCage: XCage[Cat] = new XCage[Animal](new Crocodile)

  //class CovariantVariableCage[+T](var animal: T) // types of vars are in CONTRAVARIANT POSITION
  //val ccage: CCage[Animal] = new CCAge[Cat](new Cat)
  //ccage.animal = new Crocodile
  //

  //class ContravariantVariableCage[-T](var animal: T) //Also in COVARIANT POSITION
  //val catCage: XCage[Cat] = new XCage[Animal](new Crocodile)

  class InvariantVariableCage[T](animal: T) //OK

  /*
  trait AnotherCovariantCage[+T] {
    def addAnimal(animal: T) // CONTRAVARIANT POSITION
  }
    val ccage: CCage[Animal] = new CCage[Dog]
    ccage.add(new Cat)
  */
  class AnotherContravariantCage[-T] {
    def addAnimal(animal: T) = true // ok
  }
  val acc: AnotherContravariantCage[Cat] = new AnotherContravariantCage[Animal]
  acc.addAnimal(new Cat)
  class Kitty extends Cat
  acc.addAnimal(new Kitty)

  class MyList[+A] {
    def add[B >: A](element: B): MyList[B] = new MyList[B] // widening the type
  }

  val emptyList = new MyList[Kitty]
  val animals = emptyList.add(new Kitty)
  val moreAnimals = animals.add(new Cat)
  val evenMoreAnimals = moreAnimals.add(new Dog)

  // method arguments are in contravariant position

  //return types
  class PetShop[-T] {
   // def get(isItAPuppy: Boolean): T // method return are in covariant position
    /*
     val catShop = new PetShop[Animal] {
        def get(isIt): Animal = new Cat
     }

     val dogShop: PetShop[Dog] = catShop
     dogShop.get() // Returns EVIL CAT!!!
     */
    def get[E <: T](isItAPuppy: Boolean, defaultAnimal: E): E = defaultAnimal
  }

  val shop: PetShop[Dog] = new PetShop[Animal]
  //val evilCat = shop.get(true, new Cat)
  class TerraNova extends Dog
  val bigFurry = shop.get(true, new TerraNova) //ok

  /*
    BIG RULE
        - method arguments are in CONTRAVARIANT POSITION
        - return types are in COVARIANT POSITION

   */

  class Vehicle
  class Bike extends Vehicle
  class Car extends Vehicle

  class IList[T]

  class InvariantParking[T](things: List[T]) {
    def park(vehicle: T): Unit = println("parking")
    def impound(vehicles: List[T]): Unit = println("impound")
    def checkVehicles(param: String): List[T] = List()
    def flatMap[E >: T](f: T => CovariantParking[E]): ContravariantParking[E] = new ContravariantParking[E](List())
  }

  class CovariantParking[+T](things: List[T]) {
    def park[E >: T](vehicle: E): Unit = println("parking")
    def impound[E >: T](vehicles: List[E]): Unit = println("impound")
    def checkVehicles(param: String): List[T] = List()

    def flatMap[E >: T](f: T => CovariantParking[E]): ContravariantParking[E] = new ContravariantParking[E](List())

  }

  class ContravariantParking[-T](things: List[T]) {
    def park(vehicle: T): Unit = println("parking")
    def impound(vehicles: List[T]): Unit = println("impound")
    def checkVehicles[E <: T](param: String): List[E] = List()
  }

  class Animal2
  class CatAnimal extends Animal2
  class Cat2 extends CatAnimal
  class Dog2 extends Animal2


  val cats: List[Animal2] = List[Cat2](new Cat2, new Cat2)
  val newCats =  new CatAnimal +: cats
  val allCats = newCats :+ new Dog2

  allCats.foreach(println)

  import collection.mutable._
  val mutableCats: ArrayBuffer[Cat2] = ArrayBuffer[Cat2]()
  mutableCats.update(1, new Cat2)
//  mutableCats.update(1, new Dog2)
 // mutableCats.update(1, new CatAnimal)

}
