package com.iyou.study.mongo;

import com.iyou.study.mongo.document.UserDoc;
import com.iyou.study.mongo.service.UserMongoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhang.hp
 * @date 11/30 0030.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTest {

    @Autowired
    private UserMongoService userMongoService;

    @Test
    public void testSave(){
        userMongoService.saveObj2(new UserDoc());
    }
}
