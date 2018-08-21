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

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RouteTest {
    @Autowired
    private RouteRepository repository;

    @Test
    public void insertTownAffectedRowsTest() {
        Route route = new Route(1, "A", "B", 5);
        int rowAffected = repository.insert(route);

        assert rowAffected == 1;
    }

    @Test
    public void insertRouteAndValidateRowInsertedTest() {
        int idRouteGroup = 2;
        Route routeOne = new Route(idRouteGroup, "A", "B", 7);
        repository.insert(routeOne);
        Route routeTwo = new Route(idRouteGroup, "B", "C", 4);
        repository.insert(routeTwo);

        List<Route> routesFounded = repository.findByIdRouteGroup(idRouteGroup);

        assert routesFounded.size() == 2;
    }

    //TODO Test for functionality
}
