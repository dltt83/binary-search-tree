import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Tree tree1 = new Tree(new Node(null, null, null, 8));
        Integer[] nodesToAdd = {4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15};
        //Integer[] nodesToAdd = {4, 12, 2, 6, 14, 1, 5, 7, 13, 15};

        System.out.println("--------");
        System.out.println(Arrays.toString(nodesToAdd));

        for (int node : nodesToAdd) {
            tree1.addNode(tree1.getRoot(), new Node(null, null, null, node));
        }

        tree1.setNodeGUIPositions(500, 500, tree1.getRoot());

        tree1.printStat(tree1.getRoot());

        Group rootGroup = getGroup(tree1);

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Tree Example");
        primaryStage.setScene(new Scene(rootGroup, 800, 500));
        primaryStage.show();
    }

    public Group getGroup(Tree tree1) {
        Group thisGroup = new Group();

        for (Node thisNode : tree1.BFS(tree1.getRoot())) {
            String label = String.valueOf(thisNode.getData());
            int x = thisNode.getPosition()[0];
            int y = thisNode.getPosition()[1];

            thisGroup.getChildren().add(new Text(x, y, label));

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

    public static void main(String[] args) {
        launch(args);
    }
}
