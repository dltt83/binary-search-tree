import java.util.Arrays;

public class Node {
    private Node leftNode;
    private Node rightNode;
    private Node parent;
    private int data;
    private int[] position = {0, 0};

    // constructor
    public Node(Node givenLeft, Node givenRight, Node givenParent, int givenData){
        leftNode = givenLeft;
        rightNode = givenRight;
        parent = givenParent;
        data = givenData;
    }

    // get/set methods
    public int getData() {return data;}

    public Node getLeftNode() {return leftNode;}

    public Node getRightNode() {return rightNode;}

    public Node getParent() {return parent;}

    public int[] getPosition() {return position;}

    public void setPosition(int[] newPosition) {position = newPosition;}

    public void setLeftNode(Node newLeft) {leftNode = newLeft;}

    public void setRightNode(Node newRight) {rightNode = newRight;}

    public void setParent(Node newParent) {parent = newParent;}

    // other methods
    public boolean isLeaf() {
        return (leftNode == null && rightNode == null);
    }

    public void updatePosition() {
        // update position of node for gui
        if (parent.getData() > data) {
            if (getParent().getParent() != null) {
                int difference = Math.abs(getParent().getPosition()[0] - getParent().getParent().getPosition()[0]);
                position[0] = getParent().getPosition()[0] - difference/2;
            } else {
                position[0] = parent.getPosition()[0] / 2;
            }
        } else {
            if (getParent().getParent() != null) {
                int difference = Math.abs(getParent().getPosition()[0] - getParent().getParent().getPosition()[0]);
                position[0] = getParent().getPosition()[0] + difference/2;
            } else {
                position[0] = (int) Math.round(parent.getPosition()[0] + (parent.getPosition()[0]) * 0.5);
            }
        }

        position[1] = (getNodeDepth() + 1) * (parent.getPosition()[1] / (parent.getNodeDepth() + 1));
    }

    public int getNodeHeight() {
        // get height of node in tree
        if (isLeaf()) {
            return 1;
        } else {
            int leftHeight;
            if (getLeftNode() == null) {
                leftHeight = 0;
            } else {
                leftHeight = getLeftNode().getNodeHeight();
            }
            int rightHeight;
            if (getRightNode() == null) {
                rightHeight = 0;
            } else {
                rightHeight = getRightNode().getNodeHeight();
            }

            if (leftHeight > rightHeight) {
                return leftHeight + 1;
            } else {
                return rightHeight + 1;
            }
        }
    }

    public int getNodeDepth() {
        // get depth of node in tree
        if (getParent() == null) {
            return 0;
        } else {
            return (getParent().getNodeDepth() + 1);
        }
    }

    public void printStat() {
        // print out node info
        System.out.println("-----------");
        System.out.println("For node:  " + data);

        // check if node is root now of tree
        if (parent == null) {
            System.out.println("ROOT");
        } else {
            System.out.println("Parent:  " + parent.getData());
        }

        // print out info about lower nodes
        if (isLeaf()) {
            System.out.println("LEAF");
        } else {
            if (leftNode != null) {
                System.out.println("Left:  " + leftNode.getData());
            }
            if (rightNode != null) {
                System.out.println("Right:  " + rightNode.getData());
            }
        }

        System.out.println("Depth:  " + getNodeDepth());
        System.out.println("Height:  " + getNodeHeight());
        System.out.println(Arrays.toString(position));
        System.out.println("----------- \n");
    }
}
