package HarrysSalon;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;


public class BookingSystem {
    Scanner scanner = new Scanner(System.in);
    boolean kørProgram = true;
    List<Kunder> bookingListe = new ArrayList<>();
    List<Dage> Kalender = new ArrayList<>();
    BookingKalender bookingKalender = new BookingKalender();
    //Dage dage = new Dage("dato", new String[]{"10", "11", "12", "13", "14", "15", "16", "17"}); // skal nok bare slettes
    Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        new BookingSystem().run();
    }

    public void run() {
        kodeord();
        File file = new File("tider.txt");

        if (!file.exists() || file.length() == 0) {
            for (int i = -14; i < 427; i++) { // ÆNDRING
                LocalDateTime dagsDato = LocalDateTime.now().plusDays(i);
                String dato = dagsDato.toLocalDate().toString();
                String dagPåUgen = dagsDato.getDayOfWeek().toString();
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                    PrintStream ps = new PrintStream(fileOutputStream);
                    Dage dage = new Dage(dato, new String[]{"10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"});
                    if (dagPåUgen.equals("SATURDAY") || dagPåUgen.equals("SUNDAY")) { // SKAL SKRIVES PÅ ENGENSK DA VI BRUGER getDayOfWeek
                        ps.println("Dato: " + dato + "\nSalonen er lukket i weekenden");
                    } else {
                        ps.println(dage);
                    }
                    Kalender.add(dage);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        updaterKalenderFil(Kalender); // ÆNDRING - denne gør at vi får kalender oversigten før vi booker.

        while (kørProgram) {
            menu();
        }
    }

    public void kodeord() {
        System.out.println("Indtast kodeord");
        String password = "hairyharry";
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        if (input.equals(password)) {
            System.out.println("Korrekt kodeord");
        } else {
            System.out.println("Forkert kodeord, prøv igen");
            kodeord();
        }
    }

    public void menu() {
        System.out.println("*** VELKOMMEN TIL HARRY'S SALON ***\n");
        System.out.println("1. Opret aftale");
        System.out.println("2. Aflys aftale");
        System.out.println("3. Se Kalenderoversigt");
        System.out.println("4. Søg efter dato");
        System.out.println("5. Tjek indtjening (dato)");
        System.out.println("9. Afslut program");
        menuValg();
    }

    public void menuValg() {
        int valg = in.nextInt();
        in.nextLine();
        switch (valg) {
            case 1 -> opretAftale();
            case 2 -> aflysAftale();
            case 3 -> kalenderOversigt();
            case 4 -> {
                System.out.print("Indtast dato (YYYY-MM-DD). År kan kun være (2023/2024) ");
                String inputDato = scanner.nextLine();
                søgDato(inputDato);
            }
            case 5 -> { // ÆNDRING
                System.out.println("Indtast dato (YYYY-MM-DD). År kan kun være (2023/2024):");
                String datoInput = in.nextLine();
                LocalDate indtastetDato = LocalDate.parse(datoInput); // Konverterer den indtastede string til en LocalDate
                LocalDate iDag = LocalDate.now();
                if (indtastetDato.isAfter(iDag)) {
                    System.out.println("Du kan kun søge på dage op til en dag før dags dato");
                    menu();
                } else {
                    seTotalBeløb(datoInput);
                }
            }

            case 9 -> kørProgram = false;
            default -> System.out.println("Dette er ikke en valgmulighed, prøv igen");
        }
    }

    public static void søgDato(String søgtDato) {
        String[] tider = {
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
            File file = new File("tider.txt");
            Scanner inFile = new Scanner(file);
            boolean fundetDato = false;

            while (inFile.hasNextLine()) {
                String linje = inFile.nextLine();

                if (linje.equals(søgtDato)) {
                    fundetDato = true;
                    System.out.println(linje);

                    for (int i = 0; i < tider.length; i++) {
                        if (inFile.hasNextLine()) {
                            linje = inFile.nextLine();
                            if (linje.equals("null")) {
                                break; // Denne break må ikke være i en for-løkke. Find anden måde at afslutte på.
                            }
                            System.out.println(linje);
                        }
                    }
                    break; // NEJ NEJ ENDNU EN BREAK! tsk tsk tsk.
                }
            }

            if (!fundetDato) {
                System.out.println("Ingen reservationer fundet for denne dato.");
            }

            inFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("En fejl opstod.");
            e.printStackTrace();
        }
    }


    public void opretAftale() {
        System.out.println("Vil du se kaldenderen for en given dato, før du booker? (ja/nej)");
        String svar = in.nextLine();
        if (svar.equalsIgnoreCase("ja")){
            System.out.println("Indtast dato: (YYYY-MM-DD). År kan kun være (2023/2024)");
            String inputDato = scanner.nextLine();
            søgDato(inputDato);
        }

        System.out.println("Indtast kundens navn: ");
        String navn = in.nextLine();
        System.out.println("Indtast år: (YYYY). År kan kun være (2023/2024)");
        int år = in.nextInt();
        in.nextLine();
        System.out.println("Indtast måned: (mm- eks: 06)");
        int måned = in.nextInt();
        in.nextLine();
        System.out.println("Indtast dag: (dd - eks: 03)");
        int dag = in.nextInt();
        in.nextLine();
        System.out.println("Indtast tid: (eks: 10, 13 eller 16");
        int time = in.nextInt();
        in.nextLine();
        int min = 0;
        System.out.println("Indtast pris");
        double prise = in.nextDouble();
        in.nextLine();
        LocalDateTime startTime = LocalDateTime.of(år, måned, dag, time, min);
        int slutTime = time + 1;
        LocalDateTime slutTid = LocalDateTime.of(år, måned, dag, slutTime, min);
        opretAftale_2(navn, startTime, slutTid, prise);
    }


    public void opretAftale_2(String navn, LocalDateTime startTid, LocalDateTime slutTid, double pris) {
        if (bookingKalender.erBookingTilladt(startTid, slutTid)) {
            Kunder nyKunde = new Kunder(navn, startTid, slutTid, pris);
            for (Dage dag : Kalender) {
                if (dag.getDato().equals(startTid.toLocalDate().toString())) {

                   // String timeSlot = startTid.toLocalTime() + " - " + slutTid.toLocalTime(); // Kan ikke huske hvorfor jeg oprettede denne linje
                    TidsInterval[] tidsIntervaller = dag.getTidsIntervaller();
                    for (TidsInterval interval : tidsIntervaller) {
                        if (interval.getStart().equals(startTid.toLocalTime().toString()) && interval.getSlut().equals(slutTid.toLocalTime().toString())) {
                            interval.book(nyKunde);
                            break; // NO BREAK HONAY
                        }
                    }
                    updaterKalenderFil(Kalender);
                }
            }

            File file = new File("bookedeTider.txt");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                PrintStream ps = new PrintStream(fileOutputStream);
                bookingListe.add(nyKunde);
                ps.println(nyKunde);
                ps.println();
                menu();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
           /* File file1 = new File("tider.txt"); // har ikke det store formål i min kode men tør ikke slette noget nu hvor programmet fungerer.
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file1, true); // intet formål
                PrintStream ps = new PrintStream(fileOutputStream); // intet formål

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            */
        }
    }

    public void aflysAftale() {
        System.out.println("Indtast år. År kan kun være (2023/2024)");
        int år = in.nextInt();
        in.nextLine();
        System.out.println("Indtast måned:");
        int måned = in.nextInt();
        in.nextLine();
        System.out.println("Indtast dag:");
        int dag = in.nextInt();
        in.nextLine();
        System.out.println("Indtast tid: ");
        int time = in.nextInt();
        in.nextLine();
        LocalDateTime aftaleTid = LocalDateTime.of(år, måned, dag, time, 0);


        Kunder kunderSomSkalSlettes = null;
        for (Kunder kunde : bookingListe) {
            if (kunde.getStartTid().equals(aftaleTid)) {
                kunderSomSkalSlettes = kunde;
                break; // Damn
            }
        }

        if (kunderSomSkalSlettes != null) {
            bookingListe.remove(kunderSomSkalSlettes);


            for (Dage dagObj : Kalender) {
                if (dagObj.getDato().equals(aftaleTid.toLocalDate().toString())) {
                    TidsInterval[] tidsIntervaller = dagObj.getTidsIntervaller();
                    for (TidsInterval interval : tidsIntervaller) {
                        if (interval != null && interval.getStart().equals(aftaleTid.toLocalTime().toString())) {
                            interval.setLedig(true);
                            interval.aflys();
                            break; // åh nej
                        }
                    }
                    break; // fuck
                }
            }


            updaterKalenderFil(Kalender);
            System.out.println("Aftale aflyst!");
        } else {
            System.out.println("Ingen aftaler fundet på den givne tid, desværre");
        }
    }


    public void updaterKalenderFil(List<Dage> kalender) {
        File file = new File("tider.txt");
        try {
            PrintWriter writer = new PrintWriter(file);

            for (Dage dag : kalender) {
                LocalDate dato = LocalDate.parse(dag.getDato());
                String dagPåUgen = dato.getDayOfWeek().toString();

                if (dagPåUgen.equals("SATURDAY") || dagPåUgen.equals("SUNDAY")) { // SKAL SKRIVES PÅ ENGENSK DA VI BRUGER getDayOfWeek
                    writer.println("Dato: " + dag.getDato());
                    writer.println("Salonen er lukket i weekenden");
                } else {
                    writer.println(dag.getDato());
                    TidsInterval[] tidsIntervaller = dag.getTidsIntervaller();

                    for (TidsInterval tidsInterval : tidsIntervaller) {
                        if (tidsInterval != null) {
                            writer.println(tidsInterval.toFileString());
                        }
                    }
                }
            }

            writer.close();
            System.out.println("Kalender opdateret");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public void kalenderOversigt() {
        File file = new File("tider.txt");
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

    public void seTotalBeløb(String dato) {
        double totalIndtjening = 0;

        System.out.println("Indbetalinger for den " + dato + ":");
        for (Kunder kunde : bookingListe) {
            if (kunde.getStartTid().toLocalDate().toString().equals(dato)) {
                totalIndtjening += kunde.getPris();

                System.out.println("Kunde: " + kunde.getNavn() + ", Betalt beløb: " + kunde.getPris() + "kr.");
            }
        }

        System.out.println("Samlet indtjening for dagen: " + totalIndtjening + "kr.");
    }


}