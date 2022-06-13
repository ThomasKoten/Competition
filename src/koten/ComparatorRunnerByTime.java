/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koten;

import java.util.Comparator;

/**
 *
 * @author TOMKO
 */
public class ComparatorRunnerByTime {

/**
 *
 * @author 
 */
public class ComparatorRunnerByRunTime implements Comparator<Zavodnik>{

    @Override
    public int compare(Zavodnik o1, Zavodnik o2) {
        return o1.getRunTime() - o2.getRunTime();
        /*if(o1.getRunTime() > o2.getRunTime()){
            return 1;
        } else if(o1.getRunTime() < o2.getRunTime()){
            return -1;
        }
        return 0;*/
        //Double.compare(o1.getRunTime(), o2.getRunTime());
    }

}
}
