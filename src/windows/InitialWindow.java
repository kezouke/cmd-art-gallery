package windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreConnection;
import db_connectors.firebase.FirestoreUpdateData;
import windows.auth.AuthWindow;

import java.util.Scanner;

public class InitialWindow implements Window {
    private final Firestore database;
    private final Scanner scanner;

    public InitialWindow() {
        database = new FirestoreConnection().database;
        scanner = new Scanner(System.in);
    }

    public InitialWindow(Firestore database, Scanner scanner) {
        this.database = database;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
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
