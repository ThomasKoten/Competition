/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koten;

/**
 *
 * @author TOMKO
 */
public class Time {
    
    private int hours;
    private int minutes;
    private int seconds;
    
    public Time(int hour,int min,int sec){
        this.hours=hour;
        this.minutes=min;
        this.seconds=sec;
    }
    
    public Time(int secondsInput){
        hours=secondsInput/3600;
        minutes=secondsInput/60 %60;
        seconds=secondsInput%60;
    }
    
    public Time(String inputTime){
        String[] arr=inputTime.split(":");
        hours= Integer.parseInt(arr[0]);
        minutes= Integer.parseInt(arr[0]);
        seconds= Integer.parseInt(arr[0]);
    }
    public int getHours(){
        return hours;
    }
     public int getMin(){
        return minutes;
    }
      public int getSec(){
        return seconds;
    }
    
    public int timeToSeconds(){
        return hours*3600+minutes*60+seconds;
    }
    
    public String timeToString(){
        return String.format("%02d:%02d:%02d", hours,minutes,seconds);
    }
}

