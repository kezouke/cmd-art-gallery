package instruments.window_messages.update;

public class UpdateUserBioMessage {
    public void outputBio() {
        String message = """
                Enter a bio:
                Note: You can enter any text with any size.
                      However, enter '<*>' when you will finish.
                      So, I will understand that you entered your country.
                      Example:
                            COOP enjoyer <*>
                """;
        System.out.print(message);
    }

    public void outputSuccess() {
        String message = """
                Success! Now you have new bio!
                """;
        System.out.print(message);
    }
}
