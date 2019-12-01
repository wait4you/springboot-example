package com.iyou.study.mongo.dao;

import com.iyou.study.mongo.document.UserDoc;
import org.springframework.stereotype.Repository;

/**
 * @author zhang.hp
 * @date 11/30 0030.
 */
@Repository
public class UserMongoDao extends MongoBaseDao<UserDoc> {
    @Override
    protected Class<UserDoc> getEntityClass() {
        return UserDoc.class;
    }
}
