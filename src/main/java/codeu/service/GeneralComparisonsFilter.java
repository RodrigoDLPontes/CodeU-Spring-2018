package codeu.service;

/**
 * Filter class (not an actual Servlet Filter) responsible for counting comparisons from Store
 * classes
 */
public class GeneralComparisonsFilter {

  private String description;
  private long comparisons;

  public GeneralComparisonsFilter(String description) {
    this.description = description;
  }

  public void increment() {
    comparisons++;
  }

  public void finish() {
    // prints info (e.g. "UserStore getUser(id): 1 comparisons")
    System.out.println("STATS: " + description + ": " + comparisons + " comparisons");
  }
}
