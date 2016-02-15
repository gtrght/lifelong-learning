package algorithms.data

import scala.collection.JavaConversions._

/**
 * User: Vasily Vlasov
 * Date: 26.01.13
 */
class LazyUnion[T](objects: Seq[T]) {
  private var obj2pos: Map[T, Int] = objects.zip(0 until objects.size).toMap
  private var parents: Array[(Int, Int)] = (0 until objects.size).zip(Array.fill(objects.size) {
    0
  }).toArray

  def findRootTuple(x: T): (Int, Int) = {
    def traverseUp(position: Int): (Int, Int) = {
      if (position == -1) (position, -1)
      else {
        val candidate: (Int, Int) = parents(position)
        if (candidate._1 == position) candidate
        else {
          val up: (Int, Int) = traverseUp(candidate._1)
          parents(position) = (up._1, candidate._2)
          up
        }
      }
    }
    traverseUp(obj2pos.getOrElse(x, -1))
  }

  def findRoot(x: T): Int = findRootTuple(x)._1

  def rank(position: Int) = parents(position)._2

  def same(o1: T, o2: T): Boolean = findRoot(o1) == findRoot(o2)

  def contains(o: T) = obj2pos.containsKey(o)

  def merge(object1: T, object2: T) {
    val root1 = findRootTuple(object1)
    val root2 = findRootTuple(object2)

    if (root1 != root2)
      if (root1._2 > root2._2) parents(root2._1) = (root1._1, root2._2)
      else if (root2._2 > root1._2) parents(root2._1) = (root1._1, root2._2)
      else if (root2._2 == root1._2) {
        parents(root1._1) = (root1._1, root1._2 + 1)
        parents(root2._1) = (root1._1, root2._2)
      }
  }

  def this(objects: java.util.List[T]) = this(objects.toIndexedSeq)
  def this(objects: Array[T]) = this(objects.toIndexedSeq)

  override def clone():LazyUnion[T] = {
    val copy: LazyUnion[T] = new LazyUnion[T](new java.util.ArrayList[T]())
    copy.parents = parents.clone()
    copy.obj2pos = obj2pos

    copy
  }
}
