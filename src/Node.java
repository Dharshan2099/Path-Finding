import java.util.ArrayList;

public class Node {
    //instance variables
    public String id;
    public int weight;
    public int t_distance;
    public ArrayList<Node> nodes;
    public Node root_node;
    public String path;
    public int x;
    public int y;
    public double h_value;
    public double f_value;
    public boolean visited;
    //public constructor
    public Node(String id, int weight){
        this.id = id; this.weight = weight; this.t_distance = -1; nodes = new ArrayList<Node>();
        this.path = id; visited = false;
    }
}
