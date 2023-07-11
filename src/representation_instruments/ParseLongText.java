package representation_instruments;

import java.util.Scanner;

public class ParseLongText {
    private final Scanner scanner;

    public ParseLongText(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getText() {
        StringBuilder text = new StringBuilder();
        while (scanner.hasNext()) {
            String word = scanner.next();
            if (word.equals("<*>")) {
                break;
            }
            text.append(word).append(" ");
        }
        return text.toString();
    }
}
