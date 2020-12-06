package com.cvindexer.cvindexer;

import com.cvindexer.cvindexer.models.cv;
import com.cvindexer.cvindexer.repository.CVRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CVRepositoryTest {
    @ClassRule
    RestHighLevelClient highLevelClient;

    @Autowired
    CVRepository repository;

    @Test
    public void testAdd() {
        cv c = new cv();
        c.setFilename("test.docx");
        c.setContent("je suis un test");
        c = repository.save(c);
        Assert.assertNotNull(c);
    }
}
