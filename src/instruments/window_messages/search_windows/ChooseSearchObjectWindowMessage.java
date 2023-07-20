package instruments.window_messages.search_windows;

import instruments.window_messages.WrongCommandMessage;

public class ChooseSearchObjectWindowMessage {
    public void outputVariantsToChose() {
        String optionsMessage = """
                if you want to search concrete pictures:
                    enter 'pictures'
                if you want to search concrete authors:
                    enter 'authors'
                if you want to return back to main menu:
                    enter 'return'
                """;
        System.out.println(optionsMessage);
    }

    public void outputWrongCommandEnteredMessage() {
        new WrongCommandMessage().outputMessage();
    }
}
