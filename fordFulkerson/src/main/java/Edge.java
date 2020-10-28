/**
 * @author Paede
 * @version 23.09.2020
 */
public class Edge {

    private int value;
    private String name;
    private char sPoint;
    private char tPoint;

    public Edge() {
    }

    public Edge(int value, char sPoint, char tPoint) {
        this.value = value;
        this.sPoint = sPoint;
        this.tPoint = tPoint;
        this.name = Character.toString(sPoint)+Character.toString(tPoint);
    }

    public int getValue(){
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
