package lectures.part4implicits

object OrganizingImplicits extends App {

  implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  println(List(1,2,5,4,3).sorted)

  //scala.PreDef

  /*
    Implicit (used as implicit parameters):
    - val / var
    - object
    - accessor methods = defs with no parentheses
   */

  case class Person(name: String, age: Int)

  object Person {
    //   implicit val personOrdering: Ordering[Person] = Ordering.fromLessThan(_.name < _.name)
  }

  //implicit val ageOrdering: Ordering[Person] = Ordering.fromLessThan(_.age < _.age)
  val persons = List(
    Person("Steve", 30),
    Person("Amy", 22),
    Person("John", 66)
  )

  import AlphabeticOrdering._
  println(persons.sorted)

  /*
    Implicit scope
    - normal scope = LOCAL SCOPE
    - imported scope
    - companions of all types involved in the method signature
      - List
      - Ordering
      - all the types involved (A or any supertype)

    def sorted[B >: A](implicit ord : scala.math.Ordering[B]) : Repr
   */

  object AlphabeticOrdering {
    implicit val personOrdering: Ordering[Person] = Ordering.fromLessThan(_.name < _.name)
  }

  object AgeOrdering {
    implicit val ageOrdering: Ordering[Person] = Ordering.fromLessThan(_.age < _.age)
  }


  case class Purchase(nUnits: Int, unitPrice: Double)

  object Purchase {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a,b) => (a.nUnits * a.unitPrice) > (b.nUnits * b.unitPrice))
  }

  object UnitCount {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a,b) => a.nUnits > b.nUnits)
  }

  object UnitPrice {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a,b) => a.unitPrice > b.unitPrice)
  }

  val purchases = List(
    Purchase(3, 23),
    Purchase(1, 50),
    Purchase(11, 13)
  )
  import UnitPrice._
  println(purchases.sorted)

}
