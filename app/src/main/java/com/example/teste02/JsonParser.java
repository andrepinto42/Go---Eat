package com.example.teste02;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonParser {

    private HashMap<String,String> parseJsonObject(JSONObject objetc){

        HashMap<String,String> datalist = new HashMap<>();

        try{
            String name = objetc.getString("name");

            String latitude = objetc.getJSONObject("geometry").getJSONObject("location").getString("lat");

            String longitude = objetc.getJSONObject("geometry").getJSONObject("location").getString("lng");

            datalist.put("name",name);
            datalist.put("lat",latitude);
            datalist.put("lng",longitude);


        }catch (JSONException e){
            e.printStackTrace();
        }
        return datalist;

    }

    private List<HashMap<String,String>> parseJsonArray(JSONArray jsonArray){
        List<HashMap<String,String>> dataList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                HashMap<String,String> data = parseJsonObject((JSONObject) jsonArray.get(i));

                dataList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    public List<HashMap<String,String>> parseResult (JSONObject object) {
        JSONArray jsonArray = null;

        try{
            jsonArray = object.getJSONArray("results");
        }catch (JSONException e){
            e.printStackTrace();
        }

        return parseJsonArray(jsonArray);
    }

}
