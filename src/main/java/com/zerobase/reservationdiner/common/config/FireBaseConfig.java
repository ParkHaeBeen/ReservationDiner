package com.zerobase.reservationdiner.common.config;

import org.springframework.beans.factory.annotation.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@Component
@Slf4j
public class FireBaseConfig {
    @Value("${firebase.key-path}")
    String fcmKeyPath;
    @Value("${firebase.project-id}")
    String projectId;
    @PostConstruct
    public void init(){
        try{

            FileInputStream serviceAccount =
                    new FileInputStream(fcmKeyPath);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId(projectId)
                    .build();
            FirebaseApp.initializeApp(options);
            log.info("Fcm Setting Completed");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
