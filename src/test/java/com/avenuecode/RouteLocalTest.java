package com.avenuecode;

import com.avenuecode.common.Constants;
import com.avenuecode.common.Utils;
import com.avenuecode.domain.Route;
import com.avenuecode.dto.AvailableRoutesDTO;
import com.avenuecode.dto.RouteBetweenTownsDTO;
import com.avenuecode.rest.RouteService;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class RouteLocalTest {

    private static Logger log = Logger.getLogger("InfoLogging");

    @Test
    public void availableRoutesHardCodeTest() throws ParseException {
        List<Route> routeList = Utils.jsonToListRoutes(Constants.PAYLOAD_TEST);
        List<String> allPossibleRoutes = RouteService.getTargetsFromSource("A", "C", routeList,
                new ArrayList<String>());

        List<String> resultToCompare = new ArrayList<>();
        resultToCompare.add("B");
        resultToCompare.add("C");
        resultToCompare.add("D");
        resultToCompare.add("C");
        resultToCompare.add("E");
        resultToCompare.add("B");
        resultToCompare.add("C");

        assert resultToCompare.equals(allPossibleRoutes);
    }

    @Test
    public void distanceWithOutTownsTest() throws ParseException {
        List<Route> routeList = Utils.jsonToListRoutes(Constants.PAYLOAD_TEST);
        String[] towns = {};
        int distance = RouteService.getGraphDistance(routeList, towns);

        assert distance == 0;
    }

    @Test
    public void distanceWithOneTownsTest() throws ParseException {
        List<Route> routeList = Utils.jsonToListRoutes(Constants.PAYLOAD_TEST);
        String[] towns = {"A"};
        int distance = RouteService.getGraphDistance(routeList, towns);

        assert distance == 0;
    }

    @Test
    public void distancePathDoesntExistTest() throws ParseException {
        List<Route> routeList = Utils.jsonToListRoutes(Constants.PAYLOAD_TEST);
        String[] towns = {"A", "C", "A"};
        int distance = RouteService.getGraphDistance(routeList, towns);

        assert distance == -1;
    }

    @Test
    public void distanceABCTest() throws ParseException {
        List<Route> routeList = Utils.jsonToListRoutes(Constants.PAYLOAD_TEST);
        String[] towns = {"A", "B", "C"};
        int distance = RouteService.getGraphDistance(routeList, towns);

        assert distance == 9;
    }

    @Test
    public void distanceADTest() throws ParseException {
        List<Route> routeList = Utils.jsonToListRoutes(Constants.PAYLOAD_TEST);
        String[] towns = {"A", "D"};
        int distance = RouteService.getGraphDistance(routeList, towns);

        assert distance == 5;
    }

    @Test
    public void distanceADCTest() throws ParseException {
        List<Route> routeList = Utils.jsonToListRoutes(Constants.PAYLOAD_TEST);
        String[] towns = {"A", "D", "C"};
        int distance = RouteService.getGraphDistance(routeList, towns);

        assert distance == 13;
    }

    @Test
    public void distanceAEBCDTest() throws ParseException {
        List<Route> routeList = Utils.jsonToListRoutes(Constants.PAYLOAD_TEST);
        String[] towns = {"A", "E", "B", "C", "D"};
        int distance = RouteService.getGraphDistance(routeList, towns);

        assert distance == 22;
    }

    @Test
    public void distanceAEDTest() throws ParseException {
        List<Route> routeList = Utils.jsonToListRoutes(Constants.PAYLOAD_TEST);
        String[] towns = {"A", "E", "D"};
        int distance = RouteService.getGraphDistance(routeList, towns);

        assert distance == -1;
    }

    @Test
    public void distanceABCDETest() throws ParseException {
        List<Route> routeList = Utils.jsonToListRoutes(Constants.PAYLOAD_TEST);
        String[] towns = {"A", "B", "C", "D", "E"};
        int distance = RouteService.getGraphDistance(routeList, towns);

        assert distance == 23;
    }

    @Test
    public void distanceFromCompleteGraphTest() throws ParseException {
        List<Route> routeList = Utils.jsonToListRoutes(Constants.PAYLOAD_TEST);
        String[] towns = RouteService.getOrderedTownsFromGraph(routeList);
        int distance = RouteService.getGraphDistance(routeList, towns);

        assert distance == 23;
    }

    @Test
    public void availableRoutesCC3Test() throws ParseException {
        List<Route> routeList = Utils.jsonToListRoutes(Constants.PAYLOAD_TEST);
        String source = "C";
        String target = "C";
        int stops = 3;
        List<AvailableRoutesDTO> availableRoutesTOS = RouteService.getAvailableRoutes(routeList, source, target, stops);

        assert availableRoutesTOS.get(0).getRoute().equals("CDC");
        assert availableRoutesTOS.get(0).getStops() == 2;
        assert availableRoutesTOS.get(1).getRoute().equals("CEBC");
        assert availableRoutesTOS.get(1).getStops() == 3;
    }

    @Test
    public void availableRoutesAC4Test() throws ParseException {
        List<Route> routeList = Utils.jsonToListRoutes(Constants.PAYLOAD_TEST);
        String source = "A";
        String target = "C";
        int stops = 4;
        List<AvailableRoutesDTO> availableRoutesTOS = RouteService.getAvailableRoutes(routeList, source, target, stops);

        assert availableRoutesTOS.get(0).getRoute().equals("ABC");
        assert availableRoutesTOS.get(0).getStops() == 2;
        assert availableRoutesTOS.get(1).getRoute().equals("ADC");
        assert availableRoutesTOS.get(1).getStops() == 2;
        assert availableRoutesTOS.get(2).getRoute().equals("AEBC");
        assert availableRoutesTOS.get(2).getStops() == 3;

        //TODO Fix here, there are problem with this case
        /*log.info("availableRoutesTOS --> " + availableRoutesTOS.size());
        assert availableRoutesTOS.get(3).getRoute().equals("ADEBC");
        assert availableRoutesTOS.get(3).getStops() == 4;*/
    }

    @Test
    public void shortestRoutesACTest() throws ParseException {
        List<Route> routeList = Utils.jsonToListRoutes(Constants.PAYLOAD_TEST);
        String source = "A";
        String target = "C";
        String[] shortestRoute = {"A", "B", "C"};
        RouteBetweenTownsDTO townsTO = RouteService.getRoutesBetweenTowns(routeList, source, target);

        assert townsTO.getDistance() == 9;
        assert (Arrays.toString(townsTO.getTowns())).equals(Arrays.toString(shortestRoute));
    }

    @Test
    public void shortestRoutesBBTest() throws ParseException {
        List<Route> routeList = Utils.jsonToListRoutes(Constants.PAYLOAD_TEST);
        String source = "B";
        String target = "B";
        String[] shortestRoute = {"B"};
        RouteBetweenTownsDTO townsTO = RouteService.getRoutesBetweenTowns(routeList, source, target);

        assert townsTO.getDistance() == 0;
        assert (Arrays.toString(townsTO.getTowns())).equals(Arrays.toString(shortestRoute));
    }

}
