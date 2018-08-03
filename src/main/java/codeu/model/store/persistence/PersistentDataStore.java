// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.store.persistence;

import codeu.model.data.AboutMeMessage;
import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.Statistic;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

/**
 * This class handles all interactions with Google App Engine's Datastore service. On startup it
 * sets the state of the applications's data objects from the current contents of its Datastore. It
 * also performs writes of new of modified objects back to the Datastore.
 */
public class PersistentDataStore {

  // Handle to Google AppEngine's Datastore service.
  private DatastoreService datastore;

  /**
   * Constructs a new PersistentDataStore and sets up its state to begin loading objects from the
   * Datastore service.
   */
  public PersistentDataStore() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  /**
   * Loads all User objects from the Datastore service and returns them in a List.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<User> loadUsers() throws PersistentDataStoreException {
    List<User> users = new ArrayList<>();

    // Retrieve all users from the datastore.
    Query query = new Query("chat-users");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        String userName = (String) entity.getProperty("username");
        String passwordHash = (String) entity.getProperty("password_hash");
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        
        User user = new User(uuid, userName, passwordHash, creationTime, 
            new LinkedHashSet<Conversation>(), new HashSet<Conversation>());
        users.add(user);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return users;
  }

  /**
   * Loads all Conversation objects from the Datastore service and returns them in a List, sorted in
   * ascending order by creation time.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<Conversation> loadConversations() throws PersistentDataStoreException {
    List<Conversation> conversations = new ArrayList<>();
    UserStore userStore = UserStore.getInstance();

    // Retrieve all conversations from the datastore.
    Query query = new Query("chat-conversations").addSort("creation_time", SortDirection.ASCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        UUID ownerUuid = UUID.fromString((String) entity.getProperty("owner_uuid"));
        String title = (String) entity.getProperty("title");
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        
        String[] memberList;
        LinkedHashSet<User> memberSet = new LinkedHashSet<User>();
        try {
          memberList = ((String) entity.getProperty("member_names")).split(", ");
          
          if (!memberList[0].equals("[]")) {
            for (int i = 0; i < memberList.length; i++) {
              String username = memberList[i];
              
              username = username.replace("[", "");
              username = username.replace("]", "");
              
              memberSet.add(userStore.getUser(username));
            }
          }
          
          memberSet.remove(null);
        } catch (NullPointerException e) {
          // do nothing and continue
        }
        
        Conversation conversation = new Conversation(uuid, ownerUuid, title, creationTime, memberSet);
        ConversationStore.getInstance().updateConversation(conversation);
        
        for (User user : memberSet) {
          user.addConversation(conversation);
          
          if (user.getId().equals(ownerUuid)) {
            user.addAdminConvo(conversation);
          }
          
          userStore.updateUser(user);
        }
        conversations.add(conversation);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      } 
    }

    return conversations;
  }

  /**
   * Loads all Message objects from the Datastore service and returns them in a List, sorted in
   * ascending order by creation time.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<Message> loadMessages() throws PersistentDataStoreException {
    List<Message> messages = new ArrayList<>();

    // Retrieve all messages from the datastore.
    Query query = new Query("chat-messages").addSort("creation_time", SortDirection.ASCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        UUID conversationUuid = UUID.fromString((String) entity.getProperty("conv_uuid"));
        UUID authorUuid = UUID.fromString((String) entity.getProperty("author_uuid"));
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        String content = (String) entity.getProperty("content");
        Message message = new Message(uuid, conversationUuid, authorUuid, content, creationTime);
        messages.add(message);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return messages;
  }
  
  /**
   * Loads all AboutMeMessage objects from the Datastore service and returns them in a List, sorted in
   * ascending order by creation time.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<AboutMeMessage> loadAboutMeMessages() throws PersistentDataStoreException {

    List<AboutMeMessage> aboutmemessages = new ArrayList<>();
    // Retrieve all messages from the datastore.
    Query query = new Query("chat-aboutme").addSort("creation_time", SortDirection.ASCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        UUID authorUuid = UUID.fromString((String) entity.getProperty("author_uuid"));
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        String content = (String) entity.getProperty("content");
        AboutMeMessage aboutmemessage = new  AboutMeMessage(uuid, authorUuid, content, creationTime);
        aboutmemessages.add(aboutmemessage);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return  aboutmemessages;
  }

  public List<Statistic> loadStatistics(Statistic.Type type) throws PersistentDataStoreException {
    List<Statistic> statistics = new ArrayList<>();

    // Retrieve all statistics of that type from the datastore.
    Query query = new Query(type.getName()).addSort("creation_time", SortDirection.ASCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID id = UUID.fromString((String) entity.getProperty("uuid"));
        long value = (long) entity.getProperty("value");
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        Statistic statistic = new Statistic(id, creationTime, type, value);
        statistics.add(statistic);
      } catch (Exception e) {
        throw new PersistentDataStoreException(e);
      }
    }

    return statistics;
  }
  
  /** Write a User object to the Datastore service. */
  public void writeThrough(User user) {
    Entity userEntity = new Entity("chat-users", user.getId().toString());
    userEntity.setProperty("uuid", user.getId().toString());
    userEntity.setProperty("username", user.getName());
    userEntity.setProperty("password_hash", user.getPasswordHash());
    userEntity.setProperty("creation_time", user.getCreationTime().toString());
    
    String[] convoIds = new String[user.getConversations().size()];
    int count = 0;
    for (Conversation conversation : user.getConversations()) {
      convoIds[count] = conversation.getId().toString();
      count++;
    }
    userEntity.setProperty("convo_ids", Arrays.toString(convoIds));
    
    String[] adminConvoIds = new String[user.getAdminConvos().size()];
    count = 0;
    for (Conversation adminConvo : user.getAdminConvos()) {
      adminConvoIds[count] = adminConvo.getId().toString();
      count++;
    }
    userEntity.setProperty("admin_convo_ids", Arrays.toString(adminConvoIds));
    
    datastore.put(userEntity);
  }

