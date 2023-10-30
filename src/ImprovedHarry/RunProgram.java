package ImprovedHarry;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.DayOfWeek;


public class RunProgram {
    // This is a Github test
    Scanner scanner = new Scanner(System.in);
    boolean programRunning = true;
    List<Customer> bookingList = new ArrayList<>();
    List<Days> calender = new ArrayList<>();
    BookingCalendar bookingCalendar = new BookingCalendar();
    Days days = new Days("date", new String[]{"10", "11", "12", "13", "14", "15", "16", "17"}); // skal nok bare slettes
    Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        new RunProgram().run();
    }

    public void run() {
        password();
        File file = new File("jamie2.txt");

        // If !file... er med til at programmet ikke tilskriver de samme data i filen som allerede står der i forvejen
        if (!file.exists() || file.length() == 0) {
            for (int i = 0; i < 30; i++) {
                LocalDateTime currentDate = LocalDateTime.now().plusDays(i); // skaber datoer fra dags dato og x dage frem.
                String date = currentDate.toLocalDate().toString(); // Laver bare LocalDateTime til en String så det ser således ud "31-10-2023"
                String dayOfWeek = currentDate.getDayOfWeek().toString(); // bruges til at tjekke om det er weekend
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file, true); // Jeg bruger apend = true, så filerne ikke overskrives.
                    PrintStream ps = new PrintStream(fileOutputStream);
                    Days days = new Days(date, new String[]{"10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"});
                    if (dayOfWeek.equals("SATURDAY") || dayOfWeek.equals("SUNDAY")) {
                        ps.println("Date: " + date + "\nSalon is closed on weekends");
                    } else {
                        ps.println(days); // giver output i jamie2.txt
                    }
                    calender.add(days);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        while (programRunning) {
            menu();
        }
    }

   public void password() {
        System.out.println("Please type in the password");
        String password = "hairyHarry";
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        if (input.equals(password)) {
            System.out.println("You now have access to the system");
        }
        else password();
    }



    public void menu() {
        System.out.println("Welcome to Harry's Salon!");
        System.out.println("What would you like to do?");
        System.out.println("Type '1' - Create appointment");
        System.out.println("Type '2' - Delete appointment");
        System.out.println("Type '3' - Check calender");
        System.out.println("Type '4' - Search after a date");
        System.out.println("Type '9' - Exit program");
        menuChoice();
    }
    public void menuChoice() {
        int choice = in.nextInt();
        in.nextLine();
        switch (choice) {
            case 1 -> createAppointment();
            case 2 -> deleteAppointment();
            case 3 -> checkCalender();
            case 4 -> {
                System.out.print("Indtast dato (format: YYYY-MM-DD): ");
                String inputDate = scanner.nextLine();
                searchByDate(inputDate);
            }
            case 9 -> programRunning = false;



        }
    }

    public static void searchByDate(String dateToSearch) {
        String[] times = { // tjekker diverse tids intervaller for den ønskede dato.
                "10:00 - 11:00",
                "11:00 - 12:00",
                "12:00 - 13:00",
                "13:00 - 14:00",
                "14:00 - 15:00",
                "15:00 - 16:00",
                "16:00 - 17:00",
                "17:00 - 18:00"
        };
        try {
            File myFile = new File("jamie2.txt"); // vi åbner kalender filen (dumt filnavn i know) via Scanner
            Scanner fileReader = new Scanner(myFile);
            boolean dateFound = false; // bruges til at fortælle om den ønskede dato er fundet

            while (fileReader.hasNextLine()) { // Bruger while til at læse filen linje for linje
                String line = fileReader.nextLine();

                // Hvis vi finder den ønskede dato
                if (line.equals(dateToSearch)) {
                    dateFound = true;
                    System.out.println(line); // Udskriver datoen

                    // Læs og udskriv tiderne kun for den fundne dato
                    for (int i = 0; i < times.length; i++) { // længden på min String[]
                        if (fileReader.hasNextLine()) {
                            line = fileReader.nextLine();
                            if(line.equals("null")) {
                                break; // Stopper udskrivning hvis en af tiderne bliver til "NULL"
                            }
                            System.out.println(line); // printer 10:00 - 11:00 osv indtil 18:00
                        }
                    }
                    break; // Stop scanning af filen, når vi er færdige med den ønskede dato
                }
            }

            if (!dateFound) { // gælder kun for dage som ikke er oprettet, fx hvis vi vælger en dag som er mere end 30 dage fra dags dato
                System.out.println("Ingen reservationer fundet for denne dato.");
            }

            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("En fejl opstod.");
            e.printStackTrace();
        }
    }



    public void createAppointment() {
        System.out.println("howdy! Whats the customers name?");
        String name = in.nextLine();
        System.out.println("What year do you want to make the reservation?");
        int year = in.nextInt();
        in.nextLine();
        System.out.println("What month do you want to make the reservation?");
        int month = in.nextInt();
        in.nextLine();
        System.out.println("What day do you want to make the reservation?");
        int day = in.nextInt();
        in.nextLine();
        System.out.println("What hour do you want to make the reservation?");
        int hour = in.nextInt();
        in.nextLine();
        int min = 0;
        LocalDateTime startTime = LocalDateTime.of(year, month, day, hour, min);
        int endHour = hour + 1;
        LocalDateTime endTime = LocalDateTime.of(year, month, day, endHour, min);
        createAppointment_2(name, startTime, endTime);
    }


    public void createAppointment_2(String name, LocalDateTime startTime, LocalDateTime endTime) { // oplysningerne fra overstående metode
        if (bookingCalendar.isBookingAllowed(startTime, endTime)) { // tjekker om booking er cool - bookingCalendar objektet er initialiseret i toppen af denne klasse
            Customer newCustomer = new Customer(name, startTime, endTime);
            for (Days day : calender) { // vi itererer gennem 'calender' (som er en liste over min 'Days' objekter, for at finde dagen som matcher startdatoen for den nye aftale
                if (day.getDate().equals(startTime.toLocalDate().toString())) {
                    // Find det rigtige Days-objekt baseret på datoen
                    String timeSlot = startTime.toLocalTime() + " - " + endTime.toLocalTime(); // Kan ikke huske hvorfor jeg oprettede denne linje
                    TimeSlot[] timeSlots = day.getTimeSlots(); // TimeSlot[] er en datatype. Den angiver en array af TimeSlot objekter.
                    for (TimeSlot slot : timeSlots) { // Dette loop er ChatGPT - og kan ikke helt forklare det.
                        if (slot.getStart().equals(startTime.toLocalTime().toString()) && slot.getEnd().equals(endTime.toLocalTime().toString())) {
                            slot.book(newCustomer);
                            break;
                        }
                    }

                    updateCalendarFile(calender); // opdaterer calender filen med den nye booking!

                }
            }

            File file = new File("Jamie.txt"); // endnu et dumt filnavn, men den opretter bookingerne med navn, start- og sluttid.
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                PrintStream ps = new PrintStream(fileOutputStream);
                bookingList.add(newCustomer);
                ps.println(newCustomer);
                ps.println();
                menu();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            File file1 = new File("jamie2.txt"); // har ikke det store formål i min kode men tør ikke slette noget nu hvor programmet fungerer.
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file1, true); // intet formål
                PrintStream ps = new PrintStream(fileOutputStream); // intet formål

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteAppointment() {
        System.out.println("What year is the appointment you want to delete?");
        int year = in.nextInt();
        in.nextLine();
        System.out.println("What month is the appointment you want to delete?");
        int month = in.nextInt();
        in.nextLine();
        System.out.println("What day is the appointment you want to delete?");
        int day = in.nextInt();
        in.nextLine();
        System.out.println("At what hour is the appointment you want to delete?");
        int hour = in.nextInt();
        in.nextLine();
        LocalDateTime appointmentTime = LocalDateTime.of(year, month, day, hour, 0);


        Customer customerToDelete = null;
        for (Customer customer : bookingList) { // kigger igennem bookinglist listen.
            if (customer.getStartTime().equals(appointmentTime)) {
                customerToDelete = customer;
                break;
            }
        }

        if (customerToDelete != null) {
            bookingList.remove(customerToDelete); // hvis en kunde matcher med den angivne dag og tid så slettes kunden fra lsiten


            for (Days dayObj : calender) { // dette loop gør tiden ledig igen efter en kunde er slettet. Bare så der ikke står en tom tid med NULL
                if (dayObj.getDate().equals(appointmentTime.toLocalDate().toString())) {
                    TimeSlot[] timeSlots = dayObj.getTimeSlots();
                    for (TimeSlot slot : timeSlots) {
                        if (slot != null && slot.getStart().equals(appointmentTime.toLocalTime().toString())) {
                            slot.setAvailable(true);
                            slot.cancel();
                            break;
                        }
                    }
                    break;  // Break out of the loop once the appointment for the specific day is found and updated
                }
            }


            updateCalendarFile(calender);  // opdaterer det dumme filnavn jamie2.txt
            System.out.println("Appointment deleted!");
        } else {
            System.out.println("No appointment found for the given date and time.");
        }
    }





    public void updateCalendarFile(List<Days> calendar) {
        File file = new File("jamie2.txt");
        try {
            PrintWriter writer = new PrintWriter(file);

            // Begin loop through Days
            for (Days day : calendar) {
                LocalDate date = LocalDate.parse(day.getDate());
                String dayOfWeek = date.getDayOfWeek().toString();

                if (dayOfWeek.equals("SATURDAY") || dayOfWeek.equals("SUNDAY")) {
                    writer.println("Date: " + day.getDate());
                    writer.println("Salon is closed on weekends");
                } else {
                    writer.println(day.getDate());
                    TimeSlot[] timeSlots = day.getTimeSlots();

                    // Loop through TimeSlots (updated to handle null TimeSlots)
                    for (TimeSlot timeSlot : timeSlots) {
                        if(timeSlot != null) {
                            writer.println(timeSlot.toFileString());
                        }
                    }
                }
            }

            writer.close();
            System.out.println("Calendar updated.");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void checkCalender() {
        File file = new File("jamie2.txt");
        try {
            Scanner inFile = new Scanner(file);
            while (inFile.hasNextLine()) {
                String s = inFile.nextLine();
                System.out.println(s);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}