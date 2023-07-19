package main;

import db_connectors.firebase.FirestoreConnection;
import windows.InitialWindow;
import windows.auth.AuthWindow;

import java.util.Scanner;

public class ApplicationStart {
    public static void main(String[] args) {
        new InitialWindow().execute();
    }
}