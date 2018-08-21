package com.avenuecode;

import com.avenuecode.common.Constants;
import com.avenuecode.common.Utils;
import com.avenuecode.domain.Route;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class RouteTest {

    @Test
    public void insertTownAffectedRowsTest() throws ParseException {
        List<Route> listRoutes = Utils.jsonToListRoutes(Constants.REQUEST_DATA, 1);

        assert listRoutes.size() == 11;
    }
}
