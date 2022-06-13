
package koten;

import java.util.ArrayList;
import java.util.Scanner;


public class CompetitionApp {
    final private static Scanner sc=new Scanner(System.in);
    private static Competition competition;

    public static void main(String[] args) {
        createCompetition();
        registerRunners();
        setStartTime();
        printRegisteredRunners();
    }
     public static void createCompetition(){
        System.out.println("Zadej jmeno zavodu");
        String name = sc.nextLine();
        competition = new Competition(name);
    }
    
    public static void registerRunners(){
        String name;
        String surname;
        System.out.println("Zadej jména a přijmení běžců:");
        while (!(name = sc.next()).equals("konec")) {
            surname = sc.next();
            competition.registerRunner(name, surname);
        }
    }
    
    public static void setStartTime(){
        System.out.println("Zadej zacatek startu: ");
        String input = sc.next();
        
        System.out.println("Zadej rozdíl startovního času následujícího závodníka: ");
        int offset=sc.nextInt();
        int parsedTime=TimeTools.stringToSeconds(input);
        competition.setRunStartTime(offset, parsedTime);
        
    }
    public static void printRegisteredRunners() {
        ArrayList<Zavodnik> copy = competition.getRunnersSortedbySurName();
        for (Zavodnik runner : copy) {
            System.out.printf("%3d. %10s %10s %15s\n", runner.getStartCislo(), runner.getJmeno(), runner.getPrijmeni(), TimeTools.secondsToTime(runner.getStartTime()));
        }
    }
    
}
