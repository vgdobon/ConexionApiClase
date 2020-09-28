package com.tecnara.weather.server.utils;

import com.tecnara.weather.server.domain.Coordinates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Checker {

    public static boolean checkFormat(String messageFromClient){
        // {"lon":46.07, "lat":2.10}
        // {"lon":-156.07, "lat":-87.31}
//       String regExp= "\\{\"lon\":-?\\d{1,3}.\\d{2}, \"lat\":-?\\d{1,2}.\\d{2}}";
       String regExp ="\\{\"lon\":-?\\d{1,3}.\\d{0,6}, \"lat\":-?\\d{1,2}.\\d{0,6}}";
       Matcher matcher= Pattern.compile(regExp).matcher(messageFromClient);
       return matcher.matches();
    }

    public static boolean checkRange(Coordinates cordnts){
        return cordnts.getLat() >= -90 && cordnts.getLat() <= 90
                && cordnts.getLon() >= -180 && cordnts.getLon() <= 180;
    }
}
