package instruments.window_messages.update;

public class UpdateUserProfileLinkMessage {
    public void outputLink() {
        String message = """
                Enter a link to your new profile picture:
                """;
        System.out.print(message);
    }

    public void outputSuccess() {
        String message = """
                Success! Now you have new profile picture!
                """;
        System.out.print(message);
    }
}
