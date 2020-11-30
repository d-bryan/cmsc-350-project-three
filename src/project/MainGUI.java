/*******************************************************************************
 * File: Main.java
 * Author: Dylan Bryan
 * Date: 11/26/20, 6:45 AM
 * Project: cmsc-350-project-three
 * Purpose: Lorem ipsum dolor sit amet
 ******************************************************************************/

package project;

import javax.swing.*;
import java.awt.*;

/**
 * Main class containing components
 * for the GUI and initialization
 */
public class MainGUI extends JFrame {
  // create buttons for use in the GUI
  private final JButton makeTree = new JButton("Make Tree"),
  isBalanced = new JButton("Is Balanced?"), isFull = new JButton("Is Full?"),
  isProper = new JButton("Is Proper?"), height = new JButton("Height"),
  nodes = new JButton("Nodes"), inorder = new JButton("Inorder");
  // create text fields for input and output
  private final JTextField input = new JTextField(30),
  output = new JTextField(30);
  private JButton[] jButtons = { makeTree, isBalanced, isFull, isProper, height, nodes, inorder };
  // binary tree class
  BinaryTree binaryTree;

  public MainGUI() {
    // setup main JFrame settings
    super("Binary Tree Categorizer");
    setSize(750, 250);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridLayout(3, 1, 5, 5));
    // create extra panels for proper GUI display
    JPanel inputPanel = new JPanel(),
            buttonPanel = new JPanel(),
            outputPanel = new JPanel();
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
  } // end MainGUI constructor

  /**
   * Creates a new Binary Tree object for use through the
   * program, and catches any Invalid Tree Syntax Errors to
   * display them to the user if thrown
   */
  private void makeTree() {
    try {
      binaryTree = new BinaryTree(input.getText().trim());
      JOptionPane.showMessageDialog(null, "Tree Constructed");
    } catch (InvalidTreeSyntax ex) {
      JOptionPane.showMessageDialog(null, "Invalid Tree Syntax: \n" + ex.getMessage() + "\n" + input.getText().trim());
    } // end try/catch block
  } // end makeTree method

  /**
   * Optional method to clear the output text
   */
  private void clearOutput() {
    output.setText("");
  } // end clearOutput method

  // main method
  public static void main(String[] args) {
    MainGUI window = new MainGUI();
    window.setVisible(true);
  } // end main method

} // end MainGUI class
