package codeu.model.data;

import java.time.Instant;

import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class AboutMeMessageTest {
	@Test
	public void testCreate() {
		UUID id = UUID.randomUUID();
		UUID author = UUID.randomUUID();
		String content = "test content";
		Instant creation = Instant.now();

		AboutMeMessage aboutmemessage = new AboutMeMessage(id, author, content, creation);
		Assert.assertEquals(id, aboutmemessage.getId());
		Assert.assertEquals(author, aboutmemessage.getAuthorId());
		Assert.assertEquals(content, aboutmemessage.getContent());
		Assert.assertEquals(creation, aboutmemessage.getCreationTime());
	}

}