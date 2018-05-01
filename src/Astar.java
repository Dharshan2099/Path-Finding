import java.util.ArrayList;

public class Astar {

    //main run method
    public static ArrayList<Node> run(Node start, Node end, Node[][] node_grid, String option){

        for (int i = 0; i < node_grid.length; i++) {
            for (int j = 0; j < node_grid[i].length; j++) {
                node_grid[i][j].visited = false;
                if (option.startsWith("c"))
                    node_grid[i][j].h_value = chebyshev(node_grid[i][j].x, end.x, node_grid[i][j].y, end.y);
                else if (option.startsWith("m"))
                    node_grid[i][j].h_value = manhattan(node_grid[i][j].x, end.x, node_grid[i][j].y, end.y);
                else
                    node_grid[i][j].h_value = euclidean(node_grid[i][j].x, end.x, node_grid[i][j].y, end.y);
                node_grid[i][j].f_value = node_grid[i][j].h_value + node_grid[i][j].weight;
            }
        }

        if (start.weight == 4 || end.weight == 4) return null;

        ArrayList<Node> path = new ArrayList<Node>();
        start.visited = true;
        System.out.println(start.id);
        path.add(start);
        Node target = get_min_node(start, end);
        if (target == null) return null;
        System.out.println(target.id);
        path.add(target);

        while(target != end){
            target = get_min_node(target, end);
            if (target == null) return null;
            System.out.println(target.id);
            path.add(target);
        }

        return path;

    }

    //returning the node with the least f_value;
    public static Node get_min_node(Node start, Node end){
        Node min_node = null; int i = 0;
        for (; i < start.nodes.size(); i++){
            if (start.nodes.get(i) == end) return end;
            if (!start.nodes.get(i).visited && start.nodes.get(i).weight != 4) {
                min_node = start.nodes.get(i); min_node.visited = true; break;
            }
        }
        for (; i < start.nodes.size(); i++){
            Node node = start.nodes.get(i);
            if (node == end) return end;
            if (!node.visited && min_node.f_value > node.f_value && node.weight != 4) min_node = node;
            min_node.visited = true;
        }
        return min_node;
    }

    //metric heuristic calculations
    public static double manhattan(int x1, int x2, int y1, int y2){
        return 1 + Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }

    public static double euclidean(int x1, int x2, int y1, int y2){
        return Math.pow(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2), 0.5);
    }

    public static double chebyshev(int x1, int x2, int y1, int y2){
        return Math.max(Math.abs(x2-x1), Math.abs(y2-y1));
    }

}
