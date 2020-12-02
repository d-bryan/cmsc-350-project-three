/*******************************************************************************
 * File: BinaryTree.java
 * Author: Dylan Bryan
 * Date: 11/26/20, 6:45 AM
 * Project: cmsc-350-project-three
 * Purpose: Binary Tree Class which contains all of the necessary
 * interactions to be used in the main GUI portion of the program
 ******************************************************************************/

package project;

import java.util.*;
import java.util.regex.Pattern;

public class BinaryTree<T extends Comparable<T>> {
  private String input;
  private int parensCounter = 0;
  private int leftHeight = 0;
  private int rightHeight = 0;
  private final Comparator<T> comparator;
  private StringBuilder output = new StringBuilder();
  private Node<T> root;
  private final String REGEX = "^[0-9|A-Z|a-z|\\(|\\)]+$";

  /**
   * Nested Node class for creating binary tree
   *
   * @param <T> Generic Data
   */
  private static class Node<T> {
    private Node<T> left;
    private T data;
    private Node<T> right;

    /**
     * Node constructor
     * @param data Data for node
     */
    public Node(T data) {
      this.left = null;
      this.data = data;
      this.right = null;
    } // end constructor

    // setter and getter for data
    public void setData(T data) {
      this.data = data;
    } // end setData method
    public T getData() {
      return data;
    } // end getData method

    // setter and getter for left
    public void setLeft(Node<T> leftNode) {
      this.left = leftNode;
    } // end setLeft method
    public Node<T> getLeft() {
      return left;
    } // end getLeft method

    // setter and getter for right
    public void setRight(Node<T> rightNode) {
      this.right = rightNode;
    } // end setRight method
    public Node<T> getRight() {
      return right;
    } // end getRight method
  } // end Node class

  /**
   * Binary Tree constructor, creates a new binary tree and utilizes
   * most of the six public methods available to ensure the GUI has
   * the necessary information for user input
   * @param input STRING Binary tree representation
   * @throws InvalidTreeSyntax
   */
  public BinaryTree(String input) throws InvalidTreeSyntax {
    this.input = input;
    this.comparator = Comparator.naturalOrder();
    boolean matches = Pattern.compile(REGEX).matcher(input).lookingAt();
    if (!matches) { // check if any input is invalid before parsing to add to tree
      throw new InvalidTreeSyntax("Characters in Tree are not Valid.");
    } // end if statement
    // tokenize the input string
    StringTokenizer tokenizer = new StringTokenizer(input, "()", true);
    // array lists to contain the parenthesis
    ArrayList<String> leftParenthesis = new ArrayList<>();
    ArrayList<String> rightParenthesis = new ArrayList<>();
    // set the root node to result of make node
    makeNode(tokenizer, leftParenthesis, rightParenthesis);
    // todo: remove the print statements
    System.out.println(height()); // get the height of root
    printItems(root); // print out items
    System.out.println(" ");
    System.out.println(nodes());
    System.out.println(isBalanced());
    System.out.println(inorder());
    System.out.println(isProper());
    System.out.println(calculateFullRows(2));
    System.out.println(isFull());
//    printItems(root);
  } // end BinaryTree constructor

  private boolean printItems(Node<T> node) {
    if (node == null) {
      return false;
    } // end if statement
    printItems(node.getLeft());
    System.out.print(node.data + " ");
    printItems(node.getRight());
    return false;
  } // end printItems method
//(S(5(d(2))(3))R(e)(3))

  /**
   * Creates a new binary tree and set the data
   * for the root element
   * @param tokenizer StringTokenizer
   * @param leftParenthesis ArrayList for left parenthesis
   * @param rightParenthesis ArrayList for right parenthesis
   */
  private void makeNode(StringTokenizer tokenizer, ArrayList<String> leftParenthesis, ArrayList<String> rightParenthesis) {
    String token = nextNonBlank(tokenizer);
    // if there are no more characters end
    if (!tokenizer.hasMoreElements()) return;
    // if the item is a usable character add to tree
    if (isCharacter(token)) addNode((T) token);
    if (token.equals("(")) {
      ++parensCounter;
      leftParenthesis.add(token);
    } // end if statement
    if(token.equals(")")) {
      --parensCounter;
      rightParenthesis.add(token);
    } // end if statement
    // recursively add more items to binary tree
    makeNode(tokenizer, leftParenthesis, rightParenthesis);
  } // end makeNode method

