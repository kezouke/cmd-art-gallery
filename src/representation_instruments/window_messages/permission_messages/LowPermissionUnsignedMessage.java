package representation_instruments.window_messages.permission_messages;

public class LowPermissionUnsignedMessage {
    public void outputMessage() {
        String message = """
                As unsigned user you cannot do it :(
                """;
        System.out.println(message);
    }
}
