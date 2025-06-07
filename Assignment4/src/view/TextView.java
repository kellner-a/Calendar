package view;

/**
 * Displays the text for Calendar output to terminal.
 */
public class TextView implements IView {

  @Override
  public String display(String message) {
    return String.valueOf(message);
  }
}
