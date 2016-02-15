package algorithms.coursera.algo2

import io.Source
import scala.{collection, Array}
import collection.mutable

/**
 * User: vasily
 * Date: 23.12.12
 */
object Quiz2 extends App {

  def clustering1(expected: Int): Int = {

    def buildClustering(clusters: Set[Set[Int]], edges: List[(Int, Int, Int)]): List[(Int, Int, Int)] = {
      if (clusters.size == expected || edges.size == 0) edges
      else {
        val currentEdge: (Int, Int, Int) = edges(0)

        val from: Option[Set[Int]] = clusters.find((set: Set[Int]) => set.contains(currentEdge._1))
        val to: Option[Set[Int]] = clusters.find((set: Set[Int]) => set.contains(currentEdge._2))

        from match {
          case Some(x) => to match {
            case Some(y) => val union: Set[Int] = x ++ y
            buildClustering((clusters - x - y) + union, edges.filter((tuple: (Int, Int, Int)) => !union.contains(tuple._1) || !union.contains(tuple._2)))
            case None => throw new IllegalStateException()
          }
          case None => throw new IllegalStateException()
        }
      }
    }

    val edges: List[(Int, Int, Int)] = Source.fromURL(getClass.getResource("clustering1.txt")).getLines().map((s: String) => {
      val split: Array[String] = s.split(" ")
      (augmentString(split(0)).toInt, augmentString(split(1)).toInt, augmentString(split(2)).toInt)
    }).toList.sortBy(_._3)

    val nodes: Set[Int] = edges.foldLeft(Set[Int]())((set: Set[Int], tuple: (Int, Int, Int)) => set + tuple._1 + tuple._2)
    buildClustering(nodes.map((i: Int) => Set.apply(i)).toSet, edges).foldLeft(Int.MaxValue)((current: Int, tuple: (Int, Int, Int)) => if (tuple._3 < current) tuple._3 else current)
  }

  def copy[A: ClassManifest](array: Array[A]): Array[A] = {
    val newArray = new Array[A](array.size)
    Array.copy(array, 0, newArray, 0, array.size)
    newArray
  }

  def clustering2(): Int = {

    def switch(c: Char) = if (c == '0') '1' else '0'

    def generateSeq(input: Array[Char]): List[String] = {
      def generate0(input: Array[Char], number: Int, maxIndex: Int): List[Array[Char]] = {
        (maxIndex to input.size - number).foldLeft(List[Array[Char]]())((set: List[Array[Char]], index: Int) => {
          set ++ {
            val out: Array[Char] = copy(input)
            out(index) = switch(input(index))

            if (number == 1) {
              List(out)
            }
            else {
              generate0(out, number - 1, index + 1)
            }
          }
        })
      }
      (generate0(input, 1, 0) ++ generate0(input, 2, 0)).map((chars: Array[Char]) => new String(chars))
    }

    def cluster(input: scala.collection.mutable.Map[String, Int]): Set[Int] = {
      val keySet: collection.Set[String] = input.keySet
      var groupCounter = 0
      keySet.foreach((key: String) => {
        var group: Int = input.getOrElse(key, -1)
        if (group == -1) {
          groupCounter += 1
          group = groupCounter
          input.put(key, group)
        }
        generateSeq(key.toCharArray).foreach((s: String) => if (input.contains(s)) {
          val orElse: Int = input.getOrElse(s, -1)

          if(orElse == -1 || orElse == group)
            input.put(s, group)
          else {
            for((k, v) <- input)
              if (v == orElse) input.put(k, group)
          }
        })
      })

      input.values.toSet
    }

    val input: Map[String, Int] = Source.fromURL(getClass.getResource("clustering2.txt")).getLines().map((s: String) => {
      (s.replace(" ", ""), -1)
    }).toMap


    val size: Int = cluster(mutable.Map() ++ input).size


    println()
    size
  }

    print(clustering1(4))
  print(clustering2())
}
