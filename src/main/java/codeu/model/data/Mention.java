package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

public class Mention {
  
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
  
  public String toString(){
    return this.author + " mentioned you in" + this.convoName + ": \"" + this.content + "\"";
  }

}
