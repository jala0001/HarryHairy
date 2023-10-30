package ImprovedHarry;

public class TimeSlot {
    private String start;
    private String end;
    private Customer customer; // null means no booking
    private boolean available = true;  // by default, it should be available



    public TimeSlot(String start, String end) {
        this.start = start;
        this.end = end;
        this.customer = null;  // No customer when it's initialized
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void book(Customer customer) {
        this.customer = customer;
    }

    public void cancel() {
        this.customer = null;
    }

    public void setAvailable(boolean x) {
        this.available = x;
    }

    public String toFileString() {
        if (customer == null) {
            return start + " - " + end + ": Available";
        } else {
            return start + " - " + end + ": " + customer.getName();
        }
    }


    @Override
    public String toString() {
        if (customer == null) {
            return start + " - " + end + ": Available";
        } else {
            return start + " - " + end + ": Booked by " + customer.getName();
        }
    }

}
