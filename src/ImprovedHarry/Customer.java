package ImprovedHarry;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Customer {
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Customer(String name, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "\nStart time: " + getStartTime() + "\nEnd time: " + getEndTime();
    }
}

