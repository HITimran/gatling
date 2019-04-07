package scalaTutorial

import scala.io.StdIn

object interactive {

  def main(args: Array[String]): Unit = {

    var numberGuess = 0;

    do {
      print("Guess a number ")
      numberGuess = StdIn.readInt();
    } while (numberGuess != 15)

    printf("you guessed the secret number %d\n", 15)

    val name = "imran";
    val age = 22
    val weight = 60.5
    println(s" hello $name")
    println(f" i am ${age + 1} and Weigh $weight%.2f")
    printf("'%-5s'\n", "hi");


    var randSent="i saw a dragon fly by "
    println(  )

  }

}
