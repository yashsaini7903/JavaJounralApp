package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.ConfigJounralEntities;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJounralRepository extends MongoRepository<ConfigJounralEntities, ObjectId> {
}
