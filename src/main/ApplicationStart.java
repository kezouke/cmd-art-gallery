package main;

import db_connectors.FirestoreConnection;
import db_connectors.PicturesConnect;
import windows.MainPicturesWindow;

import java.util.Scanner;

public class ApplicationStart {
    public static void main(String[] args) {
        new MainPicturesWindow(
                new PicturesConnect(new FirestoreConnection().db).receivePicture(),
                new Scanner(System.in)
        ).execute();
    }
}