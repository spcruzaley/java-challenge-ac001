package com.avenuecode;

import com.avenuecode.domain.Route;
import com.avenuecode.repository.RouteRepository;
import org.h2.jdbc.JdbcSQLException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
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
        repository.createTable();
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
        repository.createTable();
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

    @Test
    public void getLastIdRouteGroupTest() {
        repository.createTable();
        repository.truncateTable();
        int idRouteGroup = 1;

        Route routeOne = new Route("A", "B", 7);
        repository.insert(routeOne, idRouteGroup);
        Route routeTwo = new Route("B", "C", 4);
        repository.insert(routeTwo, idRouteGroup);
        Route routeTree = new Route("D", "A", 6);
        repository.insert(routeTree, idRouteGroup);
        log.info(routeOne.toString());

        int lastIdRoute = repository.getLastIdRouteGroup();

        assert lastIdRoute == idRouteGroup;
    }

    @Test
    public void getLastIdRouteGroupZeroTest() {
        repository.createTable();
        repository.truncateTable();
        int idRouteGroup = 0;
        int lastIdRoute = repository.getLastIdRouteGroup();

        assert lastIdRoute == idRouteGroup;
    }

}
