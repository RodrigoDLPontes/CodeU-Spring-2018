package codeu.model.store.basic;

import codeu.model.data.AboutMeMessage;
import codeu.model.data.Message;
import codeu.model.store.persistence.PersistentStorageAgent;
import static org.junit.Assert.assertEquals;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AboutMeMessageStoreTest {
    private AboutMeMessageStore aboutmemessageStore;
    private PersistentStorageAgent mockPersistentStorageAgent;
    final UUID Test_Author_ID = UUID.randomUUID();
    private final AboutMeMessage ABOUTMEMESSAGE_ONE = new AboutMeMessage(UUID.randomUUID(), Test_Author_ID,
            "aboutmemessage one", Instant.ofEpochMilli(1000));

    @Before
    public void setup() {
        mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
        aboutmemessageStore = AboutMeMessageStore.getTestInstance(mockPersistentStorageAgent);
        final List<AboutMeMessage> aboutmemessageList = new ArrayList<>();
        aboutmemessageList.add(ABOUTMEMESSAGE_ONE);
        aboutmemessageStore.setAboutMeMessages(aboutmemessageList);
    }

    @Test
    public void testGetAllAboutMeMessages() {
        List<AboutMeMessage> resultAllAboutMeMessages = aboutmemessageStore.getAllAboutMeMessages();
        Assert.assertEquals(1, resultAllAboutMeMessages.size());
        assertEquals(ABOUTMEMESSAGE_ONE, resultAllAboutMeMessages.get(0));
    }

    @Test
    public void testGetAboutMeMessagesByUser() {
        List<AboutMeMessage> resultMessages = aboutmemessageStore
                .getAboutMeMessagesByUser(ABOUTMEMESSAGE_ONE.getAuthorId());
        Assert.assertEquals(1, resultMessages.size());
        assertEquals(ABOUTMEMESSAGE_ONE, resultMessages.get(0));
    }

    @Test
    public void testAddAboutMeMessage() {

        AboutMeMessage inputAboutMeMessage = new AboutMeMessage(UUID.randomUUID(), Test_Author_ID, "test_conversation",
                Instant.now());

        aboutmemessageStore.addAboutMeMessage(inputAboutMeMessage);

        Mockito.verify(mockPersistentStorageAgent).writeThrough(inputAboutMeMessage);
    }

    private void assertEquals(AboutMeMessage expectedAboutMeMessage, AboutMeMessage actualAboutMeMessage) {
        Assert.assertEquals(expectedAboutMeMessage.getId(), actualAboutMeMessage.getId());
        Assert.assertEquals(expectedAboutMeMessage.getAuthorId(), actualAboutMeMessage.getAuthorId());
        Assert.assertEquals(expectedAboutMeMessage.getContent(), actualAboutMeMessage.getContent());
        Assert.assertEquals(expectedAboutMeMessage.getCreationTime(), actualAboutMeMessage.getCreationTime());
    }
}
