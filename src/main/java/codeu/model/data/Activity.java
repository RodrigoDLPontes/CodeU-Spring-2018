package codeu.model.data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.UUID;
import java.util.List;


public class Activity implements Comparable<Activity> {
	public final UUID id;
	public final Instant creation;

	public Activity(UUID id, Instant creation) {
		this.id = id;
		this.creation = creation;	
	}
	
	public UUID getId(){
		return this.id;
	}
	
	public Instant getCreationTime(){
		return this.creation; 
	}
	
	public int compareTo(Activity other) {
		if (this.creation.toEpochMilli() > other.getCreationTime().toEpochMilli()) {
			return 1;
		} else if (this.creation.toEpochMilli() < other.getCreationTime().toEpochMilli()) {
			return -1;
		} else {
			return 0;
		}
	}
}

