package codeu.model.data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.UUID;

public class MessageActivity extends Activity {
  
  private final String username;
  private final String conversationName;
  private final String messageContent;
  
  public MessageActivity(UUID id, Instant creation, String username, String conversationName, String messageContent){
    
    super(id, creation);
    this.username = username;
    this.conversationName = conversationName;
    this.messageContent = messageContent;    
  }
  
  public String toString(){
    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withLocale(Locale.US).withZone( ZoneId.systemDefault() );
    return formatter.format(this.creation) + " " + this.username + " sent a message in " + this.conversationName + ": " + this.messageContent;
  }
  
  public String getUsername(){
    return this.username;
  }
  
  public String getConversationName(){
    return this.conversationName; 
  }
  
  public String getMessageContent(){
    return this.messageContent; 
  } 
}