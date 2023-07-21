package db_connectors.firebase;

import com.google.cloud.firestore.Firestore;
import db_connectors.connect.AuthorsConnect;
import db_connectors.connect.CommentsConnect;
import db_connectors.connect.PicturesConnect;
import db_connectors.connect.UsersConnect;
import db_objects.User;

public class FirestoreUpdateData {
    private final Firestore database;
    public PicturesConnect picturesConnect;
    public AuthorsConnect authorsConnect;
    public CommentsConnect commentsConnect;
    public User currentUser;

    public FirestoreUpdateData(Firestore database, User currentUser) {
        this.database = database;
        this.authorsConnect = new AuthorsConnect(database);
        this.commentsConnect = new CommentsConnect(database);
        this.picturesConnect = new PicturesConnect(database,
                authorsConnect,
                commentsConnect);
        this.currentUser = currentUser;
    }

    public void updateData() {
        authorsConnect.refreshData(database);
        picturesConnect.refreshData(database);
        commentsConnect.refreshData(database);
        currentUser = new UsersConnect(
                database
        ).receiveUserByUsername(currentUser.username);
    }
}
