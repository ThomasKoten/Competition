/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koten;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 *
 * @author TOMKO
 */
public class Competition {

    private ArrayList<Zavodnik> runners;
    private String name;

    public Competition(String name) {
        this.name = name;
        runners = new ArrayList<>();
    }

    public void registerRunner(String name, String surname) {
        Zavodnik newRunner = new Zavodnik(name, surname);
        this.runners.add(newRunner);
    }

    public void removeRunner(int runNum) {
        Zavodnik runner = findRunner(runNum);
        boolean runnerRemoved = runners.remove(runner);

        if (!runnerRemoved) {
            throw new NoSuchElementException("Runner not found.");
        }
    }

    public ArrayList<Zavodnik> getRunners() {
        ArrayList<Zavodnik> seznam = new ArrayList<Zavodnik>();
        for (int i = 0; i < runners.size(); i++) {
            seznam.add(runners.get(i).getZavodnik());
        }
        return seznam;
    }

    public static final Comparator<Zavodnik> compTime = Comparator.comparing(Zavodnik::getRunTime);
    //reference na metoda :: Najdi třídu Zavodnik a zavolej metodu 

    public ArrayList<Zavodnik> sortByNames() {
        Collections.sort(runners);
        return getRunners();
    }
     public ArrayList<Zavodnik> getRunnersSortedbySurName(){
        sortByNames();
        return getRunners();
    }

    public ArrayList<Zavodnik> sortByTime() {
        Collections.sort(runners, compTime);
        return getRunners();
    }

    public void addRunner(String name, String surname) {
        Zavodnik runner = new Zavodnik(name, surname);
        this.runners.add(runner);
    }

    public Zavodnik findRunner(int runNum) {
        for (Zavodnik zav : runners) {
            if (zav.getStartCislo() == runNum) {
                return zav ;
            }
        }
        throw new NoSuchElementException("Zavodnik nenalezen");
    }

    public void setRunStartTimeAll(long time) {
        for (Zavodnik zav : runners) {
            zav.setStartTime(time);
        }

    }

    public void setRunStartTimeAll(int hour, int minutes, int seconds) {
        for (Zavodnik zav : runners) {
            zav.setStartTime(hour, minutes, seconds);
        }

    }

    public void setRunStartTimeAll(String time) {
        for (Zavodnik zav : runners) {
            zav.setStartTime(time);
        }
    }

    public void setRunStartTime(int diff, long time) {
        for (Zavodnik zav : runners) {
            int mult = zav.getStartCislo();
            zav.setStartTime(time + mult * (diff * 60));

        }

    }

    public void setRunStartTime(int diff, int hour, int minutes, int seconds) {
        for (Zavodnik zav : runners) {
            int mult = zav.getStartCislo();
            zav.setStartTime(hour, minutes + (mult * diff), seconds);
        }
    }

    public void setStartTimeOf(int runNum, int hours, int minutes, int seconds) {
        findRunner(runNum).setStartTime(hours, minutes, seconds);
    }

    public void setEndTimeOf(int runNum, int hours, int minutes, int seconds) {
        findRunner(runNum).setEndTime(hours, minutes, seconds);
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Zavodnik zav : runners) {
            builder.append(zav.toString()).append("\n");
        }
        return builder.toString();
    }

}
