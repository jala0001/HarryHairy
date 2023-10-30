package ImprovedHarry;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

class BookingCalendar {

    public boolean isBookingAllowed(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.getDayOfWeek() != DayOfWeek.SATURDAY && startTime.getDayOfWeek() !=
                DayOfWeek.SUNDAY && startTime.toLocalTime().isAfter(LocalTime.of(9, 0)) && startTime.
                toLocalTime().isBefore(LocalTime.of(18, 0)) &&
                endTime.toLocalTime().isBefore(LocalTime.of(18, 1))) {
            return true;
        }
        return false;

    }

}
