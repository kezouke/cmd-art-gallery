package db_connectors;

import com.google.cloud.firestore.Firestore;

public class FirestoreUpdateData {
    private final Firestore database;
    public PicturesConnect picturesConnect;
    public AuthorsConnect authorsConnect;

    public FirestoreUpdateData(Firestore database) {
        this.database = database;
        this.authorsConnect = new AuthorsConnect(database);
        this.picturesConnect = new PicturesConnect(database, authorsConnect);
    }

    public void updateData() {
        authorsConnect.refreshData(database);
        picturesConnect.refreshData(database);
    }
}
