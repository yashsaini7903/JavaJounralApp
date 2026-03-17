package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_jounral_app")
@Data
public class ConfigJounralEntities {
    private String key;
    private String value;
}
