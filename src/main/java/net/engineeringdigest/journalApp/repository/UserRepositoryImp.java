package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImp {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSa(){
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is("user2"));
        query.addCriteria(Criteria.where("email").isNull());
        List<User> users = mongoTemplate.find(query,User.class);
        return users;
    }
}
