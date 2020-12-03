/**
 * @author Paede
 * @version 23.09.2020
 */
public class Edge {

    private int value;
    private DPoint startPoint;
    private DPoint endPoint;

    public Edge(DPoint startPoint, DPoint endPoint, int value) {
        this.endPoint = endPoint;
        this.startPoint = startPoint;
        this.value = value;
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
}
