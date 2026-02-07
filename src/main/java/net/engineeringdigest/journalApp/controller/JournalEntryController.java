package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntries;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JounralEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jounral")
class JournalEntryController {
    private Map<Long, JournalEntries> journalEntries = new HashedMap();

    @Autowired
    private JounralEntryService jounralEntryService;
    @Autowired
    private UserService userService;

    @GetMapping
    public  ResponseEntity<?> getAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.FindUserByName(userName);
        List<JournalEntries> all = user.getJournalEntriesList();
        if(all!=null&& !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntries> createEntry(@RequestBody JournalEntries myEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            jounralEntryService.SaveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//    @GetMapping("/{userName}")
//    public ResponseEntity<?> getAllEnryOfUser(@PathVariable String userName){
//       User user =  userService.FindUserByName(userName);
//       List<JournalEntries> all = user.getJournalEntriesList();
//       if(all!=null && !all.isEmpty()){
//           return new ResponseEntity<>(all,HttpStatus.OK);
//       }
//       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntries> getJounralEntry(@PathVariable String myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.FindUserByName(userName);
        List<JournalEntries> collect = user.getJournalEntriesList().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){

            Optional<JournalEntries> jounralEntry = jounralEntryService.getById(myId);
            if(jounralEntry.isPresent()){
                return new ResponseEntity<>(jounralEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> DeleteEntryById(@PathVariable String myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed =  jounralEntryService.DeleteById(myId,userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJounralById(@PathVariable String id,@RequestBody JournalEntries newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.FindUserByName(userName);
        List<JournalEntries> collect = user.getJournalEntriesList().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()) {

            Optional<JournalEntries> jounralEntry = jounralEntryService.getById(id);
            if (jounralEntry.isPresent()) {
                JournalEntries old = jounralEntry.get();
                old.setTitle(
                        (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) ? newEntry.getTitle() : old.getTitle()
                );

                old.setContent(
                        (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) ? newEntry.getContent() : old.getContent()
                );
                jounralEntryService.SaveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
