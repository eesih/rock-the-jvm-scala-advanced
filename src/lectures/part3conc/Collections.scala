package lectures.part3conc

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.atomic.AtomicReference

import scala.collection.parallel.ForkJoinTaskSupport
import scala.collection.parallel.immutable.ParVector

object Collections extends App {

  // 1. parallel collections
  val parallelList = List(1,2,3).par

  val parVector = ParVector[Int](1,2,3,4,5)

  def measure[T](operation: => T) : Long = {
    val time = System.currentTimeMillis()
    operation
    System.currentTimeMillis() - time
  }


  val list = (1 to 10000).toList
  val serialTime = measure {
    list.map(_ + 1)
  }

  val parallelTime = measure {
    list.par.map(_ + 1)
  }

  println("Serial time " + serialTime)
  println("Parallel time " + parallelTime)

  /*
    Map reduce model
      - split the elements into chunks - Splitter
      - operation
      - recombine - Combiner
   */

  //map, flatMap, filter, foreach, reduce, fold

  //fold, reduce with non-associative operators
  println(List(1,2,3).reduce(_ - _))
  println(List(1,2,3).par.reduce(_ - _))

  //synchronization
  var sum = 0
  List(1,2,3).par.foreach(x => sum += x) //race conditions
  println(s"SUM: $sum")

  //configuring
  parVector.tasksupport = new ForkJoinTaskSupport(new ForkJoinPool(2))
  /*
    alternatives
    - ThreadpoolTaskSupport - deprecated
    - ExecutionContextSupport(EC)
   */

  // 2. atomic ops and references
  val atomic = new AtomicReference[Int](2)

  val currentValue = atomic.get() //thread-safe read
  atomic.set(4) // thread-safe write

  atomic.getAndSet(5) //thread safe compo

  atomic.compareAndSet(38, 55)
  // if the value is 38, then set the value 55
  // reference equality

  atomic.updateAndGet(_ + 1) //thread safe function run
  atomic.getAndUpdate(_ + 1)

  atomic.accumulateAndGet(12, _ + _) //thread safe accumulation
  atomic.getAndAccumulate(12, _ + _)


}
