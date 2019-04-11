package lectures.part3conc

import scala.collection.mutable
import scala.util.Random

object ThreadCommunication extends App {


  class SimpleContainer {
    private var value: Int = 0
    def isEmpty: Boolean = value == 0
    def get = {
      val result = value
      value = 0
      result
    }

    def set(newValue: Int) = value = newValue
  }

  def naiveProdCons(): Unit = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("[consumer] waiting...")
      while (container.isEmpty) {
        println("[consumer] still actively waiting...")
      }
      println("[consumer] I have consumed:" + container.get)
    })

    val producer = new Thread(() => {
      println("[producer] computing...")
      Thread.sleep(500)
      val value = 42
      println("[producer] I have produced the value " + value)
      container.set(value)
    })

    consumer.start()
    producer.start()
  }
  //naiveProdCons()

  //wait and notify

  def smartProdCons(): Unit = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("[consumer] waiting...")
      container.synchronized {
        container.wait()
      }
      println("[consumer] I have consumed:" + container.get)

    })

    val producer = new Thread(() => {
      println("[producer] hard at work...")
      Thread.sleep(2000)
      val value = 42
      container.synchronized {
        println("[producer] I have produced the value " + value)
        container.set(value)
        container.notify()
      }
    })
    consumer.start()
    producer.start()

  }
  //smartProdCons()

  def prodConsLargeBuffer(): Unit = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    val capacity = 3

    val consumer = new Thread(() => {
      val random = new Random()

      while (true) {
        buffer.synchronized {
          if (buffer.isEmpty) {
            println("[consumer] buffer empty waiting")
            buffer.wait()
          }
          val x = buffer.dequeue()
          println("[consumer] I cossumed " + x)

          buffer.notify()

        }
        Thread.sleep(random.nextInt(500))
      }
    })

    val producer = new Thread(() => {
      val random = new Random()
      var i = 0

      while (true) {
        buffer.synchronized {
          if (buffer.size == capacity) {
            println("[producer] buffer is full. Waiting...")
            buffer.wait()
          }

          println("[producer] producing " + i)
          buffer.enqueue(i)

          buffer.notify()

          i += 1
        }
        Thread.sleep(random.nextInt(500))
      }
    })

    consumer.start()
    producer.start()

  }
 // prodConsLargeBuffer()

  class Consumer(id: Int, buffer: mutable.Queue[Int]) extends Thread {
    override def run(): Unit = {
      val random = new Random()

      while (true) {
        buffer.synchronized {
          while (buffer.isEmpty) {
            println(s"[consumer $id] buffer empty waiting")
            buffer.wait()
          }
          val x = buffer.dequeue()
          println(s"[consumer $id] I cossumed " + x)

          buffer.notify()

        }
        Thread.sleep(random.nextInt(500))
      }
    }
  }

  class Producer(id: Int, buffer: mutable.Queue[Int], capacity: Int) extends Thread {
    override def run(): Unit = {
      val random = new Random()
      var i = 0

      while (true) {
        buffer.synchronized {
          while (buffer.size == capacity) {
            println(s"[producer $id] buffer is full. Waiting...")
            buffer.wait()
          }

          println(s"[producer $id] producing " + i)
          buffer.enqueue(i)

          buffer.notify()

          i += 1
        }
        Thread.sleep(random.nextInt(500))
      }
    }
  }

  def multiProdCons(nConsumers: Int, nProducers: Int): Unit = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    val capacity = 3

    (1 to nConsumers).foreach(i => new Consumer(i, buffer).start())
    (1 to nProducers).foreach(i => new Producer(i, buffer, capacity).start())
  }


  //multiProdCons(3, 3)

  // 1. notifyAll differs from notify
  def testNotifyAll(): Unit = {
    val bell = new Object

    (1 to 10).foreach(i => new Thread(() => {
      bell.synchronized {
        println(s"Thread $i waiting")
        bell.wait()
        println(s"Thread $i hooray")
      }
    }).start())

    new Thread(() => {
      Thread.sleep(2000)
      println("[announcer] Rock'n roll")
      bell.synchronized {
        bell.notifyAll()
      }
    }).start()
  }

  //testNotifyAll()
  // 2. deadlock

  case class Friend(name: String) {
    def bow(other: Friend) = {
      this.synchronized {
        println(s"$this I am bowing to my friend $other")
        other.rise(this)
        println(s"$this my friend has risen")
      }
    }
    def rise(other: Friend): Unit = {
      this.synchronized {
        println(s"$this I am rising to my friend $other")
      }
    }

    var side = "right"
    def switchSide() = {
      if (side == "right") side = "left"
      else side = "right"
    }

    def pass(other: Friend) = {
      while (this.side == other.side) {
        println(s"$this: Oh but please, $other feel free to pass")
        switchSide()
        Thread.sleep(1000)
      }
    }
  }

  val sam = new Friend("Sam")
  val pierre = new Friend("Pierre")
  //new Thread(() => sam.bow(pierre)).start()
  //new Thread(() => pierre.bow(sam)).start()


  // 3. livelock
  new Thread(() => sam.pass(pierre)).start()
  new Thread(() => pierre.pass(sam)).start()
}
