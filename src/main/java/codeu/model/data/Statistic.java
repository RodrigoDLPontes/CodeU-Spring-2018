package codeu.model.data;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Statistic {

  private UUID id;
  private Instant creationTime;
  private Type type;
  private long value;

  public Statistic(Type type, long value) {
    this(UUID.randomUUID(), Instant.now(), type, value);
  }

  public Statistic(UUID id, Instant creationTime, Type type, long value) {
    this.id = id;
    this.creationTime = creationTime;
    this.type = type;
    this.value = value;
  }

  public UUID getId() {
    return id;
  }

  public Instant getCreationTime() {
    return creationTime;
  }

  public String getCreationTimeString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd hh:mm:ss")
        .withZone(ZoneOffset.UTC);
    return formatter.format(creationTime);
  }


  public Type getType() {
    return type;
  }

  public long getValue() {
    return value;
  }

  public enum Type {
    // test type
    TEST("test"),
    // chat servlet types
    CHAT_SERVLET_GET_TIME("chat-servlet-get-time"),
    CHAT_SERVLET_POST_TIME("chat-servlet-post-time"),
    // conversation servlet types
    CONVERSATION_SERVLET_GET_TIME("conversation-servlet-get-time"),
    CONVERSATION_SERVLET_POST_TIME("conversation-servlet-post-time"),
    // login servlet types
    LOGIN_SERVLET_GET_TIME("login-servlet-get-time"),
    LOGIN_SERVLET_POST_TIME("login-servlet-post-time"),
    // register servlet types
    REGISTER_SERVLET_GET_TIME("register-servlet-get-time"),
    REGISTER_SERVLET_POST_TIME("register-servlet-post-time"),
    // conversation store types
    CONVERSATION_STORE_ADD_CONVERSATION_TIME("conversation-store-add-conversation-time"),
    CONVERSATION_STORE_IS_TITLE_TAKEN_COMPS("conversation-store-is-title-taken-comps"),
    CONVERSATION_STORE_GET_CONVERSATION_WITH_TITLE_COMPS(
        "conversation-store-get-conversation-with-title-comps"),
    // message store types
    MESSAGE_STORE_ADD_MESSAGE_TIME("message-store-add-message-time"),
    MESSAGE_STORE_GET_MESSAGES_IN_CONVERSATION_COMPS(
        "message-store-get-messages-in-conversation-comps"),
    // user store types
    USER_STORE_ADD_USER_TIME("user-store-add-user-time"),
    USER_STORE_UPDATE_USER_TIME("user-store-update-user-time"),
    USER_STORE_GET_USER_USERNAME_COMPS("user-store-get-user-username-comps"),
    USER_STORE_GET_USER_ID_COMPS("user-store-get-user-id-comps"),
    USER_STORE_IS_USER_REGISTERED_COMPS("user-store-is-user-registered-comps"),
    // persistent data store types
    PERSISTENT_DATA_STORE_LOAD_USERS_TIME("persistent-data-store-load-users-time"),
    PERSISTENT_DATA_STORE_LOAD_CONVERSATIONS_TIME("persistent-data-store-load-conversations-time"),
    PERSISTENT_DATA_STORE_LOAD_MESSAGES_TIME("persistent-data-store-load-messages-time"),
    PERSISTENT_DATA_STORE_WRITE_THROUGH_USER_TIME("persistent-data-store-write-through-user-time"),
    PERSISTENT_DATA_STORE_WRITE_THROUGH_MESSAGE_TIME(
        "persistent-data-store-write-through-message-time"),
    PERSISTENT_DATA_STORE_WRITE_THROUGH_CONVERSATION_TIME(
        "persistent-data-store-write-through-conversation-time"),
    // jsp types
    INDEX_JSP("index-jsp"), ABOUT_JSP("about-jsp"), CHAT_JSP("chat-jsp"),
    CONVERSATIONS_JSP("conversations-jsp"), LOGIN_JSP("login-jsp"), REGISTER_JSP("register-jsp");

    private String name;

    Type(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    /** Gets appropriate Type from request's method and URI */
    public static Type getFromMethodAndURI(String method, String uri) {
      if(!method.equals("GET") && !method.equals("POST")) {
        return null;
      }
      // is a chat request
      if (uri.startsWith("/chat/")) {
        if (method.equals("GET")) {
          return CHAT_SERVLET_GET_TIME;
        } else {
          return CHAT_SERVLET_POST_TIME;
        }
      }
      // other requests
      switch (uri) {
        case "/conversations":
          if (method.equals("GET")) {
            return CONVERSATION_SERVLET_GET_TIME;
          } else {
            return CONVERSATION_SERVLET_POST_TIME;
          }
        case "/login":
          if (method.equals("GET")) {
            return LOGIN_SERVLET_GET_TIME;
          } else {
            return LOGIN_SERVLET_POST_TIME;
          }
        case "/register":
          if (method.equals("GET")) {
            return REGISTER_SERVLET_GET_TIME;
          } else {
            return REGISTER_SERVLET_POST_TIME;
          }
        default:
          return null;
      }
    }
  }
}
