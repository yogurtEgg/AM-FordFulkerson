import exception.ImpossibleBottleNeckValueException;
import exception.ImpossibleOrderException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Paede
 * @version 23.09.2020
 */

public class MaxFlow {
    /**
     * Creates Arraylist for all the points in the diagram
     */
    public static ArrayList<DPoint> points = new ArrayList<DPoint>();

    /**
     * Initialises the Reverse Value of the Connection between S -> A
     */
    public static int sRev = 0;

<<<<<<< Updated upstream
    /**
     * Adds all the points to the ArrayList points
     */
    public static void initData() {
        //Diagram 1: MaxFlow = 14
        points.add(new DPoint('s', new ArrayList<String>(Arrays.asList("a5", "c10"))));
        points.add(new DPoint('a', new ArrayList<String>(Arrays.asList("b4", "d5"))));
        points.add(new DPoint('b', new ArrayList<String>(Arrays.asList("d6", "t4"))));
        points.add(new DPoint('c', new ArrayList<String>(Arrays.asList("a6", "d5"))));
        points.add(new DPoint('d', new ArrayList<String>(Collections.singletonList("t12"))));
    }

    /**
     * Initializes all the Edges from the Information given in the Points Objects
     * Adds the Point 't' which doesn't have to get initialized manually
     *
     * @param points Arraylist of the Points in the Diagram
     * @return Returns all the Edges in a EdgeList
     */
    public static ArrayList<Edge> pointToEdges(ArrayList<DPoint> points) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        points.add(new DPoint('t'));
        for (DPoint dpoint : points) {
            if (dpoint.getConnections() != null) {
                for (String connection : dpoint.getConnections()) {
                    if (isInteger(connection.substring(1))) {
                        Edge edge = new Edge(Integer.parseInt(connection.substring(1)), dpoint.getName(), connection.charAt(0));
                        edges.add(edge);
                    }
                }
            }

        }
        checkPrintEdges(edges);
        return edges;
    }

=======
    private final VisualisationController controller;
    private static String solution;

    public MaxFlow(ArrayList<Edge> edges, VisualisationController controller) throws ImpossibleOrderException, ImpossibleBottleNeckValueException {
        this.controller = controller;
        MaxFlow.edges.addAll(edges);
        solution = "";
        start();
    }

    public void start() throws ImpossibleOrderException, ImpossibleBottleNeckValueException {
        checkPrintEdges(edges);
        printMaxFlow(maxFlow(edges));
    }

