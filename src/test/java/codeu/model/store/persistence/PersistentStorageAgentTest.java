package codeu.model.store.persistence;

import codeu.model.data.AboutMeMessage;
import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.Statistic;
import codeu.model.data.Statistic.Type;
import codeu.model.data.User;
import java.time.Instant;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

/**
 * Contains tests of the PersistentStorageAgent class. Currently that class is just a pass-through
 * to PersistentDataStore, so these tests are pretty trivial. If you modify how
 * PersistentStorageAgent writes to PersistentDataStore, or if you swap out the backend to something
 * other than PersistentDataStore, then modify these tests.
 */
public class PersistentStorageAgentTest {

  private PersistentDataStore mockPersistentDataStore;
  private PersistentStorageAgent persistentStorageAgent;

  @Before
  public void setup() {
    mockPersistentDataStore = Mockito.mock(PersistentDataStore.class);
    persistentStorageAgent = PersistentStorageAgent.getTestInstance(mockPersistentDataStore);
  }

  @Test
  public void testLoadUsers() throws PersistentDataStoreException {
    persistentStorageAgent.loadUsers();
    Mockito.verify(mockPersistentDataStore).loadUsers();
    Mockito.verify(mockPersistentDataStore).writeThrough(any(Statistic.class));
  }

  @Test
  public void testLoadConversations() throws PersistentDataStoreException {
    persistentStorageAgent.loadConversations();
    Mockito.verify(mockPersistentDataStore).loadConversations();
    Mockito.verify(mockPersistentDataStore).writeThrough(any(Statistic.class));
  }

  @Test
  public void testLoadMessages() throws PersistentDataStoreException {
    persistentStorageAgent.loadMessages();
    Mockito.verify(mockPersistentDataStore).loadMessages();
    Mockito.verify(mockPersistentDataStore).writeThrough(any(Statistic.class));
  }
  
  @Test
  public void testLoadAboutMeMessages() throws PersistentDataStoreException {
    persistentStorageAgent.loadAboutMeMessages();
    Mockito.verify(mockPersistentDataStore).loadAboutMeMessages();
  }

  @Test
  public void testLoadStatistics() throws PersistentDataStoreException {
    Type type = Type.TEST;
    persistentStorageAgent.loadStatistics(type);
    Mockito.verify(mockPersistentDataStore).loadStatistics(type);
  }

  @Test
  public void testWriteThroughUser() {
    User user =
        new User(
            UUID.randomUUID(),
            "test_username",
            "$2a$10$bBiLUAVmUFK6Iwg5rmpBUOIBW6rIMhU1eKfi3KR60V9UXaYTwPfHy",
            Instant.now(),
            new LinkedHashSet<Conversation>(),
            new HashSet<Conversation>());
    persistentStorageAgent.writeThrough(user);
    Mockito.verify(mockPersistentDataStore).writeThrough(user);
    Mockito.verify(mockPersistentDataStore).writeThrough(any(Statistic.class));
  }

  @Test
  public void testWriteThroughConversation() {
    Conversation conversation =
        new Conversation(UUID.randomUUID(), UUID.randomUUID(), "test_conversation", Instant.now(),
            new LinkedHashSet<User>());
    persistentStorageAgent.writeThrough(conversation);
    Mockito.verify(mockPersistentDataStore).writeThrough(conversation);
    Mockito.verify(mockPersistentDataStore).writeThrough(any(Statistic.class));
  }

  @Test
  public void testWriteThroughMessage() {
    Message message =
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "test content", Instant.now());
    persistentStorageAgent.writeThrough(message);
    Mockito.verify(mockPersistentDataStore).writeThrough(message);
    Mockito.verify(mockPersistentDataStore).writeThrough(any(Statistic.class));
  }
  
  @Test
  public void testWriteThroughAboutMeMessage() {
    AboutMeMessage aboutmemessage =
        new AboutMeMessage(
            UUID.randomUUID(), UUID.randomUUID(), "test content", Instant.now());
    persistentStorageAgent.writeThrough(aboutmemessage);
    Mockito.verify(mockPersistentDataStore).writeThrough(aboutmemessage);
  }

  @Test
  public void testWriteThroughStatistic() {
    Statistic statistic = new Statistic(Type.TEST, 0);
    persistentStorageAgent.writeThrough(statistic);
    Mockito.verify(mockPersistentDataStore).writeThrough(statistic);
  }
}
