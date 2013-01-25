package algorithms.data.list;

import scala.Tuple2;

import static algorithms.data.list.LinkedList.Node.next;

/**
 * User: Vasily Vlasov
 * Date: 25.01.13
 */
public class LinkedList<T1> {
    //The Node class is defined bellow
    protected Node<T1> head;

    public void add(T1 value) {
        Node<T1> node = new Node<T1>(value);

        if (head == null) head = node;
        else
            tail().next = node;
    }

    /**
     * @param useCopies if true = the copy of node will be created, so this is not affect the original List
     * @return split in two lists
     * @throws IllegalStateException if there is any cycle
     */
    public Tuple2<LinkedList<T1>, LinkedList<T1>> split(boolean useCopies) {
        LinkedList<T1> list1 = new LinkedList<T1>();
        LinkedList<T1> list2 = new LinkedList<T1>();
        Node<T1> pointer1 = head;
        Node<T1> pointer2 = head;

        //lets run the pointer1 with a speed of 1 and a pointer2 with a speed of 2
        //we assume that we can't rely on size an tail
        while (pointer2 != null) {
            pointer2 = next(next(pointer2));

            if (useCopies)
                list1.add(pointer1.value);//on each step add a value of pointer1

            if (pointer1 != null && pointer1 == pointer2) throw new IllegalStateException("Cycle is detected");
            if (pointer2 != null) pointer1 = next(pointer1);
        }

        //once the cycle ends - if we are working with copies - we have to copy the rest of the list to list2
        //or make some actions on links to the next node otherwise

        if (useCopies) {
            pointer1 = next(pointer1);
            while (pointer1 != null) {
                list2.add(pointer1.value);
                pointer1 = next(pointer1);
            }
        } else {
            Node<T1> head2 = pointer1.next;
            pointer1.next = null;
            list1.setHead(head);
            list2.setHead(head2);
            head = null; //affect current list
        }

        return new Tuple2<LinkedList<T1>, LinkedList<T1>>(list1, list2);
    }

    protected void setHead(Node<T1> head) {
        this.head = head;
    }

    /**
     * This is cost-ineffective however I'd like to keep as less fields as possible
     *
     * @return
     */
    protected Node<T1> tail() {
        testCycle();

        Node<T1> pointer = head;
        while (next(pointer) != null) pointer = next(pointer);

        return pointer;
    }

    private void testCycle() {
        Node<T1> pointer1 = head;
        Node<T1> pointer2 = head;

        //Once we have null - there is no cycle
        while (pointer2 != null) {

            pointer2 = next(next(pointer2));
            pointer1 = next(pointer1);

            if (pointer2 != null && pointer1 == pointer2) throw new IllegalStateException("Cycle is detected");
        }
    }

    public int size() {
        testCycle(); //in order to check for cycles
        Node<T1> pointer = head;

        int size = 0;
        while (pointer != null) {
            size++;
            pointer = next(pointer);
        }

        return size;
    }

    static class Node<T2> {
        T2 value;
        Node<T2> next;

        Node() {
        }

        Node(T2 value) {
            this.value = value;
        }

        Node(T2 value, Node<T2> next) {
            this.value = value;
            this.next = next;
        }

        static <T2> Node<T2> next(Node<T2> node) {
            return node == null ? null : node.next;
        }
    }
}
//