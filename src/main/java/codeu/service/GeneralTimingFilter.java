package codeu.service;

import codeu.model.data.Statistic;
import codeu.model.store.persistence.PersistentStorageAgent;

/**
 * Filter class (not an actual Servlet Filter) responsible for timing methods (mostly for
 * PersistentDataStore and JSPs)
 */
public class GeneralTimingFilter {

  private Statistic.Type type;
  private PersistentStorageAgent agent;
  private long startTime;

  /** Constructor to be used with JSPs */
  public GeneralTimingFilter(Statistic.Type type) {
    this(type, PersistentStorageAgent.getInstance());
  }

  public GeneralTimingFilter(Statistic.Type type, PersistentStorageAgent agent) {
    this.type = type;
    this.agent = agent;
    startTime = System.currentTimeMillis();
  }

  public void finish() {
    // creates statistic and saves it
    Statistic statistic = new Statistic(type, System.currentTimeMillis() - startTime);
    agent.writeThrough(statistic);
  }
}
