/*******************************************************************************
 * File: InvalidTreeSyntax.java
 * Author: Dylan Bryan
 * Date: 11/26/20, 6:44 AM
 * Project: cmsc-350-project-three
 * Purpose: Error class to catch the Invalid Tree Syntax passed through
 ******************************************************************************/

package project;

public class InvalidTreeSyntax extends Exception {

  /**
   * Constructs an Invalid Tree Syntax Error
   * with a custom message for the user
   * @param message STRING message for the user
   */
  public InvalidTreeSyntax(String message) {
    super(message);
  } // end constructor

} // end InvalidTreeSyntax class
