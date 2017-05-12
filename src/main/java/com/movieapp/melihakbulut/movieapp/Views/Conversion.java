package com.movieapp.melihakbulut.movieapp.Views;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by melih.akbulut on 14.04.2017.
 */
public class Conversion {

    public static String toDate(String date){
        String[] arr=date.split("-");
        String months[]={""," Ocak "," Şubat "," Mart "," Nisan "," Mayıs "," Haziran "," Temmuz "," Ağustos "," Eylül "," Ekim "," Kasım "," Aralık "};
        return arr[2]+months[Integer.parseInt(arr[1])]+arr[0];
    }

    public static String getLang(String lang){
        HashMap<String,String> map=new HashMap<String,String>();
        map.put("en","İngilizce");
        map.put("tr","Türkçe");
        map.put("de","Almanca");
        map.put("fr","Fransızca");
        return map.get(lang);

    }

    public static String toCurrency(String cur){
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        return formatter.format(Integer.parseInt(cur));
    }
}
