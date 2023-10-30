
package ImprovedHarry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Days {
    private String date;
    private TimeSlot[] timeSlots = new TimeSlot[9];


    public Days(String date, String[] timeStrings) {
        this.date = date;
        for (int i = 0; i < timeStrings.length - 1; i++) {
            this.timeSlots[i] = new TimeSlot(timeStrings[i], timeStrings[i+1]);
        }
    }


    public TimeSlot[] getTimeSlots() {
        return timeSlots;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTimeSlots(TimeSlot[] timeSlots) {
        this.timeSlots = timeSlots;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Date: ").append(date).append("\n");
        for (TimeSlot slot : timeSlots) {
            sb.append(slot).append("\n");
        }
        return sb.toString();
    }
}
