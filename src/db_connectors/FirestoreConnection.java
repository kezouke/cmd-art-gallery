package db_connectors;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class FirestoreConnection {
    public final Firestore database;
    public FirestoreConnection () {
        try {
            FileInputStream serviceAccount = new FileInputStream(
                    System.getProperty("user.dir") + "\\cmd-art-gallery-firebase.json"
            );
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            database = FirestoreClient.getFirestore();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
