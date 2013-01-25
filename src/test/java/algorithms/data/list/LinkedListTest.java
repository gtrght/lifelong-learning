package algorithms.data.list;

import org.hamcrest.Matchers;
import org.junit.Test;
import scala.Tuple2;

import static algorithms.data.list.LinkedList.Node.next;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * User: Vasily Vlasov
 * Date: 25.01.13
 */
public class LinkedListTest {

    @Test(expected = IllegalStateException.class)
    public void testCycleDetection(){
        LinkedList<Integer> list = list(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
        //creating a cycle
        LinkedList.Node<Integer> tail = list.tail();
        tail.next = next(next(list.head));
        Tuple2<LinkedList<Integer>, LinkedList<Integer>> split = list.split(true);
    }

    @Test
    public void testSplitEmptyList() throws Exception {
        LinkedList<Integer> list = list();
        Tuple2<LinkedList<Integer>, LinkedList<Integer>> split = list.split(true);

        assertThat(list.size(), equalTo(0));
        assertThat(split._1().size(), equalTo(0));
        assertThat(split._2().size(), equalTo(0));
    }

    @Test
    public void testSplitOne() throws Exception {
        LinkedList<Integer> list = list(1);
        Tuple2<LinkedList<Integer>, LinkedList<Integer>> split = list.split(true);

        assertThat(list.size(), equalTo(1));
        assertThat(split._1().size(), equalTo(1));
        assertThat(split._2().size(), equalTo(0));
    }

    @Test
    public void testSplitTwo() throws Exception {
        LinkedList<Integer> list = list(1, 2);
        Tuple2<LinkedList<Integer>, LinkedList<Integer>> split = list.split(true);

        assertThat(list.size(), equalTo(2));
        assertThat(split._1().size(), equalTo(1));
        assertThat(split._2().size(), equalTo(1));
    }

    @Test
    public void testSplitMultipleOdd() throws Exception {
        LinkedList<Integer> list = list(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Tuple2<LinkedList<Integer>, LinkedList<Integer>> split = list.split(true);

        assertThat(split._1().size(), equalTo(5));
        assertThat(split._2().size(), equalTo(4));
    }

    @Test
    public void testSplitMultipleEven() throws Exception {
        LinkedList<Integer> list = list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Tuple2<LinkedList<Integer>, LinkedList<Integer>> split = list.split(true);

        assertThat(split._1().size(), equalTo(5));
        assertThat(split._2().size(), equalTo(5));
    }

    @Test
    public void testSplitMultipleOddInPlace() throws Exception {
        LinkedList<Integer> list = list(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Tuple2<LinkedList<Integer>, LinkedList<Integer>> split = list.split(false);

        assertThat(list.size(), equalTo(0));
        assertThat(split._1().size(), equalTo(5));
        assertThat(split._2().size(), equalTo(4));
    }


    @Test
    public void testSplitMultipleEvenInPlace() throws Exception {
        LinkedList<Integer> list = list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Tuple2<LinkedList<Integer>, LinkedList<Integer>> split = list.split(false);

        assertThat(list.size(), equalTo(0));
        assertThat(split._1().size(), equalTo(5));
        assertThat(split._2().size(), equalTo(5));
    }

    private LinkedList<Integer> list(Integer... values) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (Integer value : values) {
            list.add(value);
        }
        return list;
    }
}
