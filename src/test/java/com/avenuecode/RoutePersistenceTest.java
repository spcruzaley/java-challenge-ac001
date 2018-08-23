package com.avenuecode;

import com.avenuecode.domain.Route;
import com.avenuecode.repository.RouteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RoutePersistenceTest {
    @Autowired
    private RouteRepository repository;

    private static Logger log = Logger.getLogger("InfoLogging");

    @Test
    public void insertTest() {
        repository.truncateTable();
        int rowAffected = 0;

        Route routeOne = new Route("A", "B", 5);
        rowAffected = repository.insert(routeOne, 1);
        Route routeTwo = new Route("A", "B", 5);
        rowAffected += repository.insert(routeTwo, 1);

        assert rowAffected == 2;
    }

    @Test
    public void insertAndGetDataTest() {
        repository.truncateTable();
        int idRouteGroup = 1;

        Route routeOne = new Route("A", "B", 7);
        repository.insert(routeOne, idRouteGroup);
        Route routeTwo = new Route("B", "C", 4);
        repository.insert(routeTwo, idRouteGroup);
        Route routeTree = new Route("D", "A", 6);
        repository.insert(routeTree, idRouteGroup);

        List<Route> routesFounded = repository.findByIdRouteGroup(idRouteGroup);

        assert routesFounded.size() == 3;
        assert routesFounded.get(1).getSource().equals("B");
        assert routesFounded.get(1).getTarget().equals("C");
    }

    /*@Test
    public void availableRoutesFromDBTest() throws ParseException {
        repository.truncateTable();
        List<Route> routeList = Utils.jsonToListRoutes(Constants.REQUEST_DATA_AVAILABLE_ROUTES, 1);

        //Insert all the routes generated
        for (Route route: routeList) {
            repository.insert(route);
        }

        //Get the routes from DB
        List<Route> routeListFromDB = repository.findByIdRouteGroup(1);

        //Get the possible routes
        String source = "A";
        String target = "C";
        List<String> allPossibleRoutes = RouteBuilder.getTargetsFromSource(source, target, routeListFromDB,
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
    public void distanceTest() throws ParseException {
        repository.truncateTable();
        List<Route> routeList = Utils.jsonToListRoutes(Constants.REQUEST_DATA_AVAILABLE_ROUTES, 1);

        //Insert all the routes generated
        for (Route route: routeList) {
            repository.insert(route);
        }

        //Get the routes from DB
        List<Route> routeListFromDB = repository.findByIdRouteGroup(1);

        String[] townsABC = {"A", "B", "C"};
        int distanceABC = RouteBuilder.getGraphDistance(townsABC, routeListFromDB);
        String[] townsAD = {"A", "D"};
        int distanceAD = RouteBuilder.getGraphDistance(townsAD, routeList);
        String[] townsADC = {"A", "D", "C"};
        int distanceADC = RouteBuilder.getGraphDistance(townsADC, routeList);
        String[] townsAEBCD = {"A", "E", "B", "C", "D"};
        int distanceAEBCD = RouteBuilder.getGraphDistance(townsAEBCD, routeList);

        assert distanceABC == 9;
        assert distanceAD == 5;
        assert distanceADC == 13;
        assert distanceAEBCD == 22;
    }

    @Test
    public void distanceTwoTest() throws ParseException {
        repository.truncateTable();
        List<Route> routeList = Utils.jsonToListRoutes(Constants.REQUEST_DATA_AVAILABLE_ROUTES, 1);

        //Insert all the routes generated
        for (Route route: routeList) {
            repository.insert(route);
        }

        //Get the routes from DB
        List<Route> routeListFromDB = repository.findByIdRouteGroup(1);
        RouteDTO routeBO = RouteBuilder.transformData(routeListFromDB, 1);
        String[] towns = RouteBuilder.getTownsArray(routeBO.getData());
        int distance = RouteBuilder.getGraphDistance(towns, routeListFromDB);

        assert distance == 23;
    }

    @Test
    public void shortestDistanceTest() throws ParseException {
        repository.truncateTable();
        List<Route> routeList = Utils.jsonToListRoutes(Constants.REQUEST_DATA_AVAILABLE_ROUTES, 1);

        //Insert all the routes generated
        for (Route route: routeList) {
            repository.insert(route);
        }

        //Get the routes from DB
        List<Route> routeListFromDB = repository.findByIdRouteGroup(1);

        RouteDTO routeBO = RouteBuilder.transformData(routeListFromDB, 1);
        String[] towns = RouteBuilder.getTownsArray(routeBO.getData());

        int distance = RouteBuilder.getGraphDistance(towns, routeListFromDB);

        //Get the possible routes
        String source = "A";
        String target = "C";
        List<String> allPossibleRoutes = RouteBuilder.getTargetsFromSource(source, target, routeListFromDB,
                new ArrayList<String>());

        log.info("allPossibleRoutes --> " + Arrays.toString(allPossibleRoutes.toArray()));
        int shortestDistance = 0;
        int distance = Integer.MAX_VALUE;
        String[] towns;
        String shortestRoute = "";
        StringBuilder twonRoutes = new StringBuilder();;
        int count = 0;

        for (String town: allPossibleRoutes) {
            twonRoutes.append(town);
            count++;

            if(town.equals(target)) {
                twonRoutes.insert(0, source);
                log.info("twonRoutes --> " + twonRoutes.toString());
                twonRoutes.delete(0, twonRoutes.length());
                count = 0;
            }
        }

        log.info("shortestDistance --> " + shortestDistance);
        log.info("shortestRoute --> " + shortestRoute);

        assert 1 == 1;
    }*/

}
