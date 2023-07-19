package windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreConnection;
import db_connectors.firebase.FirestoreUpdateData;
import windows.auth.AuthWindow;

import java.util.Scanner;

public class InitialWindow implements Window {
    @Override
    public void execute() {
        Firestore database = new FirestoreConnection().database;
        Scanner scanner = new Scanner(System.in);

        AuthWindow authWindow = new AuthWindow(
                scanner,
                database
        );
        authWindow.execute();
        new MenuWindow(
                scanner,
                new FirestoreUpdateData(
                        database,
                        authWindow.currentUser
                ),
                database
        ).execute();
    }
}
