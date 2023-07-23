package instruments.window_messages.remove_windows;

import instruments.window_messages.WrongCommandMessage;

public class UnSavePictureWindowMessage {
    public void outputIsUserWantToSave() {
        String message = """
                You are going to unsave this picture.
                Are you sure? (yes/no)
                """;
        System.out.print(message);
    }

    public void outputWrongCommandEntered() {
        new WrongCommandMessage().outputMessage();
    }

    public void outputSuccess() {
        String message = """
                Removed!
                """;
        System.out.println(message);
    }
}
