package codeu.model.store.basic;

import codeu.model.store.persistence.PersistentStorageAgent;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class AboutMeMessageStore {
	  /** Singleton instance of AboutMeMessageStore. */
	private static AboutMeMessageStore instance;
	
	 /**
	   * Returns the singleton instance of AboutMeMessageStore that should be shared between all servlet
	   * classes. Do not call this function from a test; use getTestInstance() instead.
	   */
	  public static AboutMeMessageStore getInstance() {
	    if (instance == null) {
	      instance = new AboutMeMessageStore(PersistentStorageAgent.getInstance());
	    }
	    return instance;
	  }
	  /**
	   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
	   *
	   * @param persistentStorageAgent a mock used for testing
	   */
	  public static AboutMeMessageStore  getTestInstance(PersistentStorageAgent persistentStorageAgent) {
	    return new AboutMeMessageStore (persistentStorageAgent);
	  }

}
