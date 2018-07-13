package codeu.model.data;

import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.util.UUID;

public class StatisticTest {

  @Test
  public void testCreate() {
    UUID id = UUID.randomUUID();
    Instant creation = Instant.now();
    long value = 1248;

    Statistic statistic = new Statistic(id, creation, Statistic.Type.TEST, value);

    Assert.assertEquals(id, statistic.getId());
    Assert.assertEquals(creation, statistic.getCreationTime());
    Assert.assertEquals(Statistic.Type.TEST, statistic.getType());
    Assert.assertEquals(value, statistic.getValue());
  }

  @Test
  public void testGetFromMethodAndURI() {
    Statistic.Type expected;
    Statistic.Type actual;
    // Invalid method and URI
    actual = Statistic.Type.getFromMethodAndURI("NOTAMETHOD", "/notauri");
    Assert.assertNull(actual);
    // Invalid method
    actual = Statistic.Type.getFromMethodAndURI("NOTAMETHOD", "/login");
    Assert.assertNull(actual);
    // Invalid URI
    actual = Statistic.Type.getFromMethodAndURI("GET", "/notauri");
    Assert.assertNull(actual);
    // Chat servlet types
    // GET
    expected = Statistic.Type.CHAT_SERVLET_GET_TIME;
    actual = Statistic.Type.getFromMethodAndURI("GET", "/chat/"); // Exact match
    Assert.assertEquals(expected, actual);
    actual = Statistic.Type.getFromMethodAndURI("GET", "/chat/test"); // Specific chat
    Assert.assertEquals(expected, actual);
    actual = Statistic.Type.getFromMethodAndURI("GET", "/chat/other"); // Other chat
    Assert.assertEquals(expected, actual);
    actual = Statistic.Type.getFromMethodAndURI("GET", "/chat"); // Invalid URI
    Assert.assertNull(actual);
    // POST
    expected = Statistic.Type.CHAT_SERVLET_POST_TIME;
    actual = Statistic.Type.getFromMethodAndURI("POST", "/chat/"); // Exact match
    Assert.assertEquals(expected, actual);
    actual = Statistic.Type.getFromMethodAndURI("POST", "/chat/test"); // Specific chat
    Assert.assertEquals(expected, actual);
    actual = Statistic.Type.getFromMethodAndURI("POST", "/chat/other"); // Other chat
    Assert.assertEquals(expected, actual);
    actual = Statistic.Type.getFromMethodAndURI("POST", "/chat"); // Invalid URI
    Assert.assertNull(actual);
    // Conversation servlet types
    // GET
    expected = Statistic.Type.CONVERSATION_SERVLET_GET_TIME;
    actual = Statistic.Type.getFromMethodAndURI("GET", "/conversations");
    Assert.assertEquals(expected, actual);
    // POST
    expected = Statistic.Type.CONVERSATION_SERVLET_POST_TIME;
    actual = Statistic.Type.getFromMethodAndURI("POST", "/conversations");
    Assert.assertEquals(expected, actual);
    // Login servlet types
    // GET
    expected = Statistic.Type.LOGIN_SERVLET_GET_TIME;
    actual = Statistic.Type.getFromMethodAndURI("GET", "/login");
    Assert.assertEquals(expected, actual);
    // POST
    expected = Statistic.Type.LOGIN_SERVLET_POST_TIME;
    actual = Statistic.Type.getFromMethodAndURI("POST", "/login");
    Assert.assertEquals(expected, actual);
    // Register servlet types
    // GET
    expected = Statistic.Type.REGISTER_SERVLET_GET_TIME;
    actual = Statistic.Type.getFromMethodAndURI("GET", "/register");
    Assert.assertEquals(expected, actual);
    // POST
    expected = Statistic.Type.REGISTER_SERVLET_POST_TIME;
    actual = Statistic.Type.getFromMethodAndURI("POST", "/register");
    Assert.assertEquals(expected, actual);
  }
}
