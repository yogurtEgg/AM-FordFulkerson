import java.util.ArrayList;

/**
 * @author Paede
 * @version 03.10.2020
 */
public class DPoint {

    private char name;
    private ArrayList<String> connections;

    public DPoint(char name, ArrayList<String> connections) {
        this.name = name;
        this.connections = connections;
    }

    public DPoint(char name){
        this.name = name;
    }

    public DPoint(){}

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
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
}
