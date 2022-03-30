package koten;

/**
 *
 * @author TOMKO
 */
public class Zavodnik {

    private String jmeno;
    private String prijmeni;
    private int startNum;
    private static int pocitadloStartNum = 1;
    private boolean poplatek;
    private int rocnik;
    private String pohlavi;
    private long startTime;
    private long endTime;
    private long finalTime;

    private Zavodnik(String name, String surname) {
        this.jmeno = name;
        this.prijmeni = surname;
        startNum = pocitadloStartNum;
        pocitadloStartNum++;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public int getStartCislo() {
        return startNum;
    }

    public void setPohlavi(boolean gender) {
        this.pohlavi = gender ? "Muž" : "Žena";
    }

    public void setRocnik(int year) {
        this.rocnik = year;
    }

    public void setStartTime(long time) {
        this.startTime = time;
    }

    public void setStartTime(int hours, int minutes, int seconds) {
        this.startTime = TimeTools.timeToSeconds(hours, minutes, seconds);
    }

    public void setStartTime(String time) {//9:12:0
        this.startTime = TimeTools.stringToSeconds(time);
    }

    public void setEndTime(long time) {
        this.endTime = time;
    }

    public void setEndTime(int hours, int minutes, int seconds) {
        this.startTime = TimeTools.timeToSeconds(hours, minutes, seconds);
    }
    public void setEndTime(String time) {//9:12:0
        this.startTime = TimeTools.stringToSeconds(time);
    }

    public long runTime() {
        this.finalTime = endTime - startTime;
        return finalTime;
    }

}
