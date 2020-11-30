/*******************************************************************************
 * File: BinaryTree.java
 * Author: Dylan Bryan
 * Date: 11/26/20, 6:45 AM
 * Project: cmsc-350-project-three
 * Purpose: Lorem ipsum dolor sit amet
 ******************************************************************************/

package project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class BinaryTree<T> implements Iterator<T> {
  private String input;
  private Node<T> root;
  private final String REGEX = "^[0-9|A-Z|a-z|\\(|\\)]+$";

  /**
   * Nested Node class
   * @param <T>
   */
  private static class Node<T> {
    private Node<T> left;
    private T data;
    private Node<T> right;

    public Node() {
      this(null, null, null);
    } // end default constructor

    /**
     * Node constructor
     * @param data Data to pass into Node
     */
    public Node(T data, Node<T> left, Node<T> right) {
      this.data = data;
      this.left = left;
      this.right = right;
    } // end Node constructor

    // setter and getter for data
    public void setData(T data) { this.data = data; } // end setData method
    public T getData() { return data; } // end getData method
    // setter and getter for left
    public void setLeft(Node<T> link) { this.left = link; }; // end setLeft method
    public Node<T> getLeft() { return left; } // end getLeft method
    // setter and getter for right
    public void setRight(Node<T> link) { this.right = link; } // end setRight method
    public Node<T> getRight() { return right; } // end getRight method

  } // end Node class

  public BinaryTree(String input) throws InvalidTreeSyntax {
    this.input = input;
    System.out.println(this.input);
    boolean matches = Pattern.compile(REGEX).matcher(input).lookingAt();
    if (!matches) { // check if any input is invalid before parsing to add to tree
      throw new InvalidTreeSyntax("Characters in Tree are not Valid.");
    } // end if statement
    // tokenize the input string
    StringTokenizer tokenizer = new StringTokenizer(input, "()*/-+ ", true);
    // set the root node to result of make node
    root = makeNode(tokenizer);

    printItems(root);
  } // end BinaryTree constructor

  private boolean printItems(Node<T> node) {
    if (node == null) {
      return false;
    }
    printItems(node.getLeft());
    System.out.println(node.data);
    printItems(node.getRight());
//    Node<T> current = printItems(node.getLeft());
//    System.out.println(current);
//    current = printItems(node.getRight());
    return false;
  }

  private Node<T> makeNode(StringTokenizer tokenizer) throws InvalidTreeSyntax {
    Node<T> node;
    String token = nextNonBlank(tokenizer);
    // check for generic data type
    if (isCharacter(token)) {
      node = new Node<>();
      node.setData((T) token);
      return node;
    } // end if statement
    // check for left parenthesis
    if (!(token.equals("("))) {
      throw new InvalidTreeSyntax("Missing Left Parenthesis.");
    } // end if statement
    Node<T> leftChild = makeNode(tokenizer);
//    token = nextNonBlank(tokenizer);
    Node<T> rightChild = makeNode(tokenizer);
    if (!(nextNonBlank(tokenizer).equals(")"))) {
      throw new InvalidTreeSyntax("Missing Right Parenthesis.");
    } // end if statement
    node = new Node<>((T) token, leftChild, rightChild);
    return node;
  } // end makeNode method

  /**
   * Check that the token passed through is a character
   * and not parenthesis or some other item, this way
   * it can be used as a node in the binary tree
   * @param token STRING token
   * @return BOOLEAN true/false
   */
  private boolean isCharacter(String token) {
    String CHARACTER_REGEX = "^[0-9|A-Z|a-z]+$";
    boolean matches = Pattern.compile(CHARACTER_REGEX).matcher(token).lookingAt();
    if (matches) {
      return true;
    } // end if statement
    return false;
  } // end isCharacter method

  /**
   * Checks the user input to ensure there are no spaces in the Tree Expression
   * @param tokenizer TOKENIZER String token
   * @return STRING token
   * @throws InvalidTreeSyntax
   */
  private String nextNonBlank(StringTokenizer tokenizer) throws InvalidTreeSyntax {
    String token; // current item
    while (tokenizer.hasMoreTokens()) {
      if (!(token = tokenizer.nextToken()).equals(" ")) {
        return token;
      } // end if statement
    } // end while loop
    throw new InvalidTreeSyntax("Tree Expression Contains Spaces");
  } // end nextNonBlank method

//  @Override
//  public int compareTo(T t) {
//    return 0;
//  }

  private static class Iterable<T> implements Iterator<T> {

    @Override
    public boolean hasNext() {
      return false;
    }

    @Override
    public T next() {
      return null;
    }

  }

  private Iterable<T> iterable() {
    return new Iterable<>();
  }

  @Override
  public boolean hasNext() {
    return false;
  }

  @Override
  public T next() {
    return null;
  }

  /*
   * Start the public and private methods for buttons
   */

  public boolean isBalanced () {
    return false; // todo: implement the isBalanced method
  } // end isBalanced method

//  private boolean isBalanced() {
//    return false;
//  } // end isBalanced method

  public boolean isFull() {
    return false; // todo: implement the isFull method
  } // end isFull method

//  private boolean isFull() {
//    return false;
//  } // end isFull method

  public boolean isProper() {
    return false; // todo: implement the isProper method
  } // end isProper method

//  private boolean isProper() {
//    return false;
//  } // end isProper method

  public int height() {
    return 0; // todo: implement the height method
  } // end height method

//  private int height() {
//    return 0;
//  } // end height method

  /**
   * Calls the private method nodes to count
   * the number of nodes in the tree
   * @return INTEGER number of nodes
   */
  public int nodes() {
    return nodes(root);
  } // end nodes method

  /**
   * Recursively counts the number of nodes in the tree
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
//    // if the parent has no children return 0
//    if (node == null) {
//      return 0;
//    // if the children are leaf nodes return 1
//    } else if ((node.left == null) && (node.right == null)) {
//      return 1;
//    // recursively travel down the tree
//    } else {
//      return 1 + nodes(node.left) + nodes(node.right);
//    } // end if/else if/ else statement
  } // end nodes method

  public ArrayList<T> inorder() {
    ArrayList<T> list = new ArrayList<>();
    inorder(root, list);
    return list;
  } // end inorder method

  private void inorder(Node<T> node, ArrayList<T> list) {
    if (node == null) {
      return;
    } // end if statement
    // traverse the left side of list
    inorder(node.left, list);
    // add items to the list
    list.add(node.data);
    // traverse the right side of list
    inorder(node.right, list);
  } // end inorder method

  /*
   * End six public and private methods for buttons
   */

} // end BinaryTree class
