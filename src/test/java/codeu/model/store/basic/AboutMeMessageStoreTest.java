package codeu.model.store.basic;

import codeu.model.data.AboutMeMessage;

import codeu.model.data.Conversation;
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
	  private final AboutMeMessage ABOUTMEMESSAGE_ONE =
		      new AboutMeMessage(
		          UUID.randomUUID(),
		          UUID.randomUUID(),
		          "aboutmemessage one",
		          Instant.ofEpochMilli(1000));
	  
	  @Before
	  public void setup() {
	    mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
	    aboutmemessageStore =  AboutMeMessageStore.getTestInstance(mockPersistentStorageAgent);
	    final List<AboutMeMessage> aboutmemessageList = new ArrayList<>();
	    aboutmemessageList.add(ABOUTMEMESSAGE_ONE);
	    aboutmemessageStore.setAboutMeMessages(aboutmemessageList);
	  } 
}
