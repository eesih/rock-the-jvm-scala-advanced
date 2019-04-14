package lectures.part5typesystem

object Reflection extends App {

  // reflection + macros + quasiquotes => METAPROGRAMMING

  case class Person(name: String) {
    def sayMyName(): Unit = println(s"Hi, may name is $name")
  }

  // 0 import
  import scala.reflect.runtime.{universe => ru}

  // 1 MIRROR
  val m = ru.runtimeMirror(getClass.getClassLoader)
  // 2 create a class object
  val clazz = m.staticClass("lectures.part5typesystem.Reflection.Person") // creating a class object by name

  // 3 create a reflected mirror
  val cm = m.reflectClass(clazz)

  //4 get the constructor
  val constructor = clazz.primaryConstructor.asMethod
  // 5 reflect the constructor
  val constructorMirror = cm.reflectConstructor(constructor)
  // 6 invoke the constructor
  val instance = constructorMirror.apply("John")

  println(instance)
}
