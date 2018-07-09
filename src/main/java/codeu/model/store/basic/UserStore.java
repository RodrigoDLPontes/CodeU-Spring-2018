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

package codeu.model.store.basic;

import codeu.model.data.Statistic.Type;
import codeu.model.data.User;
import codeu.model.store.persistence.PersistentStorageAgent;
import codeu.service.GeneralComparisonsFilter;
import codeu.service.GeneralTimingFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class UserStore {

  /** Singleton instance of UserStore. */
  private static UserStore instance;

  /**
   * Returns the singleton instance of UserStore that should be shared between all servlet classes.
   * Do not call this function from a test; use getTestInstance() instead.
   */
  public static UserStore getInstance() {
    if (instance == null) {
      instance = new UserStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static UserStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new UserStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Users from and saving Users to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Users. */
  private List<User> users;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private UserStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    users = new ArrayList<>();
  }

  /**
   * Access the User object with the given name.
   *
   * @return null if username does not match any existing User.
   */
  public User getUser(String username) {
    GeneralComparisonsFilter filter = new GeneralComparisonsFilter(
        Type.USER_STORE_GET_USER_USERNAME_COMPS, persistentStorageAgent);
    // This approach will be pretty slow if we have many users.
    for (User user : users) {
      filter.increment();
      if (user.getName().equals(username)) {
        filter.finish();
        return user;
      }
    }
    filter.finish();
    return null;
  }

  /**
   * Access the User object with the given UUID.
   *
   * @return null if the UUID does not match any existing User.
   */
  public User getUser(UUID id) {
    GeneralComparisonsFilter filter = new GeneralComparisonsFilter(
        Type.USER_STORE_GET_USER_ID_COMPS, persistentStorageAgent);
    for (User user : users) {
      filter.increment();
      if (user.getId().equals(id)) {
        filter.finish();
        return user;
      }
    }
    filter.finish();
    return null;
  }

  /**
   * Add a new user to the current set of users known to the application. This should only be called
   * to add a new user, not to update an existing user.
   */
  public void addUser(User user) {
    GeneralTimingFilter filter = new GeneralTimingFilter(
        Type.USER_STORE_ADD_USER_TIME, persistentStorageAgent);
    users.add(user);
    persistentStorageAgent.writeThrough(user);
    filter.finish();
  }

  /**
   * Update an existing User.
   */
  public void updateUser(User user) {
    GeneralTimingFilter filter = new GeneralTimingFilter(
        Type.USER_STORE_UPDATE_USER_TIME, persistentStorageAgent);
    persistentStorageAgent.writeThrough(user);
    filter.finish();
  }

  /** Return true if the given username is known to the application. */
  public boolean isUserRegistered(String username) {
    GeneralComparisonsFilter filter = new GeneralComparisonsFilter(
        Type.USER_STORE_IS_USER_REGISTERED_COMPS, persistentStorageAgent);
    for (User user : users) {
      filter.increment();
      if (user.getName().equals(username)) {
        filter.finish();
        return true;
      }
    }
    filter.finish();
    return false;
  }

  /**
   * Sets the List of Users stored by this UserStore. This should only be called once, when the data
   * is loaded from Datastore.
   */
  public void setUsers(List<User> users) {
    this.users = users;
  }
}

