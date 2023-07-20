package windows.show_windows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.google.cloud.firestore.Firestore;
import db_connectors.firebase.FirestoreUpdateData;
import db_objects.Picture;
import db_objects.UserRole;
import representation_instruments.window_messages.show_windows.ShowPicturesWindowMessage;
import representation_instruments.work_with_firebase.ArtObjectIterator;
import windows.detailed_view_windows.DetailedPictureWindow;
import windows.add_windows.PictureAddWindow;
import windows.Window;

public class ShowPicturesWindow implements Window {
    private final int step = 3;
    private final FirestoreUpdateData firestoreUpdate;
    private ArtObjectIterator<Picture> pictures;
    private final Scanner scanner;
    private final Firestore database;
    private final ShowPicturesWindowMessage messageEngine;

    public ShowPicturesWindow(FirestoreUpdateData firestoreUpdate,
                              Scanner scanner,
                              Firestore database) {
        this.firestoreUpdate = firestoreUpdate;
        this.scanner = scanner;
        this.database = database;
        this.pictures = new ArtObjectIterator<>(
                initializeSortedPictures(),
                step
        );
        this.messageEngine = new ShowPicturesWindowMessage();
    }

    public ShowPicturesWindow(FirestoreUpdateData firestoreUpdate,
                              Scanner scanner,
                              Firestore database,
                              List<Picture> pictures) {
        this.firestoreUpdate = firestoreUpdate;
        this.scanner = scanner;
        this.database = database;
        pictures.sort(Comparator.comparingInt(picture -> picture.id));
        this.pictures = new ArtObjectIterator<>(
                pictures,
                step
        );
        this.messageEngine = new ShowPicturesWindowMessage();
    }

    @Override
    public void execute() {
        boolean running = true;
        pictures = pictures.next();
        while (running) {
            try {
                messageEngine.outputMenu(
                        pictures.hasNext(),
                        pictures.hasPrev(),
                        firestoreUpdate.currentUser.role
                );

                String input = scanner.next();
                if (input.equals("return")) {
                    running = false;
                } else if (input.equals("next")) {
                    outputNextPics();
                } else if (input.equals("back")) {
                    outputPrevPics();
                } else if (input.equals("add")) {
                    addNewPicture();
                } else if (hasSuchPicture(input)) {
                    showDetailedPicture(input);
                } else {
                    messageEngine.outputWrongCommandEntered();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void addNewPicture() throws IOException {
        if (firestoreUpdate.currentUser.role != UserRole.UNSIGNED) {
            new PictureAddWindow(
                    scanner,
                    database,
                    firestoreUpdate
            ).execute();
            updatePicturesData();
            pictures.showArtObjects();
        } else {
            messageEngine.outputLowPermissionMessage();
        }
    }


    private void showDetailedPicture(String input) {
        new DetailedPictureWindow(
                firestoreUpdate,
                database,
                firestoreUpdate
                        .picturesConnect
                        .receivePicture()
                        .get(input),
                scanner).execute();
        updatePicturesDataFromStart(
        );
        this.pictures = pictures.next();
    }

    private void outputPrevPics() throws IOException {
        if (pictures.hasPrev()) {
            pictures = pictures.back();
        } else {
            messageEngine.outputNoPreviousPictures();
            pictures.showArtObjects();
        }
    }

    private void outputNextPics() throws IOException {
        if (pictures.hasNext()) {
            pictures = pictures.next();
        } else {
            messageEngine.outputNoNextPictures();
            pictures.showArtObjects();
        }
    }

    private boolean hasSuchPicture(String picture) {
        return firestoreUpdate
                .picturesConnect
                .receivePicture()
                .containsKey(picture);
    }

    private void updatePicturesData() {
        this.firestoreUpdate.updateData();
        this.pictures = new ArtObjectIterator<>(
                initializeSortedPictures(),
                pictures.currentStart,
                step
        );
    }

    private void updatePicturesDataFromStart() {
        this.firestoreUpdate.updateData();
        this.pictures = new ArtObjectIterator<>(
                initializeSortedPictures(),
                0,
                step
        );
    }

    private List<Picture> initializeSortedPictures() {
        List<Picture> sortedPictures = new ArrayList<>(
                firestoreUpdate.picturesConnect.receivePicture().values()
        );
        sortedPictures.sort(Comparator
                .comparingInt(picture -> picture.id));
        return sortedPictures;
    }

}

