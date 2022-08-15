import java.util.*;

public class Tree {
    private Node root;

    //constructor
    public Tree(Node givenRoot) {
        root = givenRoot;
    }

    //get/set methods
    public Node getRoot() {return root;}

    //other methods
    public void addNode(Node currentNode, Node newNode) {
        //check which node is larger for which order they need to go in
        if (newNode.getData() < currentNode.getData()) {
            //check if node is end of branch
            if (currentNode.getLeftNode() == null) {
                //add new node to end of branch
                currentNode.setLeftNode(newNode);
                newNode.setParent(currentNode);

            } else {
                //recursively call new add routine
                addNode(currentNode.getLeftNode(), newNode);
            }

        } else if (newNode.getData() > currentNode.getData()) {
            //check if node is end of branch
            if (currentNode.getRightNode() == null) {
                //add new node to end of branch
                currentNode.setRightNode(newNode);
                newNode.setParent(currentNode);

            } else {
                //recursively call new add routine
                addNode(currentNode.getRightNode(), newNode);
            }

        } else {
            //print out statement for node already being in tree
            System.out.println("Node is already in tree");
        }
    }

    public void printStat(Node currentNode) {
        //print out current node
        currentNode.printStat();

        //recursively prints out child nodes
        if (currentNode.getLeftNode() != null) {
            printStat(currentNode.getLeftNode());
        }
        if (currentNode.getRightNode() != null) {
            printStat(currentNode.getRightNode());
        }
    }

    public void setNodeGUIPositions(int maxX, int maxY, Node rootNode) {
        root.setPosition(new int[]{maxX / 2, (maxY / (root.getNodeHeight() + 1))});

        for (Node currentNode : BFS(root)) {
            if (currentNode != root) {
                currentNode.updatePosition();
            }
        }
    }

    public Node getNodeByData(Node currentNode, int dataToFind) {
        //searches down tree by looking through each node and comparing
        if (currentNode.getData() < dataToFind) {
            //checks of current node is leaf
            if (currentNode.getLeftNode() != null) {
                //recursively searches subtree
                return getNodeByData(currentNode.getLeftNode(), dataToFind);
            } else {
                //returns null as data is not in tree
                return null;
            }
        } else if (currentNode.getData() > dataToFind) {
            if (currentNode.getRightNode() != null) {
                //recursively searches down subtree
                return getNodeByData(currentNode.getRightNode(), dataToFind);
            } else {
                return null;
            }
        } else {
            //data == current node so value is found
            return currentNode;
        }
    }

    public Node getSmallest(Node currentNode) {
        if (currentNode.getLeftNode() != null) {
            return getSmallest(currentNode.getLeftNode());
        } else {
            return currentNode;
        }
    }

    public Node getLargest(Node currentNode) {
        if (currentNode.getRightNode() != null) {
            return getLargest(currentNode.getRightNode());
        } else {
            return currentNode;
        }
    }

    public ArrayList<Node> BFS(Node currentNode) {
        Queue<Node> q = new LinkedList<>();
        ArrayList<Node> list = new ArrayList<>();

        q.add(currentNode);

        while (q.size() > 0) {
            Node v = q.remove();

            if (v.getLeftNode() != null) {
                q.add(v.getLeftNode());
            }

            if (v.getRightNode() != null) {
                q.add(v.getRightNode());
            }

            list.add(v);
        }

        return list;
    }

    public void removeNode(Node currentNode, int dataToRemove) {
        if (currentNode.getData() == dataToRemove) {
            if (currentNode.isLeaf()) {
                // remove node if node is leaf
                Node parent = currentNode.getParent();

                if (currentNode.getData() < parent.getData()) {
                    parent.setLeftNode(null);
                } else {
                    parent.setRightNode(null);
                }

            } else if (currentNode.getRightNode() != null && currentNode.getLeftNode() != null) {
                // remove node if node has 2 children

                Node replacement = getSmallest(currentNode.getRightNode());
                Node parent = currentNode.getParent();
                Node replaceRight = replacement.getRightNode();
                Node currentRight = currentNode.getRightNode();

                replacement.setParent(parent);

                if (replacement.getData() < parent.getData()) {
                    parent.setLeftNode(replacement);
                } else {
                    parent.setRightNode(replacement);
                }

                replacement.setLeftNode(currentNode.getLeftNode());
                replacement.getLeftNode().setParent(replacement);

                currentRight.setLeftNode(replaceRight);
                if (replaceRight != null) {
                    replaceRight.setParent(currentRight);
                }

                replacement.setRightNode(currentRight);
                currentRight.setParent(replacement);

            } else {
                // remove node if node has 1 child
                Node parent = currentNode.getParent();

                Node replacement;
                if (currentNode.getLeftNode() != null) {
                    replacement = currentNode.getLeftNode();
                } else {
                    replacement = currentNode.getRightNode();
                }

                if (replacement.getData() < parent.getData()) {
                    parent.setLeftNode(replacement);
                } else {
                    parent.setRightNode(replacement);
                }

                replacement.setParent(parent);
            }

        } else if (dataToRemove < currentNode.getData()) {
            if (currentNode.getLeftNode() != null) {
                removeNode(currentNode.getLeftNode(), dataToRemove);
            }
        } else if (dataToRemove > currentNode.getData()) {
            if (currentNode.getRightNode() != null) {
                removeNode(currentNode.getRightNode(), dataToRemove);
            }
        }
    }

