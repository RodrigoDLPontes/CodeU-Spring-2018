package codeu.service;

/**
 * Filter class (not an actual Servlet Filter) responsible for timing methods (mostly for
 * PersistentDataStore and JSPs)
 */
public class GeneralTimingFilter {

  private String description;
  private long startTime;

  public GeneralTimingFilter(String description) {
    this.description = description;
    startTime = System.currentTimeMillis();
  }

  public void finish() {
    // prints info (e.g. "STATS: PersistentDataStore loadUsers: 300ms")
    System.out.println("STATS: " + description + ": " + (System.currentTimeMillis() - startTime)
        + "ms");
  }
}
