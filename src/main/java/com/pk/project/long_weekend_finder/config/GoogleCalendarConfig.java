package com.pk.project.long_weekend_finder.config;

// import com.google.api.client.auth.oauth2.Credential;
// import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
// import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
// import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
// import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
// import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
// import com.google.api.client.http.javanet.NetHttpTransport;
// import com.google.api.client.json.JsonFactory;
// import com.google.api.client.json.gson.GsonFactory;
// import com.google.api.client.util.store.FileDataStoreFactory;
// import com.google.api.services.calendar.Calendar;
// import com.google.api.services.calendar.CalendarScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

// @Configuration
// public class GoogleCalendarConfig {

//     @Value("${google.calendar.oauth.client-id}")
//     private String clientId;

//     @Value("${google.calendar.oauth.client-secret}")
//     private String clientSecret;

//     @Value("${google.calendar.oauth.port:8888}")
//     private int port;

//     @Bean
//     public Calendar calendarService() throws IOException {
//         final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//         final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

//         GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
//                 new InputStreamReader(new FileInputStream("client_secret.json")));

//         GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                 HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, Collections.singleton(CalendarScopes.CALENDAR_READONLY))
//                 .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
//                 .setAccessType("offline")
//                 .build();

//         LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(port).build();
//         Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

//         return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
//                 .setApplicationName("Long Weekend Finder")
//                 .build();
//     }
// } 