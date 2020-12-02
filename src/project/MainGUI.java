/*******************************************************************************
 * File: Main.java
 * Author: Dylan Bryan
 * Date: 11/26/20, 6:45 AM
 * Project: cmsc-350-project-three
 * Purpose: Graphical User Interface for the Binary Tree Project
 ******************************************************************************/

package project;

import javax.swing.*;
import java.awt.*;

/** Main class containing components for the GUI and initialization */
public class MainGUI extends JFrame {
  // create buttons for use in the GUI
  private final JButton makeTree = new JButton("Make Tree"),
      isBalanced = new JButton("Is Balanced?"),
      isFull = new JButton("Is Full?"),
      isProper = new JButton("Is Proper?"),
      height = new JButton("Height"),
      nodes = new JButton("Nodes"),
      inorder = new JButton("Inorder");
  // create text fields for input and output
  private final JTextField input = new JTextField(30), output = new JTextField(30);
  private JButton[] jButtons = {makeTree, isBalanced, isFull, isProper, height, nodes, inorder};
  // binary tree class
  BinaryTree binaryTree;

  public MainGUI() {
    // setup main JFrame settings
    super("Binary Tree Categorizer");
    setSize(750, 250);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridLayout(3, 1, 5, 5));
    // create extra panels for proper GUI display
    JPanel inputPanel = new JPanel(), buttonPanel = new JPanel(), outputPanel = new JPanel();
    inputPanel.setLayout(new FlowLayout());
    buttonPanel.setLayout(new FlowLayout());
    outputPanel.setLayout(new FlowLayout());
    // input components
    inputPanel.add(new JLabel("Enter Tree: "));
    inputPanel.add(input);
    // button components
    for (JButton button : jButtons) {
      buttonPanel.add(button);
    } // end for loop
    // output components
    outputPanel.add(new JLabel("Output: "));
    output.setEditable(false);
    outputPanel.add(output);
    // add components to grid
    add(inputPanel);
    add(buttonPanel);
    add(outputPanel);
    // action event listeners
    makeTree.addActionListener(e -> makeTree());
    isBalanced.addActionListener(
        e -> {
          // if there is no tree created show user friendly message
          if (binaryTree == null) {
            JOptionPane.showMessageDialog(
                null,
                "You must create a tree before interacting with this",
                "No Tree Created",
                JOptionPane.ERROR_MESSAGE);
          } else { // else show whether the tree is balanced
            output.setText("" + binaryTree.isBalanced());
          }
        });
    isFull.addActionListener(
        e -> {
          // if there is no tree created show user friendly message
          if (binaryTree == null) {
            JOptionPane.showMessageDialog(
                null,
                "You must create a tree before interacting with this",
                "No Tree Created",
                JOptionPane.ERROR_MESSAGE);
          } else { // else show whether tree is full
            output.setText("" + binaryTree.isFull());
          }
        });
    isProper.addActionListener(
        e -> {
          // if there is no tree created show user friendly message
          if (binaryTree == null) {
            JOptionPane.showMessageDialog(
                null,
                "You must create a tree before interacting with this",
                "No Tree Created",
                JOptionPane.ERROR_MESSAGE);
          } else { // else show whether tree is proper
            output.setText("" + binaryTree.isProper());
          }
        });
    height.addActionListener(
        e -> {
          // if there is no tree created show user friendly message
          if (binaryTree == null) {
            JOptionPane.showMessageDialog(
                null,
                "You must create a tree before interacting with this",
                "No Tree Created",
                JOptionPane.ERROR_MESSAGE);
          } else { // else show tree height
            binaryTree.setLeftHeight(0);
            binaryTree.setRightHeight(0);
            output.setText("" + binaryTree.height());
          }
        });
    nodes.addActionListener(
        e -> {
          // if there is no tree created show user friendly message
          if (binaryTree == null) {
            JOptionPane.showMessageDialog(
                null,
                "You must create a tree before interacting with this",
                "No Tree Created",
                JOptionPane.ERROR_MESSAGE);
          } else { // else show number of nodes in tree
            output.setText("" + binaryTree.nodes());
          }
        });
    inorder.addActionListener(
        e -> {
          // if there is no tree created show user friendly message
          if (binaryTree == null) {
            JOptionPane.showMessageDialog(
                null,
                "You must create a tree before interacting with this",
                "No Tree Created",
                JOptionPane.ERROR_MESSAGE);
          } else { // else show in order output of tree
            output.setText("" + binaryTree.inorder());
          }
        });
  } // end MainGUI constructor

  /**
   * Creates a new Binary Tree object for use through the program, and catches any Invalid Tree
   * Syntax Errors to display them to the user if thrown
   */
  private void makeTree() {
    try {
      binaryTree = new BinaryTree(input.getText().trim());
      JOptionPane.showMessageDialog(null, "Tree Constructed");
    } catch (InvalidTreeSyntax ex) {
      JOptionPane.showMessageDialog(
          null,
              "Invalid Tree Syntax: \n" + ex.getMessage() + "\n" + input.getText().trim(),
              "Error Creating Tree",
              JOptionPane.ERROR_MESSAGE);
    } // end try/catch block
  } // end makeTree method

  // main method
  public static void main(String[] args) {
    MainGUI window = new MainGUI();
    window.setVisible(true);
  } // end main method
} // end MainGUI class
