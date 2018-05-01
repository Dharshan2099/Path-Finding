import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Main_2016017 extends Application {

    Stage stage;
    GridPane grid;
    static Button[][] cells;
    Node[][] node_grid;
    Node start, end;
    int buttons_clicked;
    static Button restart, reload;
    static Label cost;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        stage.setTitle("Slots Machine");


        run();


    }

    //main run method
    public void run(){
        grid = new GridPane();
        int x = Integer.parseInt(JOptionPane.showInputDialog("Enter the length of the grid"));
        int y = x;

        node_grid = this.create(x, y);
        printGrid(node_grid);
        buttons_clicked = 0;
        cells = new Button[x][y];

        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[i].length; j++){
                cells[i][j] = new Button();
                cells[i][j].setPrefSize(500 / y, 500 / x);
                cells[i][j].setStyle(getColor(node_grid[i][j].weight));
                final int i_p = i, j_p = j;
                cells[i][j].setOnAction(e -> {
                    set(i_p, j_p);
                });
            }

        grid.setMinSize(800, 500);
        int len_of_col = 500 / y;
        int len_of_row = 500 / x;

        for (int i = 0; i < y; i++)
            grid.getColumnConstraints().add(new ColumnConstraints(len_of_col));

        for (int i = 0; i < x; i++)
            grid.getRowConstraints().add(new RowConstraints(len_of_row));

        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells[i].length; j++)
                grid.add(cells[i][j], j, i);

        restart = new Button("RESTART");
        restart.setOnAction(e -> {
            remove_path();
        });
        reload = new Button("RELOAD");
        reload.setOnAction(e -> {
            reload();
        });

        cost = new Label("Cost : ");

        grid.getColumnConstraints().add(new ColumnConstraints(300));
        grid.add(restart, cells[0].length, 0);
        grid.add(reload, cells[0].length, 1);
        grid.add(cost, cells[0].length, 2);

        grid.setVgap(10);
        grid.setHgap(10);

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();

    }

    //to refresh screen
    public void remove_path(){
        for (int i = 0; i < node_grid.length; i++)
            for (int j = 0; j < node_grid[i].length; j++)
                cells[i][j].setStyle(getColor(node_grid[i][j].weight));
    }

    //to reload grid
    public void reload(){run();}

    //button events
    public void set(int i, int j){
        if (buttons_clicked == 0) {start = node_grid[i][j]; buttons_clicked++;}
        else if (buttons_clicked == 1) {
            end = node_grid[i][j]; buttons_clicked = 0; a_star(start, end, node_grid);
            //System.out.println(start.id + " " + end.id);
        }
    }

    //return color depending on weight
    public String getColor(int weight){
        if (weight == 1) return "-fx-background-color: White";
        if (weight == 2) return "-fx-background-color: LightGrey";
        if (weight == 3) return "-fx-background-color: DarkGrey";
        else return "-fx-background-color: Black";
    }

    //create a node grid
    public Node[][] create(int x, int y){
        Random r = new Random();
        Node[][] node_grid = new Node[x][y];
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++) {
                node_grid[i][j] = new Node("[" + i + ", " + j + "]", r.nextInt(4) + 1);
                node_grid[i][j].x = i;
                node_grid[i][j].y = j;
            }
        //
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++){
                if (j > 0) node_grid[i][j].nodes.add(node_grid[i][j-1]);
                if (i > 0) node_grid[i][j].nodes.add(node_grid[i-1][j]);
                if (j < y - 1) node_grid[i][j].nodes.add(node_grid[i][j+1]);
                if (i < x - 1) node_grid[i][j].nodes.add(node_grid[i+1][j]);
                if (i > 0 && j > 0) node_grid[i][j].nodes.add(node_grid[i-1][j-1]);
                if (i < x - 1 && j < y - 1) node_grid[i][j].nodes.add(node_grid[i+1][j+1]);
                if (i > 0 && j < y - 1) node_grid[i][j].nodes.add(node_grid[i-1][j+1]);
                if (i < x - 1 && j > 0) node_grid[i][j].nodes.add(node_grid[i+1][j-1]);
            }
        return node_grid;
    }

    //to print the grid
    public static void printGrid(Node[][] node_grid){
        for (int i = 0; i < node_grid.length; i++){
            for (int j = 0; j < node_grid[i].length; j++){
                System.out.print(node_grid[i][j].weight);
            }
            System.out.println();
        }
    }

    //a* algorithm
    public static void a_star(Node start, Node end, Node[][] node_grid){
        String option = JOptionPane.showInputDialog("Choose - Manhattan(m) or Chebyshev(c) or Euclidean(e) to apply.");
        Stopwatch clock = new Stopwatch();
        ArrayList<Node> path = Astar.run(start, end, node_grid, option); int total_cost = 0, t_cost = 0;
        //ArrayList<Node> path1 = Dijkstra.run(start, node_grid.length * node_grid[0].length, end);
        System.out.println(clock.elapsedTime());

        if (path != null){
            for (int i = 0; i < path.size(); i++) {
                cells[path.get(i).x][path.get(i).y].setStyle("-fx-background-color: Red");
                total_cost += path.get(i).weight;
            }
            cost.setText("Cost : " + total_cost);
            /*for (int i = 0; i < path1.size(); i++)
                t_cost += path1.get(i).weight;
            System.out.println(t_cost == total_cost);*/
        }else {
            JOptionPane.showMessageDialog(null, "No viable paths available");
            cost.setText("Cost : Unknown");
        }
    }

}