package codeu.model.store.basic;

import codeu.model.data.Conversation;
import codeu.model.data.Statistic;
import codeu.model.data.User;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class ConversationStoreTest {

  private ConversationStore conversationStore;
  private PersistentStorageAgent mockPersistentStorageAgent;

  private final Conversation CONVERSATION_ONE =
      new Conversation(
          UUID.randomUUID(), UUID.randomUUID(), "conversation_one", Instant.ofEpochMilli(1000),
          new LinkedHashSet<User>());

  @Before
  public void setup() {
    mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
    conversationStore = ConversationStore.getTestInstance(mockPersistentStorageAgent);

    final List<Conversation> conversationList = new ArrayList<>();
    conversationList.add(CONVERSATION_ONE);
    conversationStore.setConversations(conversationList);
  }

  @Test
  public void testGetConversationWithTitle_found() {
    Conversation resultConversation =
        conversationStore.getConversationWithTitle(CONVERSATION_ONE.getTitle());

    assertEquals(CONVERSATION_ONE, resultConversation);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(any(Statistic.class));
  }

  @Test
  public void testGetConversationWithTitle_notFound() {
    Conversation resultConversation = conversationStore.getConversationWithTitle("unfound_title");

    Assert.assertNull(resultConversation);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(any(Statistic.class));
  }

  @Test
  public void testIsTitleTaken_true() {
    boolean isTitleTaken = conversationStore.isTitleTaken(CONVERSATION_ONE.getTitle());

    Assert.assertTrue(isTitleTaken);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(any(Statistic.class));
  }

  @Test
  public void testIsTitleTaken_false() {
    boolean isTitleTaken = conversationStore.isTitleTaken("unfound_title");

    Assert.assertFalse(isTitleTaken);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(any(Statistic.class));
  }

  @Test
  public void testAddConversation() {
    Conversation inputConversation =
        new Conversation(UUID.randomUUID(), UUID.randomUUID(), "test_conversation", Instant.now(),
            new LinkedHashSet<User>());

    conversationStore.addConversation(inputConversation);
    Conversation resultConversation =
        conversationStore.getConversationWithTitle("test_conversation");

    assertEquals(inputConversation, resultConversation);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(inputConversation);
    // check if statistic was written twice (in addConversation() and getConversationWithTitle())
    Mockito.verify(mockPersistentStorageAgent, times(2)).writeThrough(any(Statistic.class));
  }

  private void assertEquals(Conversation expectedConversation, Conversation actualConversation) {
    Assert.assertEquals(expectedConversation.getId(), actualConversation.getId());
    Assert.assertEquals(expectedConversation.getOwnerId(), actualConversation.getOwnerId());
    Assert.assertEquals(expectedConversation.getTitle(), actualConversation.getTitle());
    Assert.assertEquals(
        expectedConversation.getCreationTime(), actualConversation.getCreationTime());
    Assert.assertEquals(expectedConversation.getMembers(), actualConversation.getMembers());
  }
}
