package com.avenuecode.common;

import com.avenuecode.domain.Route;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class Utils {

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

        List<Route> listRoutes = new ArrayList<Route>();
        Route route;

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonobj_1 = (JSONObject) jsonArray.get(i);
            route = new Route();
            route.setSource(jsonobj_1.get("source").toString());
            route.setTarget(jsonobj_1.get("target").toString());
            route.setDistance(Integer.parseInt(jsonobj_1.get("distance").toString()));

            listRoutes.add(route);
        }

        return listRoutes;
    }
}
