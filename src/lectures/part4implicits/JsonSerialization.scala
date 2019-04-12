package lectures.part4implicits

import java.util.Date

object JsonSerialization extends App {

  case class User(name: String, age: Int, email: String)
  case class Post(content: String, createdAt: Date)
  case class Feed(user: User, posts: List[Post])

  sealed trait JSONValue {
    def stringify: String
  }

  final case class JsonString(value: String) extends JSONValue {
    def stringify: String = "\"" + value + "\""
  }

  final case class JsonNumber(value: Int) extends JSONValue {
    def stringify: String = value.toString
  }

  final case class JsonArray(values: List[JSONValue]) extends JSONValue {
     def stringify: String = values.map(_.stringify).mkString("[", ",", "]")
  }

  final case class JsonObject(values: Map[String, JSONValue]) extends JSONValue {
    def stringify: String = values.map {
      case (key, value) =>  "\"" + key +  "\":" + value.stringify
    }.mkString("{", ",", "}")
  }

  val data = JsonObject(Map(
    "user" -> JsonString("Daniel"),
    "posts" -> JsonArray(List(
      JsonString("Scala rocks"),
      JsonNumber(453)
    ))
  ))

  println(data.stringify)

  //type class
  trait JsonConverter[T] {
    def convert(value: T): JSONValue
  }

  implicit class JsonOps[T](value: T) {
    def toJson(implicit converter: JsonConverter[T]): JSONValue = converter.convert(value)
  }

  implicit object StringConverter extends JsonConverter[String] {
     def convert(value: String): JSONValue = JsonString(value)
  }

  implicit object NumberConverter extends JsonConverter[Int] {
    def convert(value: Int): JSONValue = JsonNumber(value)
  }

  implicit object UserConverter extends JsonConverter[User] {
    def convert(user: User): JSONValue = JsonObject(Map(
      "name" -> JsonString(user.name),
      "age" -> JsonNumber(user.age),
      "email" -> JsonString(user.email)
    ))
  }

  implicit object PostConverter extends JsonConverter[Post] {
     def convert(post: Post): JSONValue = JsonObject(Map(
       "content" -> JsonString(post.content),
       "createdAt" -> JsonString(post.createdAt.toString)
     ))
  }

  implicit object FeedConverter extends JsonConverter[Feed] {
     def convert(feed: Feed): JSONValue = JsonObject(Map(
       "user" -> feed.user.toJson,
       "posts" -> JsonArray(feed.posts.map(_.toJson))
     ))
  }

  val now = new Date(System.currentTimeMillis())
  val john = User("John", 32, "john@email.com")
  val feed = Feed(john, List(
    Post("hello", now),
    Post("look at the puppy", now)
  ))

  println(feed.toJson.stringify)

}