  /** Write a Message object to the Datastore service. */
  public void writeThrough(Message message) {
    Entity messageEntity = new Entity("chat-messages", message.getId().toString());
    messageEntity.setProperty("uuid", message.getId().toString());
    messageEntity.setProperty("conv_uuid", message.getConversationId().toString());
    messageEntity.setProperty("author_uuid", message.getAuthorId().toString());
    messageEntity.setProperty("content", message.getContent());
    messageEntity.setProperty("creation_time", message.getCreationTime().toString());
    datastore.put(messageEntity);
  }

  /** Write a Conversation object to the Datastore service. */
  public void writeThrough(Conversation conversation) {
    Entity conversationEntity = new Entity("chat-conversations", conversation.getId().toString());
    conversationEntity.setProperty("uuid", conversation.getId().toString());
    conversationEntity.setProperty("owner_uuid", conversation.getOwnerId().toString());
    conversationEntity.setProperty("title", conversation.getTitle());
    conversationEntity.setProperty("creation_time", conversation.getCreationTime().toString());
    
    String[] memberNames = new String[conversation.getNumMembers()];
    int count = 0;
    for (User member : conversation.getMembers()) {
      memberNames[count] = member.getName();
      count++;
    }
    conversationEntity.setProperty("member_names", Arrays.toString(memberNames));
    
    datastore.put(conversationEntity);
  }

  /** Write a long value (e.g. elapsed time or number of comparisons) to the Datastore service */
  public void writeThrough(Statistic statistic) {
    Entity valueEntity = new Entity(statistic.getType().getName(), statistic.getId().toString());
    valueEntity.setProperty("uuid", statistic.getId().toString());
    valueEntity.setProperty("value", statistic.getValue());
    valueEntity.setProperty("creation_time", statistic.getCreationTime().toString());
    datastore.put(valueEntity);
  }

  /** Write a AboutMeMessage object to the Datastore service. */
  public void writeThrough(AboutMeMessage aboutmemessage) {
    Entity aboutmemessageEntity = new Entity("chat-aboutme", aboutmemessage.getId().toString());
    aboutmemessageEntity.setProperty("uuid",aboutmemessage.getId().toString());
    aboutmemessageEntity.setProperty("author_uuid", aboutmemessage.getAuthorId().toString());
    aboutmemessageEntity.setProperty("content", aboutmemessage.getContent());
    aboutmemessageEntity.setProperty("creation_time", aboutmemessage.getCreationTime().toString());
    datastore.put(aboutmemessageEntity);
  }
  /** Remove a Message object from the Datastore service. */
  public void deleteThrough(Message message) {
    Key messageKey = KeyFactory.createKey("chat-messages", message.getId().toString());
    datastore.delete(messageKey);
  }

  /** Remove a Conversation object from the Datastore service. */
  public void deleteThroughConvo(Conversation conversation) {
 
    Key conversationKey = KeyFactory.createKey("chat-conversations", conversation.getId().toString());
    datastore.delete(conversationKey);
    
    
    
  }

  /** Remove a Message object from the Datastore service. */
  public void deleteThroughAboutMe(AboutMeMessage aboutmemessage) {
    
    Key messageKey = KeyFactory.createKey("chat-aboutme", aboutmemessage.getId().toString());
    datastore.delete(messageKey);
    
  }
}

