package com.iyou.study.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zhang.hp
 * @date 11/30 0030.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class UserDoc {
    @Id
    private String id;
    private String name;
    private int age;
}
