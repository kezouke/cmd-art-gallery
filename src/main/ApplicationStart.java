package main;

import db_connectors.firebase.FirestoreConnection;
import windows.auth.AuthWindow;

import java.util.Scanner;

public class ApplicationStart {
    public static void main(String[] args) {
        new AuthWindow(
                new Scanner(System.in),
                new FirestoreConnection().database
        ).execute();
    }
}