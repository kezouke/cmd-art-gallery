package instruments.window_messages;

public class MenuWindowMessage {
    private final String greetingMessage;
    private final String logoutSuccessMessage;
    private final WrongCommandMessage wrongCommandMessage;

    public MenuWindowMessage() {
        this.greetingMessage = """
                _____________________________________________________
                ////////////////////// MAIN MENU \\\\\\\\\\\\\\\\\\\\
                _____________________________________________________
                1) If you want to take a look at all pictures:
                    enter 'pictures'
                _____________________________________________________
                2) If you want to take a look at all authors:
                    enter 'authors'
                _____________________________________________________
                3) If you want to search concrete pictures/authors:
                    enter 'search'
                _____________________________________________________
                4) If you want to change your current account:
                    enter 'change'
                _____________________________________________________
                5) If you want to close application:
                    enter 'close'
                _____________________________________________________
                """;
        this.logoutSuccessMessage = """
                You have been successfully log out!
                """;
        this.wrongCommandMessage = new WrongCommandMessage();
    }

    public void outputGreeting() {
        System.out.println(greetingMessage);
    }

    public void outputWrongCommandEnteredMessage() {
        wrongCommandMessage.outputMessage();
    }

    public void outputSuccessLogout(){
        System.out.println(logoutSuccessMessage);
    }
}
