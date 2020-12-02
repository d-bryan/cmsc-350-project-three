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
  private int parensCounter = 0;
  private int leftHeight = 0;
  private int rightHeight = 0;
  private Node<T> root;

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

  } // end Node class

  /**
   * Binary Tree constructor, creates a new binary tree and utilizes
   * most of the six public methods available to ensure the GUI has
   * the necessary information for user input
   * @param input STRING Binary tree representation
   * @throws InvalidTreeSyntax If Illegal characters are in input
   */
  public BinaryTree(String input) throws InvalidTreeSyntax {
    final String REGEX = "^[0-9|A-Z|a-z|\\(|\\)]+$";
    Comparator.naturalOrder();
    boolean matches = Pattern.compile(REGEX).matcher(input).lookingAt();
    if (!matches) { // check if any input is invalid before parsing to add to tree
      throw new InvalidTreeSyntax("Characters in Tree are not Valid.");
    } // end if statement
    // tokenize the input string
    StringTokenizer tokenizer = new StringTokenizer(input, "()", true);
    // array lists to contain the parenthesis and data
    ArrayList<String> leftParenthesis = new ArrayList<>();
    ArrayList<String> rightParenthesis = new ArrayList<>();
    // set the root node to result of make node
    makeNode(tokenizer, leftParenthesis, rightParenthesis);
    if (parensCounter != 0) // if the tree is not balanced throw error
      throw new InvalidTreeSyntax("Unbalanced Tree, parenthesis not even");
  } // end BinaryTree constructor

  /**
   * Creates a new binary tree and set the data
   * for the root element
   * @param tokenizer StringTokenizer
   * @param leftParenthesis ArrayList for left parenthesis
   * @param rightParenthesis ArrayList for right parenthesis
   * @throws InvalidTreeSyntax If parenthesis do not balance
   */
  private void makeNode(StringTokenizer tokenizer, ArrayList<String> leftParenthesis, ArrayList<String> rightParenthesis)
          throws InvalidTreeSyntax{
    String token = nextNonBlank(tokenizer);
    // if there are no more characters end
    if (!tokenizer.hasMoreElements()) {
      --parensCounter;
      return;
    } // end if statement
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
    // if root is null add element as data point
    if (root == null) {
      root = new Node<>(element);
      return;
    } // end if statement
    // otherwise recursively add elements to tree
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
   * @throws InvalidTreeSyntax Cannot contain spaces
   */
  private String nextNonBlank(StringTokenizer tokenizer) throws InvalidTreeSyntax {
    String token = "";
    // if there are more elements
    if (tokenizer.hasMoreElements()) {
      // return the next non blank element
      while (!(token = tokenizer.nextToken()).equals(" ")) {
        return token;
        }
    } // otherwise throw an invalid tree error
     /**** this error should more than likely never be thrown
     as the regex and trimming of the input should prevent
     it from happening ****/
    throw new InvalidTreeSyntax("Expression Contains Spaces");
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

  /**
   * Checks whether the tree is full and even
   * @return BOOLEAN true/false
   */
  public boolean isFull() {
    if (root != null) return isFull(root);
    return false;
  } // end isFull method

  /**
   * Makes calls to multiple methods to apply the difference
   * between what a full tree would be based off of the current
   * height and the number of nodes in the tree
   * @param node NODE root
   * @return BOOLEAN true/false
   */
  private boolean isFull(Node<T> node) {
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
    if (root != null) return (height(root) - 1);
    return 0;
  } // end height method

  /**
   * Recursive height method to calculate height of tree
   * @param node NODE root of tree
   * @return INTEGER height of tree
   */
  private int height(Node<T> node) {
    // if the node is null return 0
    if (node == null) {
      return 0;
    } else {
      // recursively set the value for each height
      int left = height(node.left);
      int right = height(node.right);
      // compare values, return higher value, but set each value
      // for later use in other methods
      if (left > right) {
        setLeftHeight(left);
        setRightHeight(right);
        return (left + 1);
      } else {
        return (right + 1);
      } // end if/else statement

    } // end if/else statement

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
   * @return String generated string for output
   */
  public String inorder() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder = inorder(root, 0, stringBuilder);
    String result = stringBuilder.toString();
    return result;
  } // end inorder method

  /**
   * Creates the output for the inorder button, generating a string
   * with parenthesis around it
   * @param node NODE root node
   * @param count INTEGER counter variable
   * @param stringBuilder STRING result of building string
   * @return STRINGBUILDER constructed string
   */
  private StringBuilder inorder(Node<T> node, int count, StringBuilder stringBuilder) {
    String leftOutput = "";
    String rightOutput = "";
    String left = "(";
    String right = ")";
    // if node is null return the string builder
    if (node == null) return stringBuilder;

    ++count; // increase the count by one and recurse
    inorder(node.left, count, stringBuilder);
    for(int i = 0; i < count - 1; i++) {
      leftOutput += left;
      rightOutput += right;
    } // end for loop
    // append the parenthesis to the data
    stringBuilder.append(leftOutput)
            .append(node.data)
            .append(rightOutput);
    count = 0; // reset the counter
    // recurse onto the right side of root
    inorder(node.right, count, stringBuilder);
    // return the built string
    return stringBuilder.append(")");
  } // end inorder method

} // end BinaryTree class
