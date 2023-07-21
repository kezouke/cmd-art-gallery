package instruments.window_messages.show_windows;

public class ShowPictureByLinkMessage {
    public void outputFailedToLoad() {
        String message = "Failed to load the image.";
        System.out.println(message);
    }

    public void outputError(String exception) {
        String message = "Error while loading the image: ";
        System.out.println(message + exception);
    }

    public void outputOnClosing() {
        String message = """
                You closed the picture.
                Maybe you to leave some comments on it?
                """;
        System.out.print(message);
    }
}
