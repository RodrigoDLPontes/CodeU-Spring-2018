package codeu.model.store.basic;

import codeu.model.data.AboutMeMessage;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Store class that uses in-memory data structures to hold values and
 * automatically loads from and saves to PersistentStorageAgent. It's a
 * singleton so all servlet classes can access the same instance.
 */
public class AboutMeMessageStore {
    
    /** Singleton instance of AboutMeMessageStore. */
    private static AboutMeMessageStore instance;

    /**
     * Returns the singleton instance of MessageStore that should be shared between
     * all servlet classes. Do not call this function from a test; use
     * getTestInstance() instead.
     */
    public static AboutMeMessageStore getInstance() {
        if (instance == null) {
            instance = new AboutMeMessageStore(PersistentStorageAgent.getInstance());
        }
        return instance;
    }

    /**
     * Instance getter function used for testing. Supply a mock for
     * PersistentStorageAgent. @param persistentStorageAgent a mock used for testing
     */
    public static AboutMeMessageStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
        return new AboutMeMessageStore(persistentStorageAgent);
    }

    /**
     * The PersistentStorageAgent responsible for loading Messages from and saving
     * Messages to Datastore.
     */
    private PersistentStorageAgent persistentStorageAgent;

    /** The in-memory list of Messages. */
    private List<AboutMeMessage> aboutmemessages;

    /**
     * This class is a singleton, so its constructor is private. Call getInstance()
     * instead.
     */
    private AboutMeMessageStore(PersistentStorageAgent persistentStorageAgent) {
        this.persistentStorageAgent = persistentStorageAgent;
        aboutmemessages = new ArrayList<>();
    }

    /**
     * Add a new message to the current set of messages known to the application.
     */
    public void addAboutMeMessage(AboutMeMessage aboutmemessage) {
        aboutmemessages.add(aboutmemessage);
        persistentStorageAgent.writeThrough(aboutmemessage);
    }

    /** Access the current set of AboutMeMessages known to the application. */
    public List<AboutMeMessage> getAllAboutMeMessages() {

        return aboutmemessages;
    }

    /** Access the current set of AboutMeMessages given a unique user . */

    public List<AboutMeMessage> getAboutMeMessagesByUser(UUID authorId) {

        List<AboutMeMessage> aboutMeMessagesByUser = new ArrayList<>();

        
        for (AboutMeMessage aboutmemessage : aboutmemessages) {
            if (aboutmemessage.getAuthorId().equals(authorId)) {
                aboutMeMessagesByUser.add(aboutmemessage);
            }
        }

        return aboutMeMessagesByUser;
        
    }
    

    /** Sets the List of AboutMeMessage stored by this ConversationStore. */
    public void setAboutMeMessages(List<AboutMeMessage> aboutmemessages) {
        this.aboutmemessages = aboutmemessages;
    }

    /** Get the number of AboutMe Messages users have written */

    public int getNumAboutMeMessages() {
        return aboutmemessages.size();
    }
}
