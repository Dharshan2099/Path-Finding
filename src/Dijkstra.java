import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;

public class Dijkstra {

    //main run method
    public static ArrayList<Node> run(Node start, int len, Node end){
        //
        ArrayList<Node> visited_nodes = new ArrayList<Node>();
        ArrayList<Node> un_visited_nodes = new ArrayList<Node>();
        start.t_distance = 0;
        visited_nodes.add(start);
        do_node(start, un_visited_nodes, visited_nodes);
        //System.out.println(start.t_distance + " *****");
        sort(un_visited_nodes);
        while (visited_nodes.size() != len){
            Node node = un_visited_nodes.get(0);
            remove_unvisited(node, un_visited_nodes);
            visited_nodes.add(node);
            do_node(node, un_visited_nodes, visited_nodes);
        }
        ArrayList<Node> path = new ArrayList<Node>();
        while (end != null){
            //System.out.println(end.id);
            path.add(end);
            end = end.root_node;
        }
        return path;
    }

    //calculating for each node
    public static void do_node(Node node, ArrayList<Node> un_visited_nodes, ArrayList<Node> visited_nodes){
        for (int i = 0; i < node.nodes.size(); i++){
            Node n = node.nodes.get(i);
            int t_s = n.weight + node.t_distance;
            if (!isIn(n, visited_nodes) && n.t_distance == -1 || n.t_distance > t_s){
                n.t_distance = t_s;
                n.root_node = node;
                un_visited_nodes.add(n);
            }
        }
    }

    //checking if node is in nodes
    public static boolean isIn(Node node, ArrayList<Node> visited_nodes){
        for (int i = 0; i < visited_nodes.size(); i++)
            if (visited_nodes.get(i) == node) return true;
        return false;
    }

    //sorting functions
    public static void sort(ArrayList<Node> nodes){
        int len = nodes.size();
        for (int i = 0; i < len; i++)
            swap(nodes, i, findMin(nodes, i));
    }

    public static void swap(ArrayList<Node> nodes, int i, int j){
        Node a = nodes.get(i), b = nodes.get(j);
        Node temp = a;
        a = b;
        b = temp;
    }

    public static int findMin(ArrayList<Node> nodes, int start){
        int min = nodes.get(start).weight, index = start;
        for (; start < nodes.size(); start++){
            if(min > nodes.get(start).weight) {
                min = nodes.get(start).weight;
                index = start;
            }
        }
        return index;
    }

    public static void remove_unvisited(Node node, ArrayList<Node> unvisited_nodes){
        for (int i = 0; i < unvisited_nodes.size(); i++){
            if (unvisited_nodes.get(i) == node) {
                unvisited_nodes.remove(i); break;
            }
        }
    }

}
