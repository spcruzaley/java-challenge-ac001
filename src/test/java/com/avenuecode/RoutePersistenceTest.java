package com.avenuecode;

import com.avenuecode.common.Constants;
import com.avenuecode.common.Utils;
import com.avenuecode.domain.Route;
import com.avenuecode.repository.RouteBO;
import com.avenuecode.repository.RouteBuilder;
import com.avenuecode.repository.RouteRepository;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
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
    public void insertTownAffectedRowsTest() {
        repository.truncateTable();
        int rowAffected = 0;

        Route routeOne = new Route(1, "A", "B", 5);
        rowAffected = repository.insert(routeOne);
        Route routeTwo = new Route(1, "A", "B", 5);
        rowAffected += repository.insert(routeTwo);

        assert rowAffected == 2;
    }

    @Test
    public void insertRouteAndValidateRowInsertedAndDataTest() {
        repository.truncateTable();
        int idRouteGroup = 1;

        Route routeOne = new Route(idRouteGroup, "A", "B", 7);
        repository.insert(routeOne);
        Route routeTwo = new Route(idRouteGroup, "B", "C", 4);
        repository.insert(routeTwo);
        Route routeTree = new Route(idRouteGroup, "D", "A", 6);
        repository.insert(routeTree);

        List<Route> routesFounded = repository.findByIdRouteGroup(idRouteGroup);

        assert routesFounded.size() == 3;
        assert routesFounded.get(1).getSource().equals("B");
        assert routesFounded.get(1).getTarget().equals("C");
    }

    @Test
    public void availableRoutesHardCodeTest() throws ParseException {
        List<Route> routeList = Utils.jsonToListRoutes(Constants.REQUEST_DATA_AVAILABLE_ROUTES, 1);
        List<String> allPossibleRoutes = RouteBuilder.getTargetsFromSource("A", "C", routeList,
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

}
