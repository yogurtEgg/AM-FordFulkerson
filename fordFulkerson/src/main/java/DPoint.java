import java.util.ArrayList;

/**
 * @author Paede
 * @version 03.10.2020
 */
public class DPoint {
    private int id;
    private double posX;
    private double posY;
    private double radius;
    private ArrayList<String> connections;

    public DPoint(double posX, double posY, int id) {
        this.posX = posX;
        this.posY = posY;
        this.id = id;
        radius = 10;
    }

    public DPoint(){

    }

    public ArrayList<String> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<String> connections) {
        this.connections = connections;
    }

    public void addConnection(String connection){
        this.connections.add(connection);
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
