package com.iyou.study.mongo.service;

import com.iyou.study.mongo.dao.UserMongoDao;
import com.iyou.study.mongo.document.UserDoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhang.hp
 * @date 11/30 0030.
 */
@Service
public class UserMongoService {
    private static final Logger logger = LoggerFactory.getLogger(UserMongoService.class);

    //注入新的CURD操作类
    @Autowired
    private UserMongoDao userMongoDao;

    /**
     * 保存对象
     * @param user
     * @return
     */
    public String saveObj2(UserDoc user) {
        user = new UserDoc("11","coffee",55);
        //调用bookMongoDbDao父类中的添加方法
        userMongoDao.save(user);
        return "添加成功";
    }
}