  /**
   * Adds element to root, if root is null
   * then that element becomes root,
   * otherwise a recursive call adds the
   * element to the rest of tree based on element
   * @param element Element
   */
  private void addNode(T element) {
    // if root is null add element as data
    // point with null left and right tree
    if (root == null) {
      root = new Node<>(element);
      return;
    } // end if statement
    // otherwise recursively add elements to the root
    // of the tree
    recursiveAdd(element, root);
  } // end addNode method

  /**
   * Recursive call to compare element to rest of tree
   * @param element Element
   * @param node Node
   */
  private void recursiveAdd(T element, Node<T> node) {
    // if item is greater than root element
    if (element.compareTo(node.data) > 0) {
      if (node.left != null) {
        recursiveAdd(element, node.left);
      } else {
        node.left = new Node<>(element);
      } // end if/else statement
    // if item is less than root element
    } else if (element.compareTo(node.data) <= 0) {
      if (node.right != null) {
        recursiveAdd(element, node.right);
      } else {
        node.right = new Node<>(element);
      } // end if/else statement
    } // end if/else if statement
  } // end recursiveAdd method

  /**
   * Check that the token passed through is a character and not parenthesis or some other item, this
   * way it can be used as a node in the binary tree
   *
   * @param token STRING token
   * @return BOOLEAN true/false
   */
  private boolean isCharacter(String token) {
    String CHARACTER_REGEX = "^[0-9|A-Z|a-z]+$";
    return Pattern.compile(CHARACTER_REGEX).matcher(token).lookingAt();
  } // end isCharacter method

  /**
   * Checks the user input to ensure there are no spaces in the Tree Expression
   *
   * @param tokenizer TOKENIZER String token
   * @return STRING token
   */
  private String nextNonBlank(StringTokenizer tokenizer) {
    String token = ""; // current item
    if (tokenizer.hasMoreTokens()) {
      while (tokenizer.hasMoreTokens()) {
        token = tokenizer.nextToken();
        if (!token.equals(" ")) {
          return token;
        } // end if statement
      } // end while loop
    }

    return token;
  } // end nextNonBlank method

  // setter for left height
  public void setLeftHeight(int leftHeight) {
    this.leftHeight = leftHeight;
  } // end setLeftHeight method

  // setter for right height
  public void setRightHeight(int rightHeight) {
    this.rightHeight = rightHeight;
  } // end setRightHeight method

  /**
   * Calculates the total number of nodes that
   * a row would have
   * @param n DOUBLE current row height
   * @return DOUBLE max number of nodes if full
   */
  private double calculateFullRows(double n) {
    if (n == 0) return 1;
    else return (Math.pow(2, n) + calculateFullRows(n - 1));
  } // end calculateFullRows method

  /**
   * Checks whether the tree is balanced with
   * a recursive call to isBalanced and height
   * @return BOOLEAN true/false
   */
  public boolean isBalanced() {
    if (root != null) return isBalanced(leftHeight, rightHeight);
    return false;
  } // end isBalanced method

  /**
   * Recursive call to isBalanced to check whether
   * Tree is balanced, must not have more than one
   * in height
   * @param leftHeight INTEGER left height
   * @param rightHeight INTEGER right height
   * @return BOOLEAN true/false value
   */
  private boolean isBalanced(int leftHeight, int rightHeight) {

    // set the max difference between heights to the difference
    int maxDifference = leftHeight - rightHeight;

    // only allow a true return for a max height difference of one
    switch (maxDifference) {
      case 0:
      case -1:
      case 1:
        return true;
      default:
        return false;
    } // end switch statement
  } // end isBalanced method

  public boolean isFull() {
     // todo: implement the isFull method
    if (root != null) return isFull(root);
    return false;
  } // end isFull method

