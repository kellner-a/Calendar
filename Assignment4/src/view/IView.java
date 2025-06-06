package view;

/**
 * Interface for all views. All views can display messages.
 */
public interface IView {

  /**
   * Returns the log.
   *
   * @return String
   */
  public String display(String message);
}
