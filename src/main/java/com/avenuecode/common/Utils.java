package com.avenuecode.common;

import com.avenuecode.domain.Route;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    private Utils() {
    }

    /**
     * Transform json data into list Route object
     * @param json
     * @return
     * @throws ParseException
     */
    public static List<Route> jsonToListRoutes(String json) throws ParseException {
        JSONParser parse = new JSONParser();
        JSONObject jobj = (JSONObject) parse.parse(json);
        JSONArray jsonArray = (JSONArray) jobj.get("data");

        List<Route> listRoutes = new ArrayList<>();
        Route route;

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            route = new Route();
            route.setSource(jsonObject.get("source").toString());
            route.setTarget(jsonObject.get("target").toString());
            route.setDistance(Integer.parseInt(jsonObject.get("distance").toString()));

            listRoutes.add(route);
        }

        return listRoutes;
    }
}
