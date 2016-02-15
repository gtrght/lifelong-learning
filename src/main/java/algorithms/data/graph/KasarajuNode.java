package algorithms.data.graph;

/**
* author: v.vlasov
*/
public class KasarajuNode extends AdjGraph.Node {
    public static final int VISITED = 1;
    public static final int ADDED = 1 << 1;
    public static final int CHILDREN_EXPLORED = 1 << 2;


    public Integer value;
    public KasarajuNode leader = null;
    public int finishingTime = Integer.MAX_VALUE;

    public KasarajuNode(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public boolean isVisited() {
        return hasFlag(VISITED);
    }

    public void markVisited() {
        setFlag(VISITED);
    }

    public boolean isAdded() {
        return hasFlag(ADDED);
    }

    public void markAdded() {
        setFlag(ADDED);
    }

    public void markChildrenExplored() {
        setFlag(CHILDREN_EXPLORED);
    }

    public boolean isChildrenExplored() {
        return hasFlag(CHILDREN_EXPLORED);
    }
}
