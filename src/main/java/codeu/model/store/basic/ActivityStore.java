package codeu.model.store.basic;

import codeu.model.data.Activity;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.List;

public class ActivityStore {
	  /** Singleton instance of ActivityStore. */
	  private static ActivityStore instance;

	  /**
	   * Returns the singleton instance of ActivityStore that should be shared between all servlet
	   * classes. Do not call this function from a test; use getTestInstance() instead.
	   */
	  public static ActivityStore getInstance() {
	    if (instance == null) {
	      instance = new ActivityStore(PersistentStorageAgent.getInstance());
	    }
	    return instance;
	  }

	  /**
	   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
	   *
	   * @param persistentStorageAgent a mock used for testing
	   */
	  public static ActivityStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
	    return new ActivityStore(persistentStorageAgent);
	  }

	  /**
	   * The PersistentStorageAgent responsible for loading Activities from and saving Activities
	   * to Datastore.
	   */
	  private PersistentStorageAgent persistentStorageAgent;

	  /** The in-memory list of Activities. */
	  private List<Activity> activities;

	  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
	  private ActivityStore(PersistentStorageAgent persistentStorageAgent) {
	    this.persistentStorageAgent = persistentStorageAgent;
	    activities = new ArrayList<>();
	  }

	/** Access the current set of activities known to the application. */
	  public List<Activity> getAllActivities() {
	    return activities;
	  }

	  /** Add a new activity to the current set of activities known to the application. */
	  public void addActivity(Activity activity) {
	    activities.add(activity);
	    persistentStorageAgent.writeThrough(activity);
	  }

}
