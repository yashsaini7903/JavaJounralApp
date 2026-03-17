package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntries;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JounralEntryrepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


//we use service at place of @component for standard and readablity
//@Component
@Service
@Slf4j
public class JounralEntryService {
  //@Slf4j= private static final Logger log = LoggerFactory.getLogger(JounralEntryService.class);

    @Autowired
    private JounralEntryrepository jounralEntryrepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void SaveEntry(JournalEntries journalEntry, String userName){
        try{
            User user = userService.FindUserByName(userName);
            journalEntry.setDate(LocalDateTime.now());
           JournalEntries entry = jounralEntryrepository.save(journalEntry);
           user.getJournalEntriesList().add(entry);
           userService.SaveNewUser(user);
        } catch (Exception e){
            log.error("Error occured for",e);
//            log.warn("");
//            log.info("Exception",e);
            throw new RuntimeException("An error occured during saving the entry");
        }
    }

    public void SaveEntry(JournalEntries journalEntry){
        try{

            journalEntry.setDate(LocalDateTime.now());
            JournalEntries entry = jounralEntryrepository.save(journalEntry);

        } catch (Exception e){
            log.error("Exception",e);
        }
    }

    public List<JournalEntries> getAll(){

        return jounralEntryrepository.findAll();
    }

    public Optional<JournalEntries> getById(String id) {
        return jounralEntryrepository.findById(id);
    }

    public Optional<JournalEntries> getByIdUs(String id) {
        return jounralEntryrepository.findById(id);
    }

    public boolean DeleteById(String id,String userName){
        boolean removed = false;
        try{
            User user = userService.FindUserByName(userName);
             removed = user.getJournalEntriesList().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.SaveUser(user);
                jounralEntryrepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return removed;
    }
}
