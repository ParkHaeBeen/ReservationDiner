package com.zerobase.reservationdiner.fcmn.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.zerobase.reservationdiner.fcmn.dto.FcmMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class FcmService {

    private final StringRedisTemplate redisTemplate;
    private static final long TOKEN_EXPIRATION = 3500;

    @Value("${firebase.key-path}")
    String fcmKeyPath;
    public String getAccessToken(Long storeId) throws IOException {


        GoogleCredentials   googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(fcmKeyPath).getInputStream())
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        String newToken = googleCredentials.getAccessToken().getTokenValue();

        // 새로 생성된 토큰을 Redis에 저장
        redisTemplate.opsForValue().set(String.valueOf(storeId),newToken,TOKEN_EXPIRATION,TimeUnit.SECONDS);
        return newToken;
    }

    public Message makeMessage(FcmMessage.Request request) throws JsonProcessingException {
        Message message=Message.builder()
                .putData("reservationTime", String.valueOf(request.getReserveTime()))
                .putData("name",request.getUserName())
                .setToken(request.getTargetToken())
                .build();
        log.info("message ={}",message);
        return message;
    }

    public String sendMessage(FcmMessage.Request request) throws JsonProcessingException, FirebaseMessagingException {
        String send = FirebaseMessaging.getInstance().send(makeMessage(request));
        log.info("fcm message ={}",send);
        return send;
    }

}