>>>>>>> Stashed changes
    /**
     * Repeats the process in a while until there's no more way to be found
     *
     * @param edges The EdgeList with all the possible Edges to flow through
     * @return The maxFlow
     * @throws ImpossibleBottleNeckValueException Throws when the Bottleneck value is lower than 0
     * @throws ImpossibleOrderException           When the sPoint(SourcePoint) is t(TargetPoint)
     */
    public static int maxFlow(ArrayList<Edge> edges) throws ImpossibleBottleNeckValueException, ImpossibleOrderException {
        ArrayList<Edge> tempEdgeList = new ArrayList<Edge>();
        ArrayList<Edge> newEdgeList;

        while (true) {
            ArrayList<Edge> curWay = deepSearch(edges);
            if (curWay.isEmpty()) {
                return sRev;
            } else {
                tempEdgeList.clear();
                newEdgeList = getMaxFlow(curWay);
                for (Edge newEdge : newEdgeList) {
                    for (Edge edge : edges) {
                        if (newEdge.getsPoint() == edge.getsPoint() && newEdge.gettPoint() == edge.gettPoint()) {
                            edge.setValue(edge.getValue() + newEdge.getValue());
                        } else {
                            tempEdgeList.add(newEdge);
                        }
                    }
                }
                edges.addAll(tempEdgeList);
            }
        }
    }

    /**
     * Finds the Bottleneck value by searching the lowest value in the Array
     *
     * @param edgeFlow The EdgeList of the current way
     * @return the lowest value to be found in the values of the EdgeList
     */
    public static int bottleNeckFlow(List<Edge> edgeFlow) {
        int[] flow = new int[edgeFlow.size()];
        for (int i = 0; i < flow.length; i++) {
            flow[i] = edgeFlow.get(i).getValue();
        }
        Arrays.sort(flow);
        return flow[0];
    }

    /**
     * Iterates through every Edge in the EdgeList(way)
     *
     * @param bottleNeck The Bottleneck's value (The lowest Value in the current EdgeList(way))
     * @param edgeFlow   The EdgeList of the current way
     * @return The newEdgeList which holds the reverseEdges
     * @throws ImpossibleOrderException When the sPoint(SourcePoint) is t(TargetPoint)
     */
    public static ArrayList<Edge> reduceFlow(int bottleNeck, List<Edge> edgeFlow) throws ImpossibleOrderException {
        ArrayList<Edge> newEdgeList = new ArrayList<Edge>();
        for (Edge edge : edgeFlow) {
            try {
                Edge revEdge = sPointCheck(edge, bottleNeck);
                if (revEdge != null) {
                    newEdgeList.add(revEdge);
                }
            } catch (ImpossibleOrderException e) {
                throw new ImpossibleOrderException("The Point t isn't a valid starting point");
            }
        }
        return newEdgeList;
    }

    /**
     * Checks if the current Edge's source Point (sPoint) is 't' and throws Exception if it is.
     * If the sPoint is 's' the SRev value is counted up the bottleNeck Value
     * If the curEdges tPoint is 't' a new Edge is initialized with the s- and tPoint swapped and the bottleNeck as value
     * In the end all the values of the Edges are reduced
     *
     * @param curEdge    The current Edge
     * @param bottleNeck The Bottleneck's value (The lowest Value in the current EdgeList(way))
     * @return The new revEdge
     * @throws ImpossibleOrderException When the sPoint(SourcePoint) is t(TargetPoint)
     */
    public static Edge sPointCheck(Edge curEdge, int bottleNeck) throws ImpossibleOrderException {
        Edge edge = null;
        char sPoint = curEdge.getsPoint();
        char tPoint = curEdge.gettPoint();
        switch (sPoint) {
            case 't':
                throw new ImpossibleOrderException("The Point t as starting point isn't valid");
            case 's':
                addSRev(bottleNeck);
                reduceValue(curEdge, bottleNeck);
                break;
            default:
                if (tPoint != 't') {
                    edge = new Edge(bottleNeck, tPoint, sPoint);
                }
                reduceValue(curEdge, bottleNeck);
                break;
        }
        return edge;
    }

    /**
     * The Value of the current Edge is reduced
     *
     * @param curEdge    The current Edge
     * @param bottleNeck The Bottleneck's value (The lowest Value in the current EdgeList(way))
     */
    public static void reduceValue(Edge curEdge, int bottleNeck) {
        curEdge.setValue(curEdge.getValue() - bottleNeck);
    }
    /*
    Adds bottleNeckValue to the S->any Point Reverse Value
     */

    public static void addSRev(int bottleNeck) {
        sRev = sRev + bottleNeck;
    }

    /**
     * For every possible way and checkBottleNeck is true
     * Checks if the bottleneck is greater than 0
     *
     * @param curWay The current Way
     * @return Returns the EdgeList which holds the RevEdges
     * @throws ImpossibleOrderException           When the sPoint(SourcePoint) is t(TargetPoint)
     * @throws ImpossibleBottleNeckValueException Throws when the Bottleneck value isn't greater than 0
     */
    public static ArrayList<Edge> getMaxFlow(ArrayList<Edge> curWay) throws ImpossibleOrderException, ImpossibleBottleNeckValueException {
        checkPrintWay(curWay);
        int bottleneck = bottleNeckFlow(curWay);
        if (bottleneck > 0) {
            return reduceFlow(bottleneck, curWay);
        } else {
            throw new ImpossibleBottleNeckValueException("The Bottleneck value can't be lower than 0");
        }
    }

    /**
     * Starts the Recursion which searches a Way
     *
     * @param edges The EdgeList with all the possible Edges to flow through
     * @return a possible Way to flow
     */
    public static ArrayList<Edge> deepSearch(ArrayList<Edge> edges) {
        char curSourceChar = 's';
        ArrayList<Edge> curWay = new ArrayList<Edge>();
        return deepSearchRec(edges, curWay, curSourceChar);
    }

    /**
     * A Recursion which iterates through itself, starting from the Source until it reaches the Target
     *
     * @param edges         The List with all the possible Edges
     * @param curWay        The current Way
     * @param curSourceChar The Source point from where it should continue or start the search
     * @return a possible Way to flow
     */
    public static ArrayList<Edge> deepSearchRec(ArrayList<Edge> edges, ArrayList<Edge> curWay, char curSourceChar) {
        //Boolean to check if the Recursion isn't circling itself
        for (Edge edge : edges) {
            //Checks if the sourcePoint matches the given sourceChar and if it's greater than 0
            if (edge.getsPoint() == curSourceChar && edge.getValue() > 0) {
                //Circle Check
                if (curWay.isEmpty() || noCircle(edge, curWay, 0)) {
                    //Adds it to the current way
                    curWay.add(edge);
                    if (edge.gettPoint() == 't') {
                        //The Recursion has reached it's end
                        return curWay;
                    } else {
                        curWay = deepSearchRec(edges, curWay, edge.gettPoint());
                        //Checks if the Recursion has already reached it's end and if it has passes the return value
                        Edge checkEdge = curWay.get(curWay.size() - 1);
                        if (checkEdge.gettPoint() == 't') {
                            return curWay;
                        }
                        //Otherwise it removes the last Edge abd goes on with searching
                        curWay.remove(edge);
                    }
                }
            }
        }
        return curWay;
    }

    /**
     * Checks in a Recursion if the way isn't circling itself
     *
     * @param curEdge The next Edge the way want's to go to (the edge that has to be tested)
     * @param curWay  The current way
     * @param index   The index of the current way that has to be checked
     * @return true if there's no circle, false if there is
     */
    public static boolean noCircle(Edge curEdge, ArrayList<Edge> curWay, int index) {
        if (curEdge.gettPoint() == curWay.get(index).getsPoint()) {
            return false;
        } else {
            index++;
            if (curWay.size() > index) {
                return noCircle(curEdge, curWay, index);
            }
            return true;
        }
    }

    /**
     * Prints out each Way given by the deepSearch Method
     *
     * @param curWay The current Way
     */
    public static void checkPrintWay(ArrayList<Edge> curWay) {
        for (Edge edge : curWay) {
            String tempString = "--";
            if (edge.getValue() > 9) tempString = "-";
<<<<<<< Updated upstream
            System.out.print("(" + Character.toUpperCase(edge.getsPoint()) + ") " + tempString + edge.getValue() + "-> ");
            if(edge  == curWay.get(curWay.size() - 1)){
                System.out.print("(T)");
=======
            System.out.print("(" + edge.getStartPoint().getId() + ") " + tempString + edge.getValue() + "-> ");
            if (edge == curWay.get(curWay.size() - 1)) {
                System.out.print("(1)");
>>>>>>> Stashed changes
            }
        }
        System.out.print("\n");
    }

    /**
     * Prints the Edges with their Values, SourcePoints and TargetPoints
     *
     * @param edges The EdgeList with all the Edges which are given in the start
     */
    public static void checkPrintEdges(ArrayList<Edge> edges) {
<<<<<<< Updated upstream
        for (Edge edge : edges) {
            System.out.println(edge.getsPoint() + " -" + edge.getValue() + "-> " + edge.gettPoint());
        }
    }

    /**
     * Checks if a String has a Integer value
     *
     * @param s The String that has to be tested
     * @return if the given String is a Integer
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /**
     * Prints out maximum flow
     * @param maxFlow The maxFlow
     */
    public static void printMaxFlow(int maxFlow) {
        System.out.println("Maximum flow: " + maxFlow);
    }

    public static void main(String[] args) throws ImpossibleBottleNeckValueException, ImpossibleOrderException {
        initData();
        printMaxFlow(maxFlow(pointToEdges(points)));
    }
}
=======
        solution += "Edges:";
        for (Edge edge : edges)
            solution = solution + ("\n" + edge.getStartPoint().getId() + " -" + edge.getValue() + "-> " + edge.getEndPoint().getId());
    }

    /**
     * Prints out maximum flow
     *
     * @param maxFlow The maxFlow
     */
    public void printMaxFlow(int maxFlow) {
        controller.outputSolution(solution + "\nMax flow:" + maxFlow);
    }

    public String getSolution() {
        return solution;
    }

}
>>>>>>> Stashed changes
