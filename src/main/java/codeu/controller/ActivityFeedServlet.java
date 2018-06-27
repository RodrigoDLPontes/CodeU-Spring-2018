
//Copyright 2017 Google Inc.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.data.Activity;
import codeu.model.data.Message;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet class responsible for the activity page. */
public class ActivityFeedServlet extends HttpServlet {
	
	private UserStore userStore;
	
	private ConversationStore conversationStore;
	
	private MessageStore messageStore;

	@Override
	public void init() throws ServletException {
	    super.init();
	    setUserStore(UserStore.getInstance());
	    setConversationStore(ConversationStore.getInstance());
	    setMessageStore(MessageStore.getInstance());
	}
	
	void setUserStore(UserStore userStore) {
	    this.userStore = userStore;
	}
	
	void setConversationStore(ConversationStore conversationStore) {
	    this.conversationStore = conversationStore;
	}
	
	void setMessageStore(MessageStore messageStore) {
	    this.messageStore = messageStore;
	}
			
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	   throws IOException, ServletException {
		
		List<Activity> activities = new LinkedList<>();
		List<Conversation> conversations = this.conversationStore.getAllConversations();
		
		for (Conversation conversation : conversations) { 
			// add all of the messages in this conversation to the messages list
			List<Message> messages = this.messageStore.getMessagesInConversation(conversation.getId());
			
			for (Message message : messages){
				
				// get conversation owner's UUID from conversation
				UUID id = message.getAuthorId();
				// use the UserStore to convert the owner's UUID into a String (username)
				User author = this.userStore.getUser(id);
						
				List<String> attributes = new ArrayList<>();
			    attributes.add(author.getName());		    
			    attributes.add(conversation.getTitle());
			    attributes.add(message.getContent());
			    Activity activity = 
			    	new Activity(UUID.randomUUID(), message.getCreationTime(), "message", attributes);
			    activities.add(activity);
			}
			
			// get conversation owner's UUID from conversation
			UUID id = conversation.getOwnerId();
			// use the UserStore to convert the owner's UUID into a String (username)
			User owner = this.userStore.getUser(id);
					
			List<String> attributes = new ArrayList<>();
		    attributes.add(owner.getName());
		    attributes.add(conversation.getTitle());
		    Activity activity = 
		    	new Activity(UUID.randomUUID(), conversation.getCreationTime(), "conversation", attributes);
		    activities.add(activity);
		}
		
		List<User> users = this.userStore.getUsers();
		for (User user : users) {
			List<String> attributes = new ArrayList<>();
		    attributes.add(user.getName());
		    
		    Activity activity = 
		    	new Activity(UUID.randomUUID(), user.getCreationTime(), "user", attributes);
		    activities.add(activity);
		}
		
		Activity[] actsRA = activities.toArray(new Activity[0]); 
		Arrays.sort(actsRA);
		request.setAttribute("activities", Arrays.asList(actsRA));
		request.getRequestDispatcher("/WEB-INF/view/activityfeed.jsp").forward(request, response);
	}
}
