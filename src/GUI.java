import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;


import java.util.ArrayList;
import java.util.Arrays;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // instantiate new tree class
        Tree tree1 = new Tree(new Node(null, null, null, 8));

        // create list of nodes to be added
        Integer[] nodesToAdd = {4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15};

        // loop through and add nodes to tree
        for (int node : nodesToAdd) {
            tree1.addNode(tree1.getRoot(), new Node(null, null, null, node));
        }

        // set positions of nodes for where they should be placed on the tree window
        tree1.setNodeGUIPositions(500, 500, tree1.getRoot());

        // create group for tree objects to be displayed
        Group treeGroup = getTreeGroup(tree1);

        // create group for input methods
        Group inputGroup = getInputGroup();

        // create group for all items
        Group rootGroup = new Group();
        rootGroup.getChildren().add(treeGroup);
        rootGroup.getChildren().add(inputGroup);

        // set title for window
        primaryStage.setTitle("Tree Example");
        // set scene and add new group
        primaryStage.setScene(new Scene(rootGroup, 800, 500));
        // show window
        primaryStage.show();
    }

    public Group getTreeGroup(Tree tree1) {
        // create group and add tree nodes to it
        Group thisGroup = new Group();

        // loop through nodes using breadth-first search
        for (Node thisNode : tree1.BFS(tree1.getRoot())) {
            // create new label for node and set positions
            String label = String.valueOf(thisNode.getData());
            int x = thisNode.getPosition()[0];
            int y = thisNode.getPosition()[1];

            // add label to group
            thisGroup.getChildren().add(new Text(x, y, label));

            // check if node has children and add line to them if so
            if (thisNode.getLeftNode() != null) {
                int[] leftNodePosition = thisNode.getLeftNode().getPosition();
                thisGroup.getChildren().add(new Line(x, y+5, leftNodePosition[0] +15, leftNodePosition[1] -10));
            }
            if (thisNode.getRightNode() != null) {
                int[] rightNodePosition = thisNode.getRightNode().getPosition();
                thisGroup.getChildren().add(new Line(x+10, y+5, rightNodePosition[0] -5, rightNodePosition[1] -10));
            }
        }

        return thisGroup;
    }

    public Group getInputGroup() {
        Group thisGroup = new Group();

        // create test input field
        TextField textfield = new TextField();
        textfield.setLayoutX(500);
        textfield.setLayoutY(50);

        // create test button
        Button button = new Button("this label");
        button.setLayoutX(500);
        button.setLayoutY(0);
        button.setOnAction(actionEvent -> {
            System.out.println(textfield.getText());
        });

        thisGroup.getChildren().add(button);
        thisGroup.getChildren().add(textfield);

        return thisGroup;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
