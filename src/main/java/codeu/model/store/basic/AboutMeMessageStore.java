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
}
