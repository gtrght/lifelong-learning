package algorithms.coursera.algo2

import io.Source
import collection.mutable

/**
 * User: vasily
 * Date: 05.01.13
 */
object Quiz3 extends App {
  def knapsack(elements: List[(Int, Int)], capacity: Int): Int = {
    def knapsack0(elements: List[(Int, Int)], index: Int, capacity: Int, map: scala.collection.mutable.Map[(Int, Int), Int]): Int = {
      if (index < elements.size) {
        val key: (Int, Int) = (index, capacity)
        if (!map.contains(key)) {
            val max: Int = scala.math.max(if(elements(index)._1 <= capacity) knapsack0(elements, index + 1, capacity - elements(index)._1, map) + elements(index)._2 else 0,
              knapsack0(elements, index + 1, capacity, map))
            map.put(key, max)
        }
        map.getOrElse(key, 0)
      }
      else 0
    }

    val result: mutable.Map[(Int, Int), Int] = mutable.Map.empty
    knapsack0(elements, 0, capacity, result)

    result.values.foldLeft(0) ((result: Int, current: Int) => scala.math.max(result, current))
  }

  def knapsackRecursive(elements: List[(Int, Int)], index: Int, capacity: Int): Int = {
    if (elements.size > index && capacity >= elements(index)._1) {
      scala.math.max(knapsackRecursive(elements, index + 1, capacity - elements(index)._1) + elements(index)._2,
        knapsackRecursive(elements, index + 1, capacity))
    }
    else 0
  }

  def task1(): Int = {
    val input: List[(Int, Int)] = Source.fromURL(getClass.getResource("knapsack_1.txt")).getLines().map((s: String) => {
      val split: Array[String] = s.split(" ")
      (augmentString(split(1)).toInt, augmentString(split(0)).toInt)
    }).toList
    knapsack(input, 10000)
  }

  def task2(): Int = {
    val input: List[(Int, Int)] = Source.fromURL(getClass.getResource("knapsack_2.txt")).getLines().map((s: String) => {
      val split: Array[String] = s.split(" ")
      (augmentString(split(1)).toInt, augmentString(split(0)).toInt)
    }).toList
    knapsack(input, 2000000)
  }


  def test(){
    List(
      ("test/knapsack_sample_1.txt", 170, 1735),
      ("test/knapsack_sample_2.txt", 26, 51),
      ("test/knapsack_sample_4.txt", 50, 107),
      ("test/knapsack_sample_3.txt", 190, 150)
    ).foreach((tuple: (String, Int, Int)) => {
      val input: List[(Int, Int)] = Source.fromURL(getClass.getResource(tuple._1)).getLines().map((s: String) => {
        val split: Array[String] = s.split("\\s+")
        (augmentString(split(1)).toInt, augmentString(split(0)).toInt)
      }).toList
      println(knapsack(input, tuple._2) + " = " + tuple._3)
    })
  }





//   test()
  println(task1())
  println(task2())
}





