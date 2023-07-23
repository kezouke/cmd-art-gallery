package instruments.window_messages.saved;

import instruments.window_messages.WrongCommandMessage;

public class ChooseSavedObjectMessage {
    public void outputVariantsToChose() {
        String optionsMessage = """
                if you want to see saved pictures:
                    enter 'pictures'
                if you want to see saved authors:
                    enter 'authors'
                if you want to return back to main menu:
                    enter 'return'
                """;
        System.out.println(optionsMessage);
    }

    public void outputWrongCommandEnteredMessage() {
        new WrongCommandMessage().outputMessage();
    }

    public void outputEmptySavedPictures() {
        String message = """
                You didn't save any pictures yet
                """;
        System.out.print(message);
    }

    public void outputEmptySavedAuthors() {
        String message = """
                You didn't save any authors yet
                """;
        System.out.print(message);
    }
}
