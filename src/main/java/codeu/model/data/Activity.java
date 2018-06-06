package codeu.model.data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.UUID;
import java.util.List;


public class Activity {
	public final UUID id;
	public final Instant creation;
	public final String type;
	public final List<String> attributes;

	public Activity(UUID id, Instant creation, String type, List<String> attributes) {
		this.id = id;
		this.creation = creation;
		this.type = type;
		this.attributes = attributes;
	}
	
	public UUID getId(){
		return this.id;
	}
	
	public Instant getCreationTime(){
		return this.creation; 
	}
	
	public String getType(){
		return this.type;
	}
	
	public List<String> getAttributes(){
		return this.attributes;
	}
	
	public String getStringAttributes() {
		return String.join(",", this.attributes);
	}
	
	public String toString(){
		if (this.type.equals("conversation")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withLocale(Locale.US).withZone( ZoneId.systemDefault() );
			return formatter.format(this.creation) + " " + this.attributes.get(0) + " created a new conversation: " + this.attributes.get(1);
		}
		return null;
		
	}
}
