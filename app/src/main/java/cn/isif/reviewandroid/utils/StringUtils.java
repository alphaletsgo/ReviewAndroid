package cn.isif.reviewandroid.utils;


public class StringUtils {
    public static boolean isEmpty(String arg){
        if (null!=arg&&arg.isEmpty()){
            return false;
        }
        return true;
    }
}
