import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;


public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //create variables to easily change size of tree window
        Integer treeWinX = 500;
        Integer treeWinY = 500;

        // instantiate new tree class
        Tree tree1 = new Tree(new Node(null, null, null, 1));

        // create list of nodes to be added
//        Integer[] nodesToAdd = {4, 12, 2, 6, 10, 14};
        Integer[] nodesToAdd = {2, 3, 4, 5, 6, 7};

        // loop through and add nodes to tree
        for (int node : nodesToAdd) {
            tree1.addNode(tree1.getRoot(), new Node(null, null, null, node));
        }

        // create root group for things to be added to
        Group rootGroup = new Group();

        // create group for tree objects to be displayed
        getTreeGroup(tree1, rootGroup, treeWinX, treeWinY);

        // create group for input methods
        getInputGroup(tree1, rootGroup, treeWinX, treeWinY);

        // set title for window
        primaryStage.setTitle("Tree Example");
        // set scene and add new group
        primaryStage.setScene(new Scene(rootGroup, treeWinX+300, treeWinY));
        // show window
        primaryStage.show();
    }

    public void getTreeGroup(Tree tree1, Group rootGroup, Integer treeWinX, Integer treeWinY) {
        // set positions of nodes for where they should be placed on the tree window
        tree1.setNodeGUIPositions(treeWinX, treeWinY, tree1.getRoot());

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

        // add new group to root
        rootGroup.getChildren().add(thisGroup);
    }

    public void updateDisplay(TextField inputField, Group rootGroup, Group buttonGroup, Tree tree1, int treeWinX, int treeWinY) {
        inputField.clear();

        rootGroup.getChildren().remove(0);
        rootGroup.getChildren().remove(0);

        rootGroup.getChildren().add(buttonGroup);
        getTreeGroup(tree1, rootGroup, treeWinX, treeWinY);
    }

    public void getInputGroup(Tree tree1, Group rootGroup, Integer treeWinX, Integer treeWinY) {
        Group thisGroup = new Group();

        // create test input field
        TextField inputField = new TextField();
        inputField.setLayoutX(500);
        inputField.setLayoutY(0);

        // create button to add new node to tree
        Button addButton = new Button("Add Node");
        addButton.setLayoutX(treeWinX);
        addButton.setLayoutY(50);
        addButton.setOnAction(actionEvent -> {
            try {
                int intInput = Integer.parseInt(inputField.getText());
                tree1.addNode(tree1.getRoot(), new Node(null, null, null, intInput));

                updateDisplay(inputField, rootGroup, thisGroup, tree1, treeWinX, treeWinY);
            } catch (NumberFormatException ex) {
                System.out.println("Not a number");
            }
        });

        // create button to remove nodes from tree
        Button removeButton = new Button("Remove Node");
        removeButton.setLayoutX(treeWinX);
        removeButton.setLayoutY(100);
        removeButton.setOnAction(actionEvent -> {
            try {
                int intInput = Integer.parseInt(inputField.getText());
                tree1.removeNode(tree1.getRoot(), intInput);

                updateDisplay(inputField, rootGroup, thisGroup, tree1, treeWinX, treeWinY);
            } catch (NumberFormatException ex) {
                System.out.println("Not a number");
            }
        });

        // create button to rotate about pivot
        Button leftRotateButton = new Button("Rotate left on pivot");
        leftRotateButton.setLayoutX(treeWinX);
        leftRotateButton.setLayoutY(150);
        leftRotateButton.setOnAction(actionEvent -> {
            try {
                int intInput = Integer.parseInt(inputField.getText());
                tree1.rotateLeft(intInput);

                updateDisplay(inputField, rootGroup, thisGroup, tree1, treeWinX, treeWinY);
            } catch (NumberFormatException ex) {
                System.out.println("Not a number");
            }
        });

        // create button to rotate about pivot
        Button rightRotateButton = new Button("Rotate right on pivot");
        rightRotateButton.setLayoutX(treeWinX);
        rightRotateButton.setLayoutY(200);
        rightRotateButton.setOnAction(actionEvent -> {
            try {
                int intInput = Integer.parseInt(inputField.getText());
                tree1.rotateRight(intInput);

                updateDisplay(inputField, rootGroup, thisGroup, tree1, treeWinX, treeWinY);
            } catch (NumberFormatException ex) {
                System.out.println("Not a number");
            }
        });

        // create button to rotate about pivot
        Button balanceButton = new Button("Balance Tree");
        balanceButton.setLayoutX(treeWinX);
        balanceButton.setLayoutY(300);
        balanceButton.setOnAction(actionEvent -> {
            tree1.balance(tree1.getRoot());

            updateDisplay(inputField, rootGroup, thisGroup, tree1, treeWinX, treeWinY);
        });

        thisGroup.getChildren().add(addButton);
        thisGroup.getChildren().add(removeButton);
        thisGroup.getChildren().add(leftRotateButton);
        thisGroup.getChildren().add(rightRotateButton);
        thisGroup.getChildren().add(balanceButton);
        thisGroup.getChildren().add(inputField);

        // add group to root
        rootGroup.getChildren().add(thisGroup);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
