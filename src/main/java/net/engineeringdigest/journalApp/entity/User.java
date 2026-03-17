package net.engineeringdigest.journalApp.entity;

import javax.validation.constraints.NotNull;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
@Data
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NotNull
    private String username;
    @NotNull
    private String password;
    @DBRef
    private List<JournalEntries> journalEntriesList = new ArrayList<>();
    private List<String> roles;
    private String email;
}
