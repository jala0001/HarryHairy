package HarrysSalon;

import java.time.LocalDateTime;

public class Kunder {
    private String navn;
    private LocalDateTime startTid;
    private LocalDateTime slutTid;
    private double pris;

    public Kunder(String navn, LocalDateTime startTid, LocalDateTime slutTid, double pris) {
        this.navn = navn;
        this.startTid = startTid;
        this.slutTid = slutTid;
        this.pris = pris;
    }

    public String getNavn() {
        return navn;
    }
    public LocalDateTime getStartTid() {
        return startTid;
    }

    public double getPris() {
        return pris;
    }

    @Override
    public String toString() {
        return "Kunde Navn: " + navn +
                "\nStart Tid: " + startTid +
                "\nSlut Tid: " + slutTid +
                "\nPris: " + pris + " kr.";
    }

}

