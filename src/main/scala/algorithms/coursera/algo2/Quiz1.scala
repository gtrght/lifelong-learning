package algorithms.coursera.algo2

import io.Source

/**
 * User: vasily
 * Date: 23.12.12
 */
object Quiz1 extends App {
  def loadJobsData(): Array[(Int, Int)] = {
    Source.fromURL(getClass.getResource("jobs.txt")).getLines().map(
      (s: String) => {
        val split: Array[String] = s.split(" ")
        (augmentString(split(0)).toInt, augmentString(split(1)).toInt)
      }
    ).toArray
  }

  def calculateWithCompareFunction(compareFunction: ((Int, Int), (Int, Int)) => Boolean, printFunction: ((Int, Int)) => Unit): Long = {
    val data: Array[(Int, Int)] = loadJobsData()
    val sorted: Array[(Int, Int)] = data.sortWith(compareFunction)
    sorted.foreach(printFunction)

    sorted.foldLeft(0l, 0)((total, current) => {
      (total._1 + (total._2 + current._2) * current._1, total._2 + current._2)
    })._1
  }

  def calculate1(): Long = {
    def compareFunction: ((Int, Int), (Int, Int)) => Boolean = {
      (o1, o2) => {
        val d1: Int = o1._1 - o1._2
        val d2: Int = o2._1 - o2._2

        if (d1.compareTo(d2) == 0) o1._2.compare(o2._2) > 0 else d1.compareTo(d2) > 0
      }
    }

    calculateWithCompareFunction(compareFunction, (tuple: (Int, Int)) => println(tuple + " : " + (tuple._1 - tuple._2)))
  }

  def calculate2(): Long = {
    def compareFunction: ((Int, Int), (Int, Int)) => Boolean = {
      (o1, o2) => {
        val d1: Float = o1._1.toFloat / o1._2
        val d2: Float = o2._1.toFloat / o2._2

        d1.compareTo(d2) > 0
      }
    }

    calculateWithCompareFunction(compareFunction, (tuple: (Int, Int)) => println(tuple + " : " + (tuple._1.toFloat / tuple._2)))
  }

  //  println("Answer " + calculate2())
  //  println("Answer " + calculate1())

  def prims(): Long = {

    def prim0(addedNodes: Set[Int], addedEdges: Set[(Int, Int, Int)], edges: List[(Int, Int, Int)]): Set[(Int, Int, Int)] = {
      val filtered: List[(Int, Int, Int)] = edges.filter((tuple: (Int, Int, Int)) => !(addedNodes.contains(tuple._1) && addedNodes.contains(tuple._2)))
      val addingEdge: (Int, Int, Int) = filtered
        .foldLeft((Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE))((tuple: (Int, Int, Int), candidate: (Int, Int, Int)) =>
        if ((addedNodes.contains(candidate._1) || addedNodes.contains(candidate._2)) && candidate._3 < tuple._3) candidate else tuple
      )


      if (addingEdge._3 != Integer.MAX_VALUE) {
        prim0(Array(addingEdge._1, addingEdge._2).foldLeft(addedNodes)((set: Set[Int], i: Int) => set + i), addedEdges + addingEdge, filtered)
      }
      else
        addedEdges
    }

    val list: List[(Int, Int, Int)] = Source.fromURL(getClass.getResource("adjacentNodes.txt")).getLines().map(
      (s: String) => {
        val split: Array[String] = s.split(" ")
        (augmentString(split(0)).toInt, augmentString(split(1)).toInt, augmentString(split(2)).toInt)
      }
    ).toList

    //picking non-random node
    val prim: Set[(Int, Int, Int)] = prim0(Set.apply(3), Set(), list)


    prim.foldLeft(0)((i: Int, tuple: (Int, Int, Int)) => i + tuple._3)
  }

  print(prims())
}
