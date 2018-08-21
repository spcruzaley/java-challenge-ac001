package com.avenuecode;

import com.avenuecode.domain.Town;
import com.avenuecode.repository.TownRepository;
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
public class TownTest {
    @Autowired
    private TownRepository repository;

    @Test
    public void existInformationTest() {
        List<Town> lista = repository.getAll();
        assert lista.size() > 0;
    }

    @Test
    public void insertTownAffectedRowsTest() {
        int rowAffected = repository.insert("A");
        assert rowAffected == 1;
    }

    @Test
    public void insertTownAndValidateRowInsertedTest() {
        String label = "B";
        repository.insert(label);
        Town town = repository.findByLabel(label);

        assert label.equals(town.getLabel());
    }

    //TODO Test for functionality
}
