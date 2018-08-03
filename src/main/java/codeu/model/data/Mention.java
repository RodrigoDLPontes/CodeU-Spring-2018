package codeu.model.data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.UUID;

public class Mention implements Comparable<Mention> {
  
  private final String author;
  private final String content;
  private final String convoName;
  private final Instant creation;
  
  
   
  public Mention(String author, String content, String convoName, Instant creation){
    this.author = author;
    this.content = content;
    this.convoName = convoName;
    this.creation = creation;
  }
  
  
  public Instant getCreationTime(){
    return this.creation; 
  }
  
  
  public String toString(){
    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.US).withZone( ZoneId.systemDefault() );
    return formatter.format(this.creation) + " <a href=\"/userprofile/" + this.author + "\">" +
    this.author + "</a> mentioned you in " + "<a href=\"/chat/" + this.convoName + "\">" + this.convoName + "</a>: \"" + this.content + "\"";
  }
  
  public int compareTo(Mention other) {
    if (this.creation.toEpochMilli() > other.getCreationTime().toEpochMilli()) {
      return 1;
    } else if (this.creation.toEpochMilli() < other.getCreationTime().toEpochMilli()) {
      return -1;
    } else {
      return 0;
    }
  }

}
