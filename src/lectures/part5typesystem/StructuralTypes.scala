package lectures.part5typesystem

object StructuralTypes extends App{

  type JavaClosable = java.io.Closeable

  class HipsterClosable {
    def close(): Unit = println("yeah yeah I'm closing")
    def closeSilently(): Unit = println("Hipster closing silently")
  }

  //def closeQuietly(closable: JavaClosable OR HipsterClosable)

  type UnifiedCloseable = {
    def close(): Unit
  } //Structural type

  def closeQuietly(unifiedCloseable: UnifiedCloseable): Unit = unifiedCloseable.close()


  closeQuietly(new JavaClosable {
    override def close(): Unit = println("closing")
  })

  closeQuietly(new HipsterClosable)

  // TYPE REFINEMENTS

  type AdvancedCloseable = JavaClosable {
    def closeSilently(): Unit
  }

  class AdvancedJavaCloseable extends JavaClosable {
    override def close(): Unit = println("Java closes")
    def closeSilently(): Unit = println("Java closing silently")
  }

  def closeShh(advancedCloseable: AdvancedCloseable): Unit = advancedCloseable.closeSilently()

  closeShh(new AdvancedJavaCloseable)
  //closeShh(new HipsterClosable)

  //using structural types as standalone types

  def alternativeClose(closeable: { def close(): Unit }): Unit = closeable.close()

  //type-checking => duck typing


}
