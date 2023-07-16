package windows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.google.cloud.firestore.Firestore;
import db_connectors.FirestoreUpdateData;
import db_objects.Picture;
import representation_instruments.ArtObjectIterator;
import representation_instruments.OutputMessage;

public class ShowPicturesWindow implements Window {
    private final int step = 3;
    private final FirestoreUpdateData firestoreUpdate;
    private ArtObjectIterator<Picture> pictures;
    private final Scanner scanner;
    private final Firestore database;

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
    }

    @Override
    public void execute() {
        boolean running = true;
        OutputMessage picturesMessage =
                new OutputMessage("files/OutputForPicture");
        OutputMessage errorMessage =
                new OutputMessage("files/OutputForError");
        OutputMessage nextPics =
                new OutputMessage("files/OutputForNextPictures");
        OutputMessage prevPics =
                new OutputMessage("files/OutputForPrevPictures");

        pictures = pictures.next();
        while (running) {
            try {
                outputMenu(picturesMessage,
                        nextPics,
                        prevPics);

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
                    errorMessage.display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void addNewPicture() {
        new PictureAddWindow(
                scanner,
                database,
                firestoreUpdate
        ).execute();
        updatePicturesData();
        pictures.showArtObjects();
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
        pictures.showArtObjects();
    }

    private void outputMenu(OutputMessage picturesMessage,
                            OutputMessage nextPics,
                            OutputMessage prevPics) throws IOException {
        if (pictures.hasNext()) {
            nextPics.display();
        }
        if (pictures.hasPrev()) {
            prevPics.display();
        }
        picturesMessage.display();
    }

    private void outputPrevPics() throws IOException {
        if (pictures.hasPrev()) {
            pictures = pictures.back();
        } else {
            new OutputMessage("files/OutputForNoPrevPics")
                    .display();
            pictures.showArtObjects();
        }
    }

    private void outputNextPics() throws IOException {
        if (pictures.hasNext()) {
            pictures = pictures.next();
        } else {
            new OutputMessage("files/OutputForAllPicturesShowed")
                    .display();
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

    private List<Picture> initializeSortedPictures() {
        List<Picture> sortedPictures = new ArrayList<>(
                firestoreUpdate.picturesConnect.receivePicture().values()
        );
        sortedPictures.sort(Comparator
                .comparingInt(picture -> picture.id));
        return sortedPictures;
    }

}

