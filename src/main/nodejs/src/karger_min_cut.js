/**
 *
 * The file contains the adjacency list representation of a simple undirected graph. There are 200 vertices labeled 1 to 200. The first column in the file represents the vertex label, and the particular row (other entries except the first column) tells all the vertices that the vertex is adjacent to. So for example, the 6th row looks like : "6    155    56    52    120    ......". This just means that the vertex with label 6 is adjacent to (i.e., shares an edge with) the vertices with labels 155,56,52,120,......,etc
 *
 * Your task is to code up and run the randomized contraction algorithm for the min cut problem and use it on the above graph to compute the min cut. (HINT: Note that you'll have to figure out an implementation of edge contractions.
 * author: v.vlasov
 */

_ = require('./underscore.js')._;

Array.prototype.contains = function (x) {
    return this.indexOf(x) != -1;
};

Array.prototype.removeAll = function (elements) {
    var newArray = [];
    this.forEach(function (x) {
        if (!elements.contains(x))
            newArray[newArray.length] = x;
    });
    this.length = 0;
    return this.push.apply(this, newArray);
};

Array.prototype.remove = function (element) {
    var indexOf = this.indexOf(element);
    if (indexOf < 0) return;
    this.removeAt(indexOf);
};

Array.prototype.removeAt = function (from, to) {
    var rest = this.slice((to || from) + 1 || this.length);
    this.length = from < 0 ? this.length + from : from;
    return this.push.apply(this, rest);
};


function guid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

function Graph(adjacent) {
    this.adjacent = adjacent || {};

    this.addEdge = function (edge) {
        var edges = this.adjacent[edge.source];

        if (_.isUndefined(edges)) {
            this.adjacent[edge.source] = edges = [];
        }
        edges[edges.length] = edge;
    };

    this.addNode = function (node) {
        var edges = this.adjacent[node];
        if (_.isUndefined(edges))
            this.adjacent[node] = [];
    };

    this.randomPair = function () {
        var keys = _.keys(this.adjacent);
        if (this.adjacent.length < 2) throw new Error("Less then 2 nodes.");

        var x = keys[_.random(0, keys.length - 1)];
        var y = keys[_.random(0, keys.length - 1)];
        while (x == y) {
            y = keys[_.random(0, keys.length - 1)];
        }
        return [x, y];
    };

    this.renameNode = function (oldNames, newName) {
        var adjacent = this.adjacent;
        var nodes = [];

        adjacent[newName].forEach(function (edge) {
            if (oldNames.contains(edge.source)) {
                edge.source = newName;
                nodes[nodes.length] = edge.target;
            }
            if (oldNames.contains(edge.target)) {
                edge.target = newName;
                nodes[nodes.length] = edge.source;
            }
        });

        nodes.forEach(function (node) {
            var find = _.find(adjacent[node], function (edge) {
                return oldNames.contains(edge.target);
            });

            if (!_.isUndefined(find))
                find.target = newName;
        });
    };

    this.mergeNodes = function (node1, node2) {
        var edges1 = this.adjacent[node1];
        var edges2 = this.adjacent[node2];

        var newName = node1 + ',' + node2;
        this.adjacent[newName] = _.union(edges1, edges2);

        this.renameNode([node1, node2], newName);
        delete this.adjacent[node1];
        delete this.adjacent[node2];

        this.removeCycles(newName);
    };

    this.removeCycles = function (x) {
        var toCheck = [];
        if (x)
            toCheck[0] = x;
        else
            toCheck = this.nodes();

        var adjList = this.adjacent;
        toCheck.forEach(function (node) {
            adjList[node].removeAll(_.filter(adjList[node], function (x) {
                return x.source == x.target;
            }));
        });
    };

    this.countEdges = function () {
        var nodes = this.nodes();
        var adjacent = this.adjacent;

        var sum = 0;
        nodes.forEach(function (node) {
            sum += adjacent[node].length;
        });
        return sum;
    };

    this.nodes = function () {
        return _.keys(this.adjacent);
    };

    this.size = function () {
        return _.keys(this.adjacent).length;
    };

    this.toString = function () {
        return "graph";
    };

}

function Edge(source, target, value) {
    this.source = source;
    this.target = target;
    this.value = value;
}


var fs = require('fs');

function loadGraph(filename) {
    var graph = new Graph();
    var content = fs.readFileSync(filename, {encoding: 'utf-8'}).trim();
    content.split('\r\n').forEach(function (line) {
        var split = line.trim().split(/\s+/g);

        for (var i = 1; i < split.length; i++)
            graph.addEdge(new Edge(split[0], split[i], 1));
    });
    return graph;
}

function karger(graph) {
    while (graph.size() > 2) {
        var pair = graph.randomPair();
        graph.mergeNodes(pair[0], pair[1]);
    }

    var sizes = _.map(graph.nodes(), function (x) {
        return graph.adjacent[x].length;
    });
    return Math.min.apply(0, sizes);
}

function checkUnordered(graph) {
    var nodes = graph.nodes();
    var adjacent = graph.adjacent;

    return _.every(nodes, function (node) {
        var result = true;
        adjacent[node].forEach(function (edge1) {
            var contains = false;
            adjacent[edge1.target].forEach(function (edge2) {
                if (edge2.target == node) contains = true;
            });

            if (!contains) {
                console.log(node + " " + edge1.target);
                result = false;
            }
        });
        return result;
    });


}

if (require.main == module) {
    var graph = loadGraph('karger_min_cut.txt');

    var unordered = checkUnordered(graph);
    console.log('Graph unordered: ' + unordered);

    var size = graph.nodes().length;
    var minCut = 10000000;
    var minGraph = null;

    for (var i = 0; i < size * size * Math.log(size); i++) {
        var graph2 = new Graph(JSON.parse(JSON.stringify(graph.adjacent)));
        var candidate = karger(graph2);

        if (minCut > candidate) {
            minCut = candidate;
            minGraph = graph2;
            console.log(candidate);
        }

        minCut = Math.min(minCut, candidate);
    }

    console.log("Result: " + minCut);
}
