package algorithms.data.graph;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * User: Vasily Vlasov
 * Date: 03.02.13
 */
public class GraphUtils {

    public static AdjGraph readAdjGraph(String resource) throws IOException {
        InputStream inputStream = GraphUtils.class.getResourceAsStream(resource);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        Integer numberOfVertices = Integer.valueOf(reader.readLine());
        Multimap<AdjGraph.Node, AdjGraph.Node> graphData = HashMultimap.create(numberOfVertices, 0);
        HashMap<String, AdjGraph.Node> nodeMap = new HashMap<String, AdjGraph.Node>();

        while (reader.ready()) {
            String[] split = reader.readLine().trim().split(" ");

            graphData.put(getOrCreate(split[0], nodeMap), getOrCreate(split[1], nodeMap));
        }

        return new AdjGraph(graphData);
    }

    private static AdjGraph.Node getOrCreate(String s, HashMap<String, AdjGraph.Node> nodeMap) {
        AdjGraph.Node node = nodeMap.get(s);

        if (node == null) {
            node = new AdjGraph.Node(Integer.valueOf(s));
            nodeMap.put(s, node);
        }

        return node;
    }
}
