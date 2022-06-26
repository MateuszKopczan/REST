package com.example.rest.util;

public class NumberParser {

    public static float parseStringToFloat(String string){
        try{
            return Float.parseFloat(string);
        } catch (NumberFormatException exception){
            return -1f;
        }
    }

    public static int parseStringToInt(String string){
        try{
            return Integer.parseInt(string);
        } catch (NumberFormatException exception){
            return -1;
        }
    }

    public static Long parseStringToLong(String string){
        try{
            return Long.parseLong(string);
        } catch (NumberFormatException exception){
            return -1L;
        }
    }
}
