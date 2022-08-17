import java.util.*;

public class Tree {
    private Node root;

    // constructor
    public Tree(Node givenRoot) {
        root = givenRoot;
    }

    // get/set methods
    public Node getRoot() {return root;}

    // other methods
    public void addNode(Node currentNode, Node newNode) {
        // check which node is larger for which order they need to go in
        if (newNode.getData() < currentNode.getData()) {
            // check if node is end of branch
            if (currentNode.getLeftNode() == null) {
                // add new node to end of branch
                currentNode.setLeftNode(newNode);
                newNode.setParent(currentNode);

            } else {
                // recursively call new add routine
                addNode(currentNode.getLeftNode(), newNode);
            }

        } else if (newNode.getData() > currentNode.getData()) {
            // check if node is end of branch
            if (currentNode.getRightNode() == null) {
                //add new node to end of branch
                currentNode.setRightNode(newNode);
                newNode.setParent(currentNode);

            } else {
                // recursively call new add routine
                addNode(currentNode.getRightNode(), newNode);
            }

        } else {
            // print out statement for node already being in tree
            System.out.println("Node is already in tree");
        }
    }

    public void printStat(Node currentNode) {
        //print out current node
        currentNode.printStat();

        // recursively prints out child nodes
        if (currentNode.getLeftNode() != null) {
            printStat(currentNode.getLeftNode());
        }
        if (currentNode.getRightNode() != null) {
            printStat(currentNode.getRightNode());
        }
    }

    public void setNodeGUIPositions(int maxX, int maxY, Node rootNode) {
        // set root node to be at top of page in the middle
        rootNode.setPosition(new int[]{maxX / 2, (maxY / (rootNode.getNodeHeight() + 1))});

        // loop through nodes and update position of each
        for (Node currentNode : BFS(root)) {
            if (currentNode != root) {
                currentNode.updatePosition();
            }
        }
    }

    public Node getNodeByData(Node currentNode, int dataToFind) {
        // searches down tree by looking through each node and comparing
        if (dataToFind < currentNode.getData()) {
            // checks of current node is a leaf
            if (currentNode.getLeftNode() != null) {
                // recursively searches subtree
                return getNodeByData(currentNode.getLeftNode(), dataToFind);
            } else {
                // returns null as data is not in tree
                return null;
            }
        } else if (dataToFind > currentNode.getData()) {
            if (currentNode.getRightNode() != null) {
                // recursively searches down subtree
                return getNodeByData(currentNode.getRightNode(), dataToFind);
            } else {
                return null;
            }
        } else {
            // data == current node so value is found
            return currentNode;
        }
    }

    public Node getSmallest(Node currentNode) {
        // recursively finds the smallest node in given subtree
        if (currentNode.getLeftNode() != null) {
            return getSmallest(currentNode.getLeftNode());
        } else {
            return currentNode;
        }
    }

    public Node getLargest(Node currentNode) {
        // recursively finds the largest node in given subtree
        if (currentNode.getRightNode() != null) {
            return getLargest(currentNode.getRightNode());
        } else {
            return currentNode;
        }
    }

