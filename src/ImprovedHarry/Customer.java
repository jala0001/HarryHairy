package ImprovedHarry;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Customer {
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double price;

    public Customer(String name, LocalDateTime startTime, LocalDateTime endTime, double price) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Customer Name: " + name +
                "\nStart Time: " + startTime +
                "\nEnd Time: " + endTime +
                "\nPrice: " + price + " kr.";
    }

}

