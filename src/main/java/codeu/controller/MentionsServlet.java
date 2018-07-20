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

package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.ConversationActivity;
import codeu.model.data.Mention;
import codeu.model.data.Message;
import codeu.model.data.MessageActivity;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.LinkedList;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet class responsible for the conversations page. */
public class MentionsServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Store class that gives access to Conversations. */
  private ConversationStore conversationStore;
  
  private MessageStore messageStore;

  /**
   * Set up state for handling conversation-related requests. This method is only called when
   * running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setConversationStore(ConversationStore.getInstance());
    setMessageStore(MessageStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }
  
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
}

  /**
   * Sets the ConversationStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  /**
   * This function fires when a user navigates to the conversations page. It gets all of the
   * conversations from the model and forwards to conversations.jsp for rendering the list.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String username = (String) request.getSession().getAttribute("user");
    if (username == null) {
      // user is not logged in, don't let them look at their mentions
      response.sendRedirect("/login");
      return;
    }
    
    List<Conversation> conversations = conversationStore.getAllConversations();
    List<Mention> mentions = new LinkedList<>();
    
    for (Conversation conversation : conversations) { 
      // add all of the messages in this conversation to the messages list
      List<Message> messages = this.messageStore.getMessagesInConversation(conversation.getId());
      
      for (Message message : messages){
        
        if (message.getContent().contains("@" + username)){
          // get conversation owner's UUID from conversation
          UUID id = message.getAuthorId();
          // use the UserStore to convert the owner's UUID into a String (username)
          User author = this.userStore.getUser(id);
                     
          Mention mention = 
              new Mention(author.getName(), message.getContent(), conversation.getTitle(), message.getCreationTime());
          mentions.add(mention);
        }
      }
    }
    
    
    request.setAttribute("mentions", mentions);
    request.getRequestDispatcher("/WEB-INF/view/mentions.jsp").forward(request, response);
    
  }


}
