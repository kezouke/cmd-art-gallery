package db_objects;

import instruments.work_with_text.FormatTextInWindow;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements iShortInfo {
    private final String border = "_________________________________\n";
    public final String username;
    private String password;
    public UserRole role;

    public String linkOnProfilePicture = "No photo yet";
    public String bio = "No bio yet";

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username) {
        this.username = username;
    }

    public String generateHash() {
        String input = username + ":" + password;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hashBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                hashBuilder.append(String.format("%02x", b));
            }
            return hashBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String shortInfo() {
        return border +
                "username: " + username + "\n" +
                border +
                "role: " + roleToString() + "\n" +
                border;
    }

    public String detailedInfo() {
        return shortInfo() +
                "Profile Picture\n\t" + linkOnProfilePicture + "\n" +
                border +
                "Bio:\n\t" +
                new FormatTextInWindow().format(bio, 20) +
                "\n" + border;
    }

    private String roleToString() {
        return switch (role) {
            case ADMIN -> "admin";
            case SIGNED -> "signed";
            case UNSIGNED -> "unsigned";
        };
    }

    public URL receiveProfilePictureURL() throws MalformedURLException {
        return new URL(linkOnProfilePicture);
    }
}
