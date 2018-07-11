package codeu.service;

import codeu.model.data.Statistic;
import codeu.model.store.persistence.PersistentStorageAgent;

/**
 * Filter class (not an actual Servlet Filter) responsible for counting comparisons from Store
 * classes
 */
public class GeneralComparisonsFilter {

  private Statistic.Type type;
  PersistentStorageAgent agent;
  private long comparisons;

  public GeneralComparisonsFilter(Statistic.Type type, PersistentStorageAgent agent) {
    this.type = type;
    this.agent = agent;
  }

  public void increment() {
    comparisons++;
  }

  public void finish() {
    // creates statistic and saves it
    Statistic statistic = new Statistic(type, comparisons);
    agent.writeThrough(statistic);
  }
}
