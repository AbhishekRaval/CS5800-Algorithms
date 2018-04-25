package Kruskal;

import java.util.ArrayList;

public class Kruskal {

    public static void main(String[] args) {
        /*
        *
        *         10
            0--------1
            |  \     |
           6|   5\   |15
            |      \ |
            2--------3
        *
        *
        *
        * */
//        double[][] graph = {{0, 10, 5, 6}, {10, 0, 15, 0}, {5, 15, 0, 4}, {6, 0, 4, 0}};
        double[][] graph = {
                {0, 0, 0, 0, 5, 0, 0, 3, 0, 0, 0},
                {2, 0, 4, 0, 4, 4, 0, 0, 0, 0, 0},
                {0, 4, 0, 1, 0, 6, 2, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 3, 0, 0, 0, 2},
                {5, 4, 0, 0, 0, 3, 0, 4, 3, 0, 0},
                {0, 4, 6, 0, 3, 0, 0, 0, 5, 7, 0},
                {0, 0, 2, 3, 0, 0, 0, 0, 0, 4, 2},
                {3, 0, 0, 0, 4, 0, 0, 0, 3, 0, 0},
                {0, 0, 0, 0, 3, 5, 0, 3, 0, 6, 0},
                {0, 0, 0, 0, 0, 7, 4, 0, 6, 0, 3},
                {0, 0, 0, 2, 0, 0, 2, 0, 0, 3, 0},
        };
        KruskalTree kt = new KruskalTree();
        double result = kt.kruskal(graph);
        System.out.println(kt.sum);
        for (int i = 0; i < kt.kruskalEdges.size(); i++) {
            System.out.println(kt.kruskalEdges.get(i));
        }


    }

    // builds a graph with uniformly distributed random doubles in [0,1]
    private static double[][] buildGraph(int graphSize) {
        double[][] graph = new double[graphSize][graphSize];

        // set up random weighted edges
        for (int i = 0; i < graphSize; i++) {
            for (int j = 0; j <= i; j++) {
                // deal with diagonal of matrix
                if (i == j) {
                    graph[i][j] = 0;
                    break;
                }

                // set random value for both symmetric matrix entries at once
                graph[i][j] = Math.random();
                graph[j][i] = graph[i][j];
            }
        }
        return graph;
    }
}

// represents an element of the disjoint set directed forest
class Set {
    public Set parent;
    public int id;
    public int rank;

    public Set(int i, int r) {
        parent = this;
        id = i;
        rank = r;
    }

    public void setParent(Set newParent) {
        parent = newParent;
    }

    public void incrementRank() {
        rank++;
    }

    @Override
    public String toString() {
        return "Parent::" + parent.id + " ID ::" + id + " Rank:: " + rank;
    }
}


// represents an edge of a graph
class Edge {
    public Set node1;
    public Set node2;
    public double weight;

    public Edge(Set n1, Set n2, double w) {
        node1 = n1;
        node2 = n2;
        weight = w;
    }

    @Override
    public String toString() {
        return "Source = " + node1.id + "; Target = " + node2.id + "; Weight = " + weight;
    }
}

class KruskalTree {
    public double sum;
    public ArrayList<Edge> kruskalEdges;

    public KruskalTree() {
        sum = 0;
        kruskalEdges = new ArrayList<Edge>();
    }

    // applies Kruskal's algorithm to find L(n)
    public double kruskal(double[][] graph) {

        // build the initial disjoint sets for the vertices
        Set[] vertices = new Set[graph[0].length];
        for (int i = 0; i < graph[0].length; i++) {
            Set s = makeSet(i);
            System.out.println(s);
            vertices[i] = s;
        }


        // build initial edge collection
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for (int i = 0; i < graph[0].length; i++) {
            for (int j = 0; j < i; j++) {
                if (graph[i][j] > 0) {
                    Edge edge = new Edge(vertices[i], vertices[j], graph[i][j]);
                    edges.add(edge);

                }
            }
        }

        System.out.println(edges);
        // sort edges by non-decreasing weight (using quicksort)
        ArrayList<Edge> mstEdges = qSort(edges);
        for (Edge edge : mstEdges) {
            System.out.println(edge);

        }

		/*
            for each edge, if vertices not in same disjoint set, union their
			sets and add edge to edge set (calculate L(n) at the same time)

			note: takes sum rather than adding edge to some final collection
		*/
        for (Edge e : mstEdges) {
            if (findSet(e.node1) != findSet(e.node2)) {
                this.sum += e.weight;
                this.kruskalEdges.add(e);
                union(e.node1, e.node2);
            }
        }

        return this.sum;
    }


    // implements quicksort to sort the edges according to their weights
    private static ArrayList<Edge> qSort(ArrayList<Edge> edges) {
        // base case for recursion
        if (edges.size() < 2) {
            return edges;
        } else {
            ArrayList<Edge> r1 = new ArrayList<Edge>();
            ArrayList<Edge> r2 = new ArrayList<Edge>();
            ArrayList<Edge> r3 = new ArrayList<Edge>();

            // just use the first entry in the list as pivot
            Edge pivot = edges.get(0);
            r2.add(pivot);

            // partition according to pivot weight
            for (Edge e : edges) {
                if (e.weight < pivot.weight) {
                    r1.add(e);
                } else if (e.weight > pivot.weight) {
                    r3.add(e);
                } else {
                    r2.add(e);
                }
            }

            // recurse
            ArrayList<Edge> left = qSort(r1);
            ArrayList<Edge> right = qSort(r3);

            // concatenate results
            ArrayList<Edge> toReturn = new ArrayList<Edge>();
            toReturn.addAll(left);
            toReturn.addAll(r2);
            toReturn.addAll(right);

            return toReturn;
        }
    } // qsort()


    // makes the initial sets
    private static Set makeSet(int id) {
        // singleton sets are their own parents and have rank 0
        Set toReturn = new Set(id, 0);

        return toReturn;
    }


    // returns a set's ultimate parent, implements path compression
    private static Set findSet(Set s) {
        Set parent = s.parent;

        // store sets found along the way to set representative
        ArrayList<Set> toCompress = new ArrayList<Set>();

        // representative has itself as parent
        while (parent != parent.parent) {
            toCompress.add(parent);
            parent = parent.parent;
        }

        // compress path
        for (Set st : toCompress) {
            st.setParent(parent);
        }

        return parent;
    }


    // implements union by rank
    private static void union(Set a, Set b) {
        Set repA = findSet(a);
        Set repB = findSet(b);

        if (repA.rank < repB.rank) {
            repA.setParent(repB);
        } else {
            repB.setParent(repA);

            if (repA.rank == repB.rank) {
                repA.incrementRank();
            }
        }
    }
}
