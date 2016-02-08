object Main extends App {
  def sayHello(name: String)(implicit greeting: String) {
    println(s"$greeting, $name")
  }

  //sayHello("John") // does not compile
  sayHello("Paul")("Hello")

  implicit val greeting = "Hi"
  sayHello("George")


  class Prefixer(val prefix: String) {
    def ha(x: String): Unit = {
      println("fuck" + x)
    }

    def fuck(x:String)={println("fucj " + this.prefix)}


  }
  implicit def any2ArrowAssoc[A](x: A): Prefixer = new Prefixer("ds")
  def addPrefix(s: String)(implicit p: Prefixer) = p.prefix + s

  // then probably in your application
  implicit val myImplicitPrefixer = new Prefixer("***")
  println(addPrefix("abc")) // returns "***abc"

  val m = Map(1 -> "one", 2 -> "two", 3 -> "three")
  val p = new Prefixer("d")
  p ha "43"


  m fuck "sdf"

  println {1024}

}

