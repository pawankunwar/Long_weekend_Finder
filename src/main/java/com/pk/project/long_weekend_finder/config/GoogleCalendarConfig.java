package com.pk.project.long_weekend_finder.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Configuration
public class GoogleCalendarConfig {

    @Value("classpath:credentials.json")
    private Resource credentialsResource;

    @Value("${google.calendar.oauth.port:8888}")
    private int port;

    @Bean
    public Calendar googleCalendarConfigService() throws IOException, GeneralSecurityException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        // Load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                jsonFactory,
                new InputStreamReader(credentialsResource.getInputStream())
        );

        if (clientSecrets.getDetails().getClientId() == null ||
                clientSecrets.getDetails().getClientSecret() == null) {
            throw new IllegalStateException("Invalid credentials.json file");
        }

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport,
                jsonFactory,
                clientSecrets,
                Collections.singleton(CalendarScopes.CALENDAR_READONLY)
        )
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();

        Credential credential = getCredential(flow);
        return new Calendar.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("Long Weekend Finder")
                .build();
    }

    private static Credential getCredential(GoogleAuthorizationCodeFlow flow) throws IOException {
        AuthorizationCodeInstalledApp manualAuth = new AuthorizationCodeInstalledApp(
                flow,
                new VerificationCodeReceiver() {
                    @Override
                    public String getRedirectUri() throws IOException {
                        return "urn:ietf:wg:oauth:2.0:oob"; // special redirect for manual copy-paste flow
                    }

                    @Override
                    public String waitForCode() throws IOException {
                        System.out.println("Visit this URL in your browser and authorize:");
                        System.out.println(flow.newAuthorizationUrl().setRedirectUri(getRedirectUri()));
                        System.out.print("Enter the code you see after authorizing: ");
                        return new java.util.Scanner(System.in).nextLine();
                    }

                    @Override
                    public void stop() throws IOException { }

                    public void start() throws IOException { }
                }
        );

        Credential credential = manualAuth.authorize("user");
        return credential;
    }
}
