package algorithms.coursera.algo2

/**
 * User: vasily
 * Date: 25.12.12
 */
object WISPath extends App {
  def wis(nodes: List[Int]): List[Int] = {
    def wis0(nodes: List[Int], index: Int, cache: Array[Int]): Int = {

      if (index < nodes.size) {
        if (cache(index) < 0)
          cache(index) = math.max(wis0(nodes, index + 1, cache), wis0(nodes, index + 2, cache) + nodes(index))
        cache(index)
      }
      else 0
    }

    val result: Array[Int] = Array.fill(nodes.size) {
      -1
    }
    wis0(nodes, 0, result)

    def reconstruct(cached: Array[Int], index: Int): List[Int] = {
      if (index > cached.size - 1) List()
      else if (index == cached.size - 1) List(nodes(index))
      else if (cached(index) > cached(index + 1)) List(nodes(index)) ++ reconstruct(cached, index + 2)
      else reconstruct(cached, index + 1)
    }


    reconstruct(result, 0)
  }

  print(wis(List(3, 4, 2, 3)))
}