    public void rotateLeft(int point) {
        Node pointNode = this.getNodeByData(this.root, point);
        pointNode.printStat();
        Node replacement = pointNode.getRightNode();
        Node middle = replacement.getLeftNode();

        pointNode.setParent(replacement);
        replacement.setLeftNode(pointNode);

        pointNode.setRightNode(middle);
        middle.setParent(pointNode);
    }

    public void rotateRight(int point) {
        Node pointNode = this.getNodeByData(this.root, point);
        Node replacement = pointNode.getLeftNode();
        Node middle = replacement.getRightNode();

        pointNode.setParent(replacement);
        replacement.setRightNode(pointNode);

        pointNode.setLeftNode(middle);
        middle.setParent(pointNode);
    }


    //UI methods

    public static void treeUI() {
        //initialise scanner object for user input
        Scanner sc = new Scanner(System.in);

        //get int from user for data for root node
        System.out.println("Enter data for root node:");
        int rootData = sc.nextInt();
        sc.nextLine();

        //initialise new tree using data for root node
        Tree tree1 = new Tree(new Node(null, null, null, rootData));

        //loop until user stops
        boolean runLoop = true;

        while (runLoop) {
            //give user menu options
            tree1.printMenu();

            //get menu option input from user
            String menuOption = sc.nextLine();

            //switch statement for user options
            switch (menuOption) {
                case "1" -> //user enters 1 for print stat of whole tree
                    tree1.printStat(tree1.getRoot());
                case "2" -> {   //user enters 2 for adding new node to tree
                    //get input from user
                    System.out.println("Enter data for new node:");
                    int newNodeData = sc.nextInt();
                    sc.nextLine();

                    //make new node and add to tree
                    tree1.addNode(tree1.getRoot(), new Node(null, null, null, newNodeData));
                }

                case "3" -> {   //user enters 3 to search for node in tree
                    //get data to search for from user input
                    System.out.println("Enter data to search for: ");
                    int dataToFind = sc.nextInt();
                    sc.nextLine();

                    //search for data
                    Node nodeFound = tree1.getNodeByData(tree1.getRoot(), dataToFind);

                    //check if node was found before printing out result
                    if (nodeFound == null) {
                        System.out.println("Node was not found in tree");
                    } else {
                        //if node is found in tree print stat for node found
                        nodeFound.printStat();
                    }

                }

                case "4" -> {
                    System.out.println("Enter data to remove: ");
                    int dataToRemove = sc.nextInt();
                    sc.nextLine();

                    //remove data
                    tree1.removeNode(tree1.getRoot(), dataToRemove);

                    System.out.println("Data removed");
                }

                case "5" -> {
                    System.out.println("Enter data for point of rotation: ");
                    int dataToRotate = sc.nextInt();
                    sc.nextLine();

                    //rotate left about point
                    tree1.rotateLeft(dataToRotate);
                }

                case "6" -> {
                    System.out.println("Enter data for point of rotation: ");
                    int dataToRotate = sc.nextInt();
                    sc.nextLine();

                    //rotate right about point
                    tree1.rotateRight(dataToRotate);
                }

                case "0" ->
                    //set bool to false to break out of while loop
                    runLoop = false;
                default ->
                    System.out.println("Enter valid menu option");
            }
        }
    }

    public void printMenu() {
        //print out menu options for user
        System.out.println("\n \n---MENU---");
        System.out.println("1. PrintStat for whole tree");
        System.out.println("2. Add new node");
        System.out.println("3. Search for node");
        System.out.println("4. Remove node");
        System.out.println("5. Rotate left");
        System.out.println("6. Rotate right");
        System.out.println("7. Add multiple Nodes");
        System.out.println("8. Display GUI");
        System.out.println("0. eXit \n");
    }

    public static void main(String[] args) {
        //call ui method for user to interact
        treeUI();
    }
}
