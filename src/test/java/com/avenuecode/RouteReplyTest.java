package com.avenuecode;

import com.avenuecode.common.Constants;
import com.avenuecode.repository.RouteBuilder;
import com.avenuecode.repository.RouteRepository;
import com.avenuecode.rest.RouteReply;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RouteReplyTest {
    @Autowired
    private RouteRepository repository;

    /*@Test
    public void validateAffectedRoutes() {
        RouteReply replyRoute = new RouteReply(repository);
        int affectedRows = replyRoute.insert(Constants.REQUEST_DATA);

        assert affectedRows == 1;
    }

    @Test
    public void getAGraphByIdTest() {
        RouteReply replyRoute = new RouteReply(repository);
        replyRoute.insert(Constants.REQUEST_DATA);
        replyRoute.insert(Constants.REQUEST_DATA);
        RouteBuilder resourceToShow = replyRoute.insert(Constants.REQUEST_DATA);

        assert resourceToShow.getId() == 2;
        assert resourceToShow.getData().get(0).getSource().equals("A");
        assert resourceToShow.getData().get(0).getTarget().equals("B");
        assert resourceToShow.getData().get(0).getDistance() == 6;
    }

    @Test
    public void validateIdMaxRouteGroupGenerated() {
        int idMaxRouteToBeGenerated = 3;
        RouteReply replyRoute = new RouteReply(repository);
        replyRoute.insert(Constants.REQUEST_DATA);
        replyRoute.insert(Constants.REQUEST_DATA);
        RouteBuilder resource = replyRoute.insert(Constants.REQUEST_DATA);

        assert resource.getId() == idMaxRouteToBeGenerated;
    }

    @Test
    public void getMaxIdRouteGroupWhenTableIsEmptyTest() {
        RouteReply replyRoute = new RouteReply(repository);
        int maxId = replyRoute.getLastIdRouteGroup();

        assert maxId == 1;
    }

    @Test
    public void getMaxIdRouteGroupWhenTableHasInformation() {
        RouteReply replyRoute = new RouteReply(repository);
        RouteBuilder resourceToSave = replyRoute.insert(Constants.REQUEST_DATA);
        int maxId = replyRoute.getLastIdRouteGroup();

        assert maxId == 2;
    }*/

}
