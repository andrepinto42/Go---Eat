package com.example.teste02;


import android.content.Context;

import com.example.teste02.SistemData.Restaurante;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Parser {
    //Restaurante Lua Cheia;ChIJ61FATpr-JA0RGBr-plI6hWE;1;4.7;328;[restaurant,food,point_of_interest,establishment];41.5595704|-8.4053544
    public static Map<String, Restaurante> ParseRestaurante(Context context)
    {
        Map<String,Restaurante> mapa = new HashMap<>();

        Context mContext = context;
        InputStream input= null;
        try {
            input = mContext.getAssets().open("Places.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner sc;
        try {
            sc = new Scanner(input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        while(sc.hasNextLine())
        {
            String linha = sc.nextLine();
            String[] arr= linha.split(";");
            String nome = arr[0];
            String ID = arr[1];
            Integer priceLevel;
            if (arr[2].equals("None")) priceLevel = -1;
            else                       priceLevel = Integer.parseInt( arr[2]);

            Float rating = Float.parseFloat( arr[3]);

            Integer numberRating = Integer.parseInt( arr[4]);
            String Keywords = arr[5].substring(1,arr[5].length()-1);
            String[] arrKeywords = Keywords.split(",");

            Float latitude = Float.parseFloat( arr[6]);
            Float longitude = Float.parseFloat(arr[7]);
            Restaurante r = new Restaurante(nome,ID,rating,arrKeywords,latitude,longitude);
            mapa.put(ID,r);

            System.out.println(latitude + " ----- " + longitude);
        }

        return mapa;
    }
}
