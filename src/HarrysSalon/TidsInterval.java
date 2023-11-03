package HarrysSalon;

public class TidsInterval {
    private String start;
    private String slut;
    private Kunder kunder;
    private boolean ledig = true;



    public TidsInterval(String start, String slut) {
        this.start = start;
        this.slut = slut;
        this.kunder = null;
    }

    public String getStart() {
        return start;
    }

    public String getSlut() {
        return slut;
    }


    public void book(Kunder kunde) {
        this.kunder = kunde;
    }

    public void aflys() {
        this.kunder = null;
    }

    public void setLedig(boolean x) {
        this.ledig = x;
    }

    public String toFileString() {
        if (kunder == null) {
            return start + " - " + slut + ": Ledig";
        } else {
            return start + " - " + slut + ": " + kunder.getNavn();
        }
    }


    @Override
    public String toString() {
        if (kunder == null) {
            return start + " - " + slut + ": Ledig";
        } else {
            return start + " - " + slut + ": Booket af " + kunder.getNavn();
        }
    }

}
