package koten;

/**
 *
 * @author TOMKO
 */
public final class TimeTools {
    private TimeTools(){
    
}
    public static String formatTime(int hours, int minutes, int seconds){
        String hour;
        String min;
        String sec;
        if(hours<10){
            hour="0"+Integer.toString(hours);
        }else{
            hour=Integer.toString(hours);
        }
        if(minutes<10){
            min="0"+Integer.toString(minutes);
        }else{
            min=Integer.toString(minutes);
        }
        if(seconds<10){
             sec="0"+Integer.toString(seconds);
        }else{
            sec=Integer.toString(seconds);
        }
        return String.format("%s:%s:%s",hour,min,sec);
    }

    public static String secondsToTime(long inputSeconds) {
        int seconds = (int)inputSeconds % 60;
        int minutes = (int)inputSeconds / 60;
        int hours=minutes/60;
        minutes=minutes%60;
        
        return formatTime(hours,minutes,seconds);
    }
    public static long timeToSeconds(int hours, int minutes, int seconds){
        return hours*3600+minutes*60+seconds;
    }
    public static long stringToSeconds(String inputTime){
        String[] parts=inputTime.split(":");
        int hours=Integer.parseInt(parts[0]);
        int minutes=Integer.parseInt(parts[1]);
        int seconds=Integer.parseInt(parts[2]);
        
        return hours*3600+minutes*60+seconds;
    }
//    public static void main(String[] args) {
//        System.out.println(secondsToTime(22388));
//        System.out.println(stringToSeconds("06:13:08"));
//
//    }
}
