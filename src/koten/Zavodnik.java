package koten;

/**
 *
 * @author TOMKO
 */
public class Zavodnik implements Comparable<Zavodnik> {

    private String jmeno;
    private String prijmeni;
    private int startNum;
    private static int pocitadloStartNum = 1;
    private boolean poplatek;
    private int rocnik;
    private String pohlavi;
    private String klub;
    private long startTime;
    private long endTime;
    private long finalTime;

    public Zavodnik(String name, String surname) {
        this.jmeno = name;
        this.prijmeni = surname;
        startNum = pocitadloStartNum;
        pocitadloStartNum++;
    }

    private Zavodnik(Zavodnik zav) {
        this.jmeno = zav.jmeno;
        this.prijmeni = zav.prijmeni;
        this.startNum = zav.startNum;
        this.klub = zav.klub;
        this.pohlavi = zav.pohlavi;
        this.poplatek = zav.poplatek;
        this.startTime = zav.startTime;
        this.endTime = zav.endTime;
    }

    public Zavodnik getZavodnik() {
        Zavodnik seznam = new Zavodnik(this.jmeno, this.prijmeni, this.startNum);
        seznam.klub = this.klub;
        seznam.pohlavi = this.pohlavi;
        seznam.rocnik = this.rocnik;
        seznam.poplatek = this.poplatek;
        return seznam;
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

    public long getStartTime() {
        return startTime;
    }

    public void setPohlavi(boolean gender) {
        this.pohlavi = gender ? "Muž" : "Žena";
    }

    public void setRocnik(int year) {
        this.rocnik = year;
    }

    public void setKlub(String club) {
        this.klub = club;
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
        this.endTime = TimeTools.timeToSeconds(hours, minutes, seconds);
    }

    public void setEndTime(String time) {//9:12:0
        this.endTime = TimeTools.stringToSeconds(time);
    }

    public long runTime() {
        return endTime - startTime;

    }

    public long getRunTime() {
        if (finalTime == 0 && startTime != 0 && endTime != 0) {
            this.finalTime = runTime();
        }
        return finalTime;
    }

    /**
     *
     * @param zav
     * @return
     */
    @Override
    public int compareTo(Zavodnik zav) {
        return this.prijmeni.compareTo(zav.prijmeni);
    }

    @Override
    public String toString() {
        return String.format("%10s %10s, %5d %15s %15s %15s %n ",
                jmeno, prijmeni, startNum, TimeTools.secondsToTime(startTime), TimeTools.secondsToTime(endTime), TimeTools.secondsToTime(getRunTime()));
    }

}