    public ArrayList<Node> BFS(Node currentNode) {
        // gets list of nodes in order of a breath first search

        // creates queue and list for holding data while searching tree
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
        // finds and removes node from tree

        if (currentNode.getData() == dataToRemove) {
            if (currentNode.isLeaf()) {
                if (currentNode != root) {
                    // remove node if node is leaf
                    Node parent = currentNode.getParent();

                    if (currentNode.getData() < parent.getData()) {
                        parent.setLeftNode(null);
                    } else {
                        parent.setRightNode(null);
                    }
                } else {
                    System.out.println("Cannot remove when root is only node");
                }
            } else if (currentNode == root) {
                if (currentNode.getRightNode() != null && currentNode.getLeftNode() != null) {
                    // remove node if node has 2 children

                    Node replacement = getSmallest(currentNode.getRightNode());
                    Node replaceRight = replacement.getRightNode();
                    Node currentRight = currentNode.getRightNode();

                    if (currentRight.getLeftNode() == null) {
                        // for when replacement has no left node
                        replacement.setParent(null);

                        root = replacement;

                        replacement.setLeftNode(currentNode.getLeftNode());
                        replacement.getLeftNode().setParent(replacement);
                    } else {
                        // for when replacement has 2 children

                        replacement.setParent(null);

                        root = replacement;

                        replacement.setLeftNode(currentNode.getLeftNode());
                        replacement.getLeftNode().setParent(replacement);

                        currentRight.setLeftNode(replaceRight);
                        if (replaceRight != null) {
                            replaceRight.setParent(currentRight);
                        }

                        replacement.setRightNode(currentRight);
                        currentRight.setParent(replacement);
                    }
                } else {
                    // root only has 1 child
                    if (currentNode.getLeftNode() == null) {
                        root = currentNode.getRightNode();
                        root.setParent(null);
                    } else {
                        root = currentNode.getLeftNode();
                        root.setParent(null);
                    }
                }
            } else if (currentNode.getRightNode() != null && currentNode.getLeftNode() != null) {
                // remove node if node has 2 children

                Node replacement = getSmallest(currentNode.getRightNode());
                Node parent = currentNode.getParent();
                Node replaceRight = replacement.getRightNode();
                Node currentRight = currentNode.getRightNode();


                if (currentRight.getLeftNode() == null) {
                    // for when replacement has no left node
                    replacement.setParent(parent);

                    if (replacement.getData() < parent.getData()) {
                        parent.setLeftNode(replacement);
                    } else {
                        parent.setRightNode(replacement);
                    }

                    replacement.setLeftNode(currentNode.getLeftNode());
                    replacement.getLeftNode().setParent(replacement);
                } else {
                    // for when replacement has 2 children

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
                }

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
        // current node not to be removed so recursively find
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
        // performs left rotation on given pivot

        Node pointNode = getNodeByData(root, point);
        Node replacement = pointNode.getRightNode();
        Node parent = pointNode.getParent();

        if (replacement != null) {
            Node middle = replacement.getLeftNode();

            pointNode.setParent(replacement);
            replacement.setLeftNode(pointNode);

            pointNode.setRightNode(middle);
            if (middle != null) {
                middle.setParent(pointNode);
            }

            if (root == pointNode) {
                root = replacement;
                replacement.setParent(null);
            } else {
                replacement.setParent(parent);
                if (replacement.getData() < parent.getData()) {
                    parent.setLeftNode(replacement);
                } else {
                    parent.setRightNode(replacement);
                }            }
        } else {
            System.out.println("Cannot rotate");
        }
    }

    public void rotateRight(int point) {
        // performs right rotation on given pivot

        Node pointNode = getNodeByData(root, point);
        Node replacement = pointNode.getLeftNode();
        Node parent = pointNode.getParent();

        if (replacement != null) {
            Node middle = replacement.getRightNode();

            pointNode.setParent(replacement);
            replacement.setRightNode(pointNode);

            pointNode.setLeftNode(middle);
            if (middle != null) {
                middle.setParent(pointNode);
            }

            if (root == pointNode) {
                root = replacement;
                replacement.setParent(null);
            } else {
                replacement.setParent(parent);
                if (replacement.getData() < parent.getData()) {
                    parent.setLeftNode(replacement);
                } else {
                    parent.setRightNode(replacement);
                }
            }
        } else {
            System.out.println("Cannot rotate");
        }
    }

    public void balance(Node currentNode) {
        Node leftNode = currentNode.getLeftNode();
        Node rightNode = currentNode.getRightNode();

        // check for both nodes
        if (leftNode != null && rightNode != null) {
            if (Math.abs(leftNode.getNodeHeight() - rightNode.getNodeHeight()) > 1) {

                if (leftNode.getNodeHeight() > rightNode.getNodeHeight()) {
                    rotateRight(currentNode.getData());
                } else {
                    rotateLeft(currentNode.getData());
                }

                balance(currentNode);
            } else {
                balance(leftNode);
                balance(rightNode);
            }
        } else {
            if (leftNode != null) {
                if (leftNode.getNodeHeight() > 1) {
                    rotateRight(currentNode.getData());
                    balance(currentNode);
                }
            } else if (rightNode != null) {
                if (rightNode.getNodeHeight() > 1) {
                    rotateLeft(currentNode.getData());
                    balance(currentNode);
                }
            }
        }

        if (leftNode != null) {
            balance(leftNode);
        }
        if (rightNode != null) {
            balance(rightNode);
        }
    }


    // UI methods

    public static void treeUI() {
        // initialise scanner object for user input
        Scanner sc = new Scanner(System.in);

        // get int from user for data for root node
        System.out.println("Enter data for root node:");
        int rootData = sc.nextInt();
        sc.nextLine();

        // initialise new tree using data for root node
        Tree tree1 = new Tree(new Node(null, null, null, rootData));

        // loop until user stops
        boolean runLoop = true;

        while (runLoop) {
            // give user menu options
            tree1.printMenu();

            // get menu option input from user
            String menuOption = sc.nextLine();

            // switch statement for user options
            switch (menuOption) {
                case "1" -> // user enters 1 for print stat of whole tree
                    tree1.printStat(tree1.getRoot());
                case "2" -> {   // user enters 2 for adding new node to tree
                    // get input from user
                    System.out.println("Enter data for new node:");
                    int newNodeData = sc.nextInt();
                    sc.nextLine();

                    //make new node and add to tree
                    tree1.addNode(tree1.getRoot(), new Node(null, null, null, newNodeData));
                }

                case "3" -> {   // user enters 3 to search for node in tree
                    // get data to search for from user input
                    System.out.println("Enter data to search for: ");
                    int dataToFind = sc.nextInt();
                    sc.nextLine();

                    // search for data
                    Node nodeFound = tree1.getNodeByData(tree1.getRoot(), dataToFind);

                    // check if node was found before printing out result
                    if (nodeFound == null) {
                        System.out.println("Node was not found in tree");
                    } else {
                        // if node is found in tree print stat for node found
                        nodeFound.printStat();
                    }

                }

                case "4" -> {
                    System.out.println("Enter data to remove: ");
                    int dataToRemove = sc.nextInt();
                    sc.nextLine();

                    // remove data
                    tree1.removeNode(tree1.getRoot(), dataToRemove);

                    System.out.println("Data removed");
                }

                case "5" -> {
                    System.out.println("Enter data for point of rotation: ");
                    int dataToRotate = sc.nextInt();
                    sc.nextLine();

                    // rotate left about point
                    tree1.rotateLeft(dataToRotate);
                }

                case "6" -> {
                    System.out.println("Enter data for point of rotation: ");
                    int dataToRotate = sc.nextInt();
                    sc.nextLine();

                    // rotate right about point
                    tree1.rotateRight(dataToRotate);
                }

                case "0" ->
                    // set bool as false to break out of while loop
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
        System.out.println("0. eXit \n");
    }

    public static void main(String[] args) {
        //call ui method for user to interact
        treeUI();
    }
}
