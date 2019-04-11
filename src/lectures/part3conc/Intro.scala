package lectures.part3conc

import java.util.concurrent.Executors

object Intro extends App {

  //JVM threads
  val thread = new Thread(() => println("Running in parallel"))

  //thread.start() //gives the signal to JVM to start the JVM thread
  //create a JVM thread => OS thread
  thread.join() //blocks until the thread finishes running


  val threadHello = new Thread(() => (1 to 5).foreach(_ => println("hello")))
  val threadGoodbye = new Thread(() => (1 to 5).foreach(_ => println("Goodbye")))

  //threadHello.start()
  //threadGoodbye.start()

  val pool = Executors.newFixedThreadPool(10)
/*  pool.execute(() => println("Something in the thread pool"))

  pool.execute(() => {
    Thread.sleep(1000)
    println("done after 1 second")
  })

  pool.execute(() => {
    Thread.sleep(1000)
    println("almost done")
    Thread.sleep(1000)
    println("done after 2 seconds")
  })*/

 // pool.shutdown()

  def runInParallel() {
    var x = 0

    val thread1 = new Thread(() => {
      x = 1
    })

    val thread2 = new Thread(() => {
      x = 2
    })

    thread1.start()
    thread2.start()
    println(x)
  }

  //for (_ <- 1 to 100) runInParallel
  //race condition

  class BankAccount(@volatile var amount: Int) {
    override def toString: String = "" + amount

  }

  def buy(account: BankAccount, thing: String, price: Int): Unit = {
    account.amount -= price
    //println("I have bought " + thing)
   // println("my account is now " + account)
  }
  /*for (_ <- 1 to 10000) {
    val account = new BankAccount(50000)
    val thread1 = new Thread(() => buy(account, "shoes", 3000))
    val thread2 = new Thread(() => buy(account, "iPhone12", 4000))

    thread1.start()
    thread2.start()
    Thread.sleep(10)

    if (account.amount != 43000) println("AHA " + account.amount)
    //  println()
  }*/

  def buySafe(account: BankAccount, thing: String, price: Int): Unit = {
    account.synchronized {
      account.amount -= price
    }
  }

  def inceptionThreads(maxThreads: Int, i: Int = 1): Thread = new Thread(() => {
    if (i < maxThreads) {
      val newThread = inceptionThreads(maxThreads, i + 1)
      newThread.start()
      newThread.join()
    }
    println("Hello from thread " + i)
  })

  inceptionThreads(10).start()

  var x = 0
  val threads = (1 to 100).map(_ => new Thread(() => x += 1))
  threads.foreach(_.start())
  threads.foreach(_.join())
  println(x)


  var message = ""

  val awesomeThread =  new Thread(() => {
    Thread.sleep(1000)
    message = "Scala is awesome"
  })

  message = "Scala sucks"
  awesomeThread.start()
  Thread.sleep(2000)
  println(message)

}
