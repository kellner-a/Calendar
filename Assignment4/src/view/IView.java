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
  String display(String message);
}
