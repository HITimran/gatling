object testScala {

  def main(args : Array[String]): Unit =
  {
    val i=0
    val randLetters="ABCDEFGHIJKLMNOPQRSTUVWYZ"

    for(i <- 0 until randLetters.length)
    {
      println(randLetters(i))
    }

  }
}
