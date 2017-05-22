package com.avtobus.projekt.avtobus;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaka on 19.5.2017.
 */
public class JsonParser {
    public String busesName;
    public JSONArray buses, dataJsonArr, bussArrivals = new JSONArray();


    public List<Maindata> data = new ArrayList<>();
    public List<Integer> integer = new ArrayList<>();

    public List<Maindata> getArrivals(JSONObject object) {
        try {
            // dataJsonArr = object.getJSONObject("stations");
            dataJsonArr = object.getJSONArray("stations");
            for (int j = 0;j < dataJsonArr.length(); j++) {
                JSONObject postaja = dataJsonArr.getJSONObject(j);
                buses = postaja.getJSONArray("buses");
                for(int i = 0; i <buses.length(); i++){
                    JSONObject busName = buses.getJSONObject(i);
                    bussArrivals = busName.getJSONArray("arrivals");
                    Maindata mainD = new Maindata();
                    mainD.seznamPrihodov = bussArrivals;
                    bussArrivals = mainD.seznamPrihodov;
                    Log.d("wtf", String.valueOf(mainD.seznamPrihodov));
                    data.add(mainD);
                }

            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return data;// vrnemo seznam

    }


    public List<Maindata> getStations(JSONObject object) {
        try {
           // dataJsonArr = object.getJSONObject("stations");
           dataJsonArr = object.getJSONArray("stations");
            for (int j = 0;j < dataJsonArr.length(); j++) {
                JSONObject postaja = dataJsonArr.getJSONObject(j);
                buses = postaja.getJSONArray("buses");
                for(int i = 0; i <buses.length(); i++){
                    JSONObject busName = buses.getJSONObject(i);
                    busesName = busName.getString("direction");
                    Maindata mainD = new Maindata();
                    mainD.nameStation = busesName;
                    busesName = mainD.nameStation;
                    Log.d("wtf",mainD.nameStation);
                    data.add(mainD);
                }

            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return data;// vrnemo seznam

    }
}