  private boolean isFull(Node<T> node) {

//    if (node.left != null && node.right != null) {
//      result = true;
//    }
//    if (node != null) {
//      isFull(node.left, result);
//      isFull(node.right, result);
//    }
//
//    return result;

    // function to place two to the exponent of the current row height
//    Function<Double, Double> calculateBottomRow = (row) -> Math.pow(2, row);
    // reset the current row height before recalculating
    setLeftHeight(0);
    setRightHeight(0);
    // get the current row height and total number of nodes
    double currentRow = height(node);
    int totalNodes = nodes(node);
    // apply function call to calculate what a full tree would be using a factorial
    currentRow = calculateFullRows(currentRow - 1);
    // if the difference is 0, its full, otherwise it is not
    return ((currentRow - totalNodes) == 0);
  } // end isFull method

  /**
   * Checks whether the tree is proper containing only
   * 0 or 2 children within its nodes
   * @return BOOLEAN true/false
   */
  public boolean isProper() {
    if (root != null) return isProper(root);
    return false;
  } // end isProper method

  /**
   * Recursive call to isProper to check whether the
   * current tree contains only 0 or 2 children
   * @param node NODE root
   * @return BOOLEAN true/false
   */
  private boolean isProper(Node<T> node) {
    // get the total number of nodes from nodes method
    int totalNodes = nodes(node);
    // if there are an even number of nodes it is not proper
    return totalNodes % 2 != 0;
  } // end isProper method

  /**
   * Initial call to the height method
   * @return INTEGER height of tree
   */
  public int height() {
    // reset height
    setLeftHeight(0);
    setRightHeight(0);
    // recursive call to height
//    if (root != null) return height(root, leftHeight, rightHeight);
    if (root != null) return (height(root) - 1);
    return 0;
  } // end height method

//  @param leftHeight INTEGER left tree height
//   * @param rightHeight INTEGER left tree height

  /**
   * Recursive height method to calculate height of tree
   * @param node NODE root of tree
   * @return INTEGER height of tree
   */
  private int height(Node<T> node) {

    if (node == null) {
      return 0;
    } else {
      int left = height(node.left);
      int right = height(node.right);

      // set the height for later use
//      setLeftHeight(left + 1);
//      setRightHeight(right + 1);

      if (left > right) {
        setLeftHeight(left);
        setRightHeight(right);
        return (left + 1);
      } else {
        return (right + 1);
      } // end if/else statement

    } // end if/else statement

//    if (node == null) {
//      setLeftHeight(leftHeight);
//      setRightHeight(rightHeight);
//      return 0;
//    } else {
//      if (node.left != null) height(node.left, leftHeight += 1, rightHeight);
//      if (node.right != null) height(node.right, leftHeight, rightHeight += 1);
//      setLeftHeight(leftHeight);
//      setRightHeight(rightHeight);
//    }
//    return 1 + (Math.max(leftHeight, rightHeight));


//    if (node == null) return 0;
//    // set the left and right height
//    setLeftHeight(leftHeight);
//    setRightHeight(rightHeight);
//    // recursive calls
//    height(node.left, leftHeight += 1, rightHeight);
//    height(node.right, leftHeight, rightHeight += 1);
////    (A(G(R(F(j)(1))))(z(5))) todo: remove this
//
//    return 1 + Math.max(leftHeight, rightHeight);
  } // end height method

  /**
   * Calls the private method nodes to count the number of nodes in the tree
   *
   * @return INTEGER number of nodes
   */
  public int nodes() {
    return nodes(root);
  } // end nodes method

  /**
   * Recursively counts the number of nodes in the tree
   *
   * @param node Root of the tree to start with
   * @return INTEGER number of nodes in the tree
   */
  private int nodes(Node<T> node) {
    // if the parent has no children return 0
    if (node == null) {
      return 0;
      // recursively travel down the tree
    } else {
      return 1 + nodes(node.left) + nodes(node.right);
    } // end if/else if/ else statement
  } // end nodes method

  /**
   * Creates a new inorder traversal of the tree
   * @return ArrayList list
   */
  public ArrayList<T> inorder() {
    ArrayList<T> list = new ArrayList<>();
    inorder(root, list);
    return list;
  } // end inorder method

  /**
   * Recursive call to traverse the tree inorder
   * @param node NODE root
   * @param list ArrayList list
   */
  private void inorder(Node<T> node, ArrayList<T> list) {
    if (node == null) return;
    // traverse the left side of list
    inorder(node.left, list);
    // add items to the list
    list.add(node.data);
    // traverse the right side of list
    inorder(node.right, list);
  } // end inorder method

} // end BinaryTree class
