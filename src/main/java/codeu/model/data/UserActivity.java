package codeu.model.data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.UUID;

public class UserActivity extends Activity {

  private final String username;
  
  public UserActivity(UUID id, Instant creation, String username){
    super(id, creation);
    this.username = username;
  }
  
  public String getUsername(){
    return this.username;
  }
  public String toString(){
    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withLocale(Locale.US).withZone( ZoneId.systemDefault() );
    return formatter.format(this.creation) + " " + this.username + " joined!";  
  }
  
}