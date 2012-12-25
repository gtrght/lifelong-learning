package algorithms.coursera.algo2

/**
 * User: vasily
 * Date: 25.12.12
 */
object Knapsack extends App {
  def knap(elements: List[Int], capacity: Int): List[Int] = {

    def knap0(elements: List[Int], index: Int, capacity: Int, matrix: Array[Array[Int]]): Int = {

      if (index < matrix.size && matrix(index)(capacity) == -1) {
        matrix(index)(capacity) =
          if (elements(index) <= capacity)
            scala.math.max(knap0(elements, index + 1, capacity - elements(index), matrix) + elements(index),
              knap0(elements, index + 1, capacity, matrix))

          else 0
        matrix(index)(capacity)
      }
      else 0
    }

    val matrix: Array[Array[Int]] = Array.fill(elements.size, capacity + 1) {
      -1
    }
    knap0(elements, 0, capacity, matrix)

    List(matrix(0)(capacity))
  }

  print(knap(List(17, 6, 7, 10), 29))
}
