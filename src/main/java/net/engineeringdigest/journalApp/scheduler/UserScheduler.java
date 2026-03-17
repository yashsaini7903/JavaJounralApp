package net.engineeringdigest.journalApp.scheduler;

import net.engineeringdigest.journalApp.entity.JournalEntries;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.model.SentimentData;
import net.engineeringdigest.journalApp.repository.UserRepositoryImp;
import net.engineeringdigest.journalApp.service.EmailSevice;
import net.engineeringdigest.journalApp.service.SentimentAnlsisService;
import net.engineeringdigest.journalApp.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler{
     @Autowired
     private EmailSevice emailSevice;

     @Autowired
     private UserRepositoryImp userRepository;

     @Autowired
     private SentimentAnlsisService sentimentAnlsisService;

     @Autowired
     private KafkaTemplate<String, SentimentData> kafkaTemplate;

     @Scheduled(cron = "0 0 9 * * SUN")
     public void fetchUserAndSendMail(){
         List<User> users = userRepository.getUserForSa();
         for (User user : users) {
             List<JournalEntries> journalEntries = user.getJournalEntriesList();
             List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
             Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
             for (Sentiment sentiment : sentiments) {
                 if (sentiment != null)
                     sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
             }
             Sentiment mostFrequentSentiment = null;
             int maxCount = 0;
             for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                 if (entry.getValue() > maxCount) {
                     maxCount = entry.getValue();
                     mostFrequentSentiment = entry.getKey();
                 }
             }
             if (mostFrequentSentiment != null) {
                 SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days " + mostFrequentSentiment).build();
                 try{
                     kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
                 }catch (Exception e){
                     emailSevice.sendMail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
                 }
             }
         }
     }
}
