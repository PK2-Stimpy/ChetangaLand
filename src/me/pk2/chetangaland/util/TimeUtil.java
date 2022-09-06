package me.pk2.chetangaland.util;

public class TimeUtil {
    public static long toms(String time) {
        if(time.length() < 2)
            return -1;

        long   ms = Integer.parseInt(time.substring(0, time.length()-1));
        String lt = time.substring(time.length()-1);

        switch(lt) {
            case "y": ms*=365;
            case "d": ms*=24;
            case "h": ms*=60;
            case "m": ms*=60;
            case "s":
                ms*=1000;
                break;
            default:
                ms = -1;
                break;
        }

        return ms;
    }
}