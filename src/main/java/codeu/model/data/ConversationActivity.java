package codeu.model.data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.UUID;

public class ConversationActivity extends Activity {
  
  private final String username;
  private final String convoName;
  
  public ConversationActivity(UUID id, Instant creation, String username, String convoName){
    super(id, creation);
    this.username = username;
    this.convoName = convoName;
  }
  
  public String getUsername(){
    return this.username;
  }
  
  public String getConvoName(){
    return this.convoName;
  }
  
  public String toString(){
    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withLocale(Locale.US).withZone( ZoneId.systemDefault() );
    return formatter.format(this.creation) + " " + this.username + " created a new conversation: " + this.convoName; 
  }

}
