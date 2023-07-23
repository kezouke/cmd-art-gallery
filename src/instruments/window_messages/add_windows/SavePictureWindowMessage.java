package instruments.window_messages.add_windows;

import instruments.window_messages.WrongCommandMessage;

public class SavePictureWindowMessage {
    public void outputIsUserWantToSave() {
        String message = """
                You are going to save this picture.
                Are you sure? (yes/no)
                """;
        System.out.print(message);
    }

    public void outputWrongCommandEntered() {
        new WrongCommandMessage().outputMessage();
    }

    public void outputSuccess() {
        String message = """
                Saved!
                """;
        System.out.println(message);
    }
}
