
package HarrysSalon;

public class Dage {
    private String dato;
    private TidsInterval[] tidsIntervaller = new TidsInterval[9];


    public Dage(String dato, String[] tidsStrings) {
        this.dato = dato;
        for (int i = 0; i < tidsStrings.length - 1; i++) {
            this.tidsIntervaller[i] = new TidsInterval(tidsStrings[i], tidsStrings[i+1]);
        }
    }


    public TidsInterval[] getTidsIntervaller() {
        return tidsIntervaller;
    }

    public String getDato() {
        return dato;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Dato: ").append(dato).append("\n");
        for (TidsInterval interval : tidsIntervaller) {
            sb.append(interval).append("\n");
        }
        return sb.toString();
    }
}
