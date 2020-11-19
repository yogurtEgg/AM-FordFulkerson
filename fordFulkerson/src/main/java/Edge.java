/**
 * @author Paede
 * @version 23.09.2020
 */
public class Edge {

    private int value;
    private String name;
    private char sPoint;
    private char tPoint;
    private DPoint startPoint;
    private DPoint endPoint;
    private int capacity;

    public Edge(int value, char sPoint, char tPoint) {
        this.value = value;
        this.sPoint = sPoint;
        this.tPoint = tPoint;
        this.name = Character.toString(sPoint) + Character.toString(tPoint);
    }

    public Edge(DPoint currentPoint, DPoint endPoint, int capacity) {
        this.endPoint = endPoint;
        this.startPoint = startPoint;
        this.capacity = capacity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DPoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(DPoint startPoint) {
        this.startPoint = startPoint;
    }

    public DPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(DPoint endPoint) {
        this.endPoint = endPoint;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public char getsPoint() {
        return sPoint;
    }

    public void setsPoint(char sPoint) {
        this.sPoint = sPoint;
    }

    public char gettPoint() {
        return tPoint;
    }

    public void settPoint(char tPoint) {
        this.tPoint = tPoint;
    }
}
