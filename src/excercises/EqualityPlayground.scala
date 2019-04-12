package excercises

import lectures.part4implicits.TypeClasses.{User}

object EqualityPlayground extends App {

  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean = equalizer(a, b)
  }

  object UserNameEquality extends Equal[User] {
    override def apply(first: User, second:User): Boolean = first.name == second.name
  }

  implicit object UserAgeEquality extends Equal[User] {
    override def apply(first: User, second:User): Boolean = first.age == second.age
  }

  val john = User("John", 32, "john@email.com")
  val eero = User("Eero", 39, "eero@email.com")

  println(Equal(john, eero))


  implicit class EqualImpl[T](value: T) {
    def ===(anotherValue: T)(implicit equalizer: Equal[T]): Boolean = equalizer(value, anotherValue)
    def !==(anotherValue: T)(implicit equalizer: Equal[T]): Boolean = !equalizer(value, anotherValue)
  }

  println(eero === john)


}
