package com.avenuecode;

import com.avenuecode.common.Constants;
import com.avenuecode.domain.Resource;
import com.avenuecode.domain.Route;
import com.avenuecode.repository.RouteRepository;
import com.avenuecode.rest.ReplyRoute;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RouteReplyTest {
    @Autowired
    private RouteRepository repository;

    @Test
    public void getReplyAfterInsertTest() {
        ReplyRoute replyRoute = new ReplyRoute(repository);
        Resource resource = replyRoute.saveGraph(Constants.REQUEST_DATA);

        assert resource.getData().get(0).getSource().equals("A");
        assert resource.getData().get(0).getTarget().equals("B");
        assert resource.getData().get(0).getDistance() == 6;
    }

    @Test
    public void getAGraphByIdTest() {
        ReplyRoute replyRoute = new ReplyRoute(repository);
        replyRoute.saveGraph(Constants.REQUEST_DATA);
        Resource resourceToShow = replyRoute.saveGraph(Constants.REQUEST_DATA);

        assert resourceToShow.getId() == 2;
        assert resourceToShow.getData().get(0).getSource().equals("A");
        assert resourceToShow.getData().get(0).getTarget().equals("B");
        assert resourceToShow.getData().get(0).getDistance() == 6;
    }

    @Test
    public void validateIdMaxRouteGroupGenerated() {
        int idMaxRouteToBeGenerated = 3;
        ReplyRoute replyRoute = new ReplyRoute(repository);
        replyRoute.saveGraph(Constants.REQUEST_DATA);
        replyRoute.saveGraph(Constants.REQUEST_DATA);
        Resource resource = replyRoute.saveGraph(Constants.REQUEST_DATA);

        assert resource.getId() == idMaxRouteToBeGenerated;
    }

    @Test
    public void getMaxIdRouteGroupWhenTableIsEmptyTest() {
        ReplyRoute replyRoute = new ReplyRoute(repository);
        int maxId = replyRoute.getLastIdRouteGroup();

        assert maxId == 1;
    }

    @Test
    public void getMaxIdRouteGroupWhenTableHasInformation() {
        ReplyRoute replyRoute = new ReplyRoute(repository);
        Resource resourceToSave = replyRoute.saveGraph(Constants.REQUEST_DATA);
        int maxId = replyRoute.getLastIdRouteGroup();

        assert maxId == 2;
    }

}
