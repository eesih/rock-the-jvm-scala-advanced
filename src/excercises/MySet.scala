package excercises

trait MySet[A] extends (A => Boolean){

  def contains(elem: A): Boolean
  def +(elem: A): MySet[A]
  def ++(anotherSet: MySet[A]): MySet[A]
  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B]
  def filter(p: A => Boolean): MySet[A]
  def foreach(f: A => Unit): Unit
  def apply(elem: A): Boolean = contains(elem)
}

class EmptySet[A] extends MySet[A] {
  def contains(elem: A): Boolean = false
  def +(elem: A): MySet[A] = new NonEmptySet(elem, this)
  def ++(anotherSet: MySet[A]): MySet[A] = anotherSet
  def map[B](f: A => B): MySet[B] = new EmptySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]
  def filter(p: A => Boolean): MySet[A] = this
  def foreach(f: A => Unit): Unit = ()
}

class NonEmptySet[A](head: A, tail: MySet[A]) extends MySet[A] {

  def contains(elem: A): Boolean =
    head == elem || tail.contains(elem)

  def +(elem: A): MySet[A] =
    if (this contains elem) this
    else new NonEmptySet(elem, this)

  def ++(anotherSet: MySet[A]): MySet[A]
    = tail ++ anotherSet + head

  def map[B](f: A => B): MySet[B] =
    (tail map f) + f(head)

  def flatMap[B](f: A => MySet[B]): MySet[B] =
      (tail flatMap f) ++ f(head)

  def filter(p: A => Boolean): MySet[A] = {
    val filteredTail = tail.filter(p)
    if (p(head)) filteredTail + head
    else filteredTail
  }

  def foreach(f: A => Unit): Unit = {
    f(head)
    tail foreach f
  }

}
object MySet {
  def apply[A](values: A*): MySet[A] = {
    def buildSet(valSeq: Seq[A], acc: MySet[A]):MySet[A] =
      if (valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc + valSeq.head)

    buildSet(values.toSeq, new EmptySet[A])
  }
}

object MySetPlaygroud extends App {
  val s = MySet(1, 2, 3, 4)
  s foreach println
  s + 5 ++ MySet(6, 7) + 3 map (_ * 10) filter (_ % 2 == 0) foreach println
}