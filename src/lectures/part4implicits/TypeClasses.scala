package lectures.part4implicits

import java.util.Date

object TypeClasses extends App {

  trait HTMLWritable {
    def toHTML: String
  }

  case class User(name: String, age: Int, email: String) extends HTMLWritable {
    override def toHTML: String = s"<div>$name ($age years old) < href=$email /></div>"
  }

  val john = User("John", 32, "john@email.com")

  // option 2 - Pattern matching
  object HTMLSerializerPM {
    def serializeToHTML(value: Any) = value match {
      case User(n, a, e) =>
     // case Date =>
      case _ =>
    }
  }

  /*
    1 - lost type safety
    2 - need to modify code every time
    3 - still ONE implementation
   */

  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

  implicit object UserSerializer extends HTMLSerializer[User] {
     def serialize(user: User): String = s"<div>${user.name} (${user.age} years old) <href=${user.email} /></div>"
  }

  println(UserSerializer.serialize(john))

  // 1. We can define serializers for other types

  object DateSerializer extends HTMLSerializer[Date] {
    override def serialize(date: Date): String = s"<div> ${date.toString} </div>"
  }

  // 2. we can define multiple serializers
  object PartialUserSerializer extends HTMLSerializer[User] {
    def serialize(user: User): String = s"<div>${user.name}</div>"
  }


  // part 2

  object HTMLSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String =
      serializer.serialize(value)

    def apply[T](implicit serializer: HTMLSerializer[T]) = serializer
  }

  implicit object IntSerializer extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div>${value}</div>"
  }

 // println(HTMLSerializer.serialize(42))
  println(HTMLSerializer.serialize(john))

  //access to the entire type class interface
  println(HTMLSerializer[User].serialize(john))


  // part 3
  implicit class HTMLEnrichment[T](value: T) {
    def toHTML2(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(value)
  }

  println(john.toHTML2) //new HTMLEnrichment[User](john).toHTML(UserSerializer)
  /*
    - extend to new types
    - choose implementation
   */
  println(2.toHTML2)
  println(john.toHTML2(PartialUserSerializer))

  /*
    - type class itself HTMLSerializer[T] {..}
    - type class instances (some of which are implicit) UserSerializer, IntSerializer
    - conversion with implicit classes HTMLEnrichment
   */

  // context bound

  def htmlBoilerplate[T](content: T)(implicit serializer: HTMLSerializer[T]): String =
    s"<html><body> ${content.toHTML2(serializer)} </body></html>"

  def htmlSugar[T : HTMLSerializer](content: T): String =
    s"<html><body> ${content.toHTML2(implicitly[HTMLSerializer[T]])} </body></html>"

  // implicitly
  case class Permissions(mask: String)
  implicit val defaultPermissions = Permissions("0744")

  val standardPerms = implicitly[Permissions]


}
