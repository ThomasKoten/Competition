/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koten;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Collator;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author TOMKO
 */
public class Competition {

    private ArrayList<Zavodnik> runners;
    private String name;

    public void loadStart(File startFile) throws FileNotFoundException, IOException {
        ArrayList<Integer> errorLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(startFile))) {

            int lineNum = 1;
            String line, firstName, lastName, dob;
            char gender;
            Zavodnik z;
            br.readLine(); //preskocim hlavicku
            while ((line = br.readLine()) != null) {
                lineNum++;
                String[] parts = line.split("[ ]+");
                firstName = parts[0];
                lastName = parts[1];
                dob = parts[2];
                gender = parts[3].charAt(0);
                z = new Zavodnik(firstName, lastName);
                try {
                    z.setDOB(dob);
                } catch (DateTimeParseException e) {
                    z.setDOB("cas");
                    errorLines.add(lineNum);
                }
                z.setPohlavi(gender);
                runners.add(z);
            }
        }
        if (!errorLines.isEmpty()) {
            throw new RuntimeException("Chyba na řádcích" + errorLines.toString());
        }
    }

    public void loadFinish(File finishFile) throws FileNotFoundException {
        try (Scanner in = new Scanner(finishFile)) {
            int number;
            String finishTime;
            in.nextLine();
            while (in.hasNext()) {
                number = in.nextInt();
                finishTime = in.next();
                findRunner(number).setEndTime(finishTime);
            }
        }
    }

    public void saveToFile(File results) throws IOException {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(results)))) {
            sortByTime();
            int n = 1;
            for (Zavodnik runner : runners) {
                pw.print(n + ". ");
                pw.println(runner.toString());
                n++;
            }
        }
    }

    public void savetoBinary(File results) throws FileNotFoundException, IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(results))) {
            sortByTime();
            for (Zavodnik runner : runners) {
                out.writeInt(runners.size());
                out.writeUTF(runner.getJmeno());
                out.writeInt(runner.getPrijmeni().length());
                for (int i = 0; i < runner.getPrijmeni().length(); i++) {
                    out.writeChar(runner.getPrijmeni().charAt(i));
                }
                out.writeInt(runner.getRunTime());
            }
        }
    }

    public String readBinaryResults(File results) throws FileNotFoundException, IOException {
        StringBuilder sb = new StringBuilder();
        try (DataInputStream in = new DataInputStream(new FileInputStream(results))) {
            boolean end = false;
            int nRunners, nLetters, time = 0;
            int rank = 1;
            String name = "", surname = "";
            while (!end) {
                try {
                    nRunners = in.readInt();
                    for (int i = 0; i < nRunners; i++) {
                        sb.append(rank).append(". ");
                        name = in.readUTF();
                        nLetters = in.readInt();
                        surname = "";
                        for (int j = 0; j < nLetters; j++) {
                            surname += in.readUTF();
                        }
                        time = in.readInt();
                        sb.append(String.format("%2d. %10s%10s%10s", rank, name, surname, TimeTools.secondsToTime(time)));
                        rank++;
                    }
                    rank = 1;
                    sb.append("\n");
                } catch (EOFException e) {
                    end = true;
                }
            }

        }
        return sb.toString();
    }

    public static final Collator col = Collator.getInstance(new Locale("cs", "CZ"));
    public static final Comparator<Zavodnik> compTime = Comparator.comparing(Zavodnik::getRunTime);
    public static final Comparator<Zavodnik> COMP_BY_NAME = (Zavodnik zav1, Zavodnik zav2) -> {
        int value = col.compare(zav1.getPrijmeni(), zav2.getPrijmeni());
        if (value == 0) {
            value = col.compare(zav1.getPrijmeni(), zav2.getPrijmeni());
        }
        return value;
    };
    //reference na metoda :: Najdi třídu Zavodnik a zavolej metodu 

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
        ArrayList<Zavodnik> seznam = new ArrayList<>();
        for (Zavodnik zav : runners) {
            seznam.add(new Zavodnik(zav));
        }
        return seznam;
    }

    public class ComparatorRunTime implements Comparator<Zavodnik> {

        @Override
        public int compare(Zavodnik zav1, Zavodnik zav2) {
            return (int) zav1.getRunTime() - (int) zav2.getRunTime();
        }
    }

    private void sortByNames() {
        Collections.sort(runners);
    }

    public ArrayList<Zavodnik> getRunnersSortedbySurName() {
        sortByNames();
        return getRunners();
    }

    private void sortByTime() {
        Collections.sort(runners, compTime);
    }

    private void sortByAge() {
        Collections.sort(runners, new Comparator<Zavodnik>() {
            @Override
            public int compare(Zavodnik zav1, Zavodnik zav2) {
                return zav1.getRunTime() - zav2.getRunTime();
            }
        });
    }

//    public void addRunner(String name, String surname) {
//        Zavodnik runner = new Zavodnik(name, surname);
//        this.runners.add(runner);
//    }
    public Zavodnik findRunner(int runNum) {
        for (Zavodnik zav : runners) {
            if (zav.getStartCislo() == runNum) {
                return zav;
            }
        }
        throw new NoSuchElementException("Zavodnik nenalezen");
    }

    public void setStartTimeAll(int time) {
        for (Zavodnik zav : runners) {
            zav.setStartTime(time);
        }

    }

    public void setStartTimeAll(int hour, int minutes, int seconds) {
        for (Zavodnik zav : runners) {
            zav.setStartTime(hour, minutes, seconds);
        }

    }

    public void setStartTimeAll(String time) {
        for (Zavodnik zav : runners) {
            zav.setStartTime(time);
        }
    }

    public void setRunStartTime(int diffInMin, int time) {
        for (Zavodnik zav : runners) {
            int mult = zav.getStartCislo();
            zav.setStartTime(time + mult * (diffInMin * 60));

        }

    }

    public void setRunStartTime(int diff, int hour, int minutes, int seconds) {
        for (Zavodnik zav : runners) {
            int mult = zav.getStartCislo();
            zav.setStartTime(hour, minutes + (mult * diff), seconds);
        }
    }

    public void setRunStartTimeOf(int runNum, int hours, int minutes, int seconds) {
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
