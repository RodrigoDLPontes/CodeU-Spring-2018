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

package codeu.model.data;

import java.time.Instant;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.UUID;

import codeu.model.store.basic.UserStore;

/** Class representing a registered user. */
public class User {
  private final UUID id;
  private final String name;
  private final String passwordHash;
  private final Instant creation;
  private LinkedHashSet<Conversation> conversations;
  private HashSet<Conversation> adminConvos;
  private UserStore userStore;

  /**
   * Constructs a new User.
   *
   * @param id the ID of this User
   * @param name the username of this User
   * @param passwordHash the password hash of this User
   * @param creation the creation time of this User
   */
  public User(UUID id, String name, String passwordHash, Instant creation, 
      LinkedHashSet<Conversation> conversations, HashSet<Conversation> adminConvos) {
    this.id = id;
    this.name = name;
    this.passwordHash = passwordHash;
    this.creation = creation;
    this.conversations = conversations;
    this.adminConvos = adminConvos;
    userStore = UserStore.getInstance();
  }

  /** Returns the ID of this User. */
  public UUID getId() {
    return id;
  }

  /** Returns the username of this User. */
  public String getName() {
    return name;
  }
  
  /** Returns the password hash of this User. */
  public String getPasswordHash() {
    return passwordHash;
  }

  /** Returns the creation time of this User. */
  public Instant getCreationTime() {
    return creation;
  }
  
  /** Returns a set of the conversations this User belongs to */
  public LinkedHashSet<Conversation> getConversations() {
    return conversations;
  }
  
  /** Sets the conversations this User belongs to */
  public void setConversations(LinkedHashSet<Conversation> conversations) {
    this.conversations = conversations;
    userStore.updateUser(this);
  }
  
  /** Returns a set of the conversations this User is an admin for */
  public HashSet<Conversation> getAdminConvos() {
    return adminConvos;
  }
  
  /** Sets the conversations this User is an admin of */
  public void setAdminConvos(HashSet<Conversation> adminConvos) {
    this.adminConvos = adminConvos;
    userStore.updateUser(this);
  }
  
  /** Adds a conversation that this User is a member of to the Conversations set */
  public void addConversation(Conversation convo) {
    conversations.add(convo);
    userStore.updateUser(this);
  }
  
  /** Adds a conversation that this User is an admin for to the adminConvos set */
  public void addAdminConvo(Conversation adminConvo) {
    adminConvos.add(adminConvo);
    userStore.updateUser(this);
  }
  
  @Override
  public boolean equals(Object o) {
    // null check
    if (o == null) {
      return false;
    }
    
    // self check
    if (this == o) {
      return true;
    }
    
    // class check
    if (!(o instanceof User)) {
      return false;
    }
    
    User user = (User) o;
    return id.equals(user.getId());
  }
  
  @Override
  public int hashCode() {
    return id.hashCode();
  }
 
}
