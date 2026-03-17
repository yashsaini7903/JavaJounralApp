package net.engineeringdigest.journalApp.cache;

import net.engineeringdigest.journalApp.entity.ConfigJounralEntities;
import net.engineeringdigest.journalApp.repository.ConfigJounralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    @Autowired
    private ConfigJounralRepository configJounralRepositry;

    public Map<String,String> App_Cache;

    @PostConstruct
    public void init(){
        App_Cache = new HashMap<>();
        List<ConfigJounralEntities> all = configJounralRepositry.findAll();
        for (ConfigJounralEntities configJournalAppEntity : all) {
            App_Cache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }

}
