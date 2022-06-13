package koten;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    private LocalDate DOB;
    private Gender pohlavi;
    private String klub;
    private Time startTime;
    private int endTime;
    private int finalTime;

    public Zavodnik(String name, String surname) {
        this.jmeno = name;
        this.prijmeni = surname;
        startNum = pocitadloStartNum;
        pocitadloStartNum++;
    }

    public Zavodnik(Zavodnik zav) {
        this.jmeno = zav.jmeno;
        this.prijmeni = zav.prijmeni;
        this.startNum = zav.startNum;
        this.klub = zav.klub;
        this.pohlavi = zav.pohlavi;
        this.poplatek = zav.poplatek;
        this.startTime = zav.startTime;
        this.endTime = zav.endTime;
    }

//    public Zavodnik getZavodnik() {
//        Zavodnik seznam = new Zavodnik(this.jmeno, this.prijmeni, this.startNum);
//        seznam.klub = this.klub;
//        seznam.pohlavi = this.pohlavi;
//        seznam.rocnik = this.rocnik;
//        seznam.poplatek = this.poplatek;
//        return seznam;
//    }
    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }


    public int getStartCislo() {
        return startNum;
    }

    public int getStartTime() {
        return startTime.timeToSeconds();
    }

    public Gender getPohlavi() {
        return pohlavi;
    }

    public void setDOB(String dob){
        this.DOB=LocalDate.parse(dob,DateTimeFormatter.ISO_DATE);
    }
    public void setPohlavi(char gender) {
        if (gender == 'f' || gender == 'F' || gender == 'w') {
            this.pohlavi = Gender.F;
        } else {
            this.pohlavi = Gender.M;
        }
    }

    public void setRocnik(int year) {
        this.rocnik = year;
    }

    public void setKlub(String club) {
        if (!klub.matches("^[A-Z]{2,5}$")) {
            new IllegalArgumentException("Nevalidni nazev klubu.");
        } else {
            this.klub = club;
        }
    }

    public void setStartTime(int time) {
        this.startTime = new Time(time);
    }

    public void setStartTime(int hours, int minutes, int seconds) {
        this.startTime = new Time(hours, minutes, seconds);
    }

    public void setStartTime(String time) {//9:12:0
        this.startTime = new Time(time);
    }

    public void setEndTime(int time) {
        if (startTime == null) {
            throw new startTimeNotSet("Nebyl zadán startovní čas");
        }
        this.endTime = time;
    }

    public void setEndTime(int hours, int minutes, int seconds) {
        if (startTime == null) {
            throw new startTimeNotSet("Nebyl zadán startovní čas");
        }
        this.endTime = TimeTools.timeToSeconds(hours, minutes, seconds);
    }

    public void setEndTime(String time) {//9:12:0
        if (startTime == null) {
            throw new startTimeNotSet("Nebyl zadán startovní čas");
        }
        this.endTime = TimeTools.stringToSeconds(time);
    }

    public int runTime() {
        return endTime - startTime.timeToSeconds();

    }

    public int getRunTime() {
        if (finalTime == 0 && startTime != null && endTime != 0) {
            this.finalTime = runTime();
        }
        return finalTime;
    }

    /**
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
                jmeno, prijmeni, startNum, TimeTools.secondsToTime(startTime.timeToSeconds()), TimeTools.secondsToTime(endTime), TimeTools.secondsToTime(getRunTime()));
    }

    public static void main(String[] args) {
        Zavodnik prvni = new Zavodnik("Dominik", "Šefr");
        prvni.setStartTime(1, 15, 30);
        try {
            prvni.setEndTime(2, 30, 60);
            System.out.println(prvni);
        } catch (startTimeNotSet e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Chyba");

        }
        Zavodnik druhy = new Zavodnik("Radek", "Mocek");
        System.out.println(druhy);
        prvni.setKlub("AAABA");
    }

}
