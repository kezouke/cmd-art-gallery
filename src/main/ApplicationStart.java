package main;

import windows.AuthWindow;

import java.util.Scanner;

public class ApplicationStart {
    public static void main(String[] args) {
        new AuthWindow(new Scanner(System.in)).execute();
    }
}