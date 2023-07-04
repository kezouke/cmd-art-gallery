package representation_instruments;

public class FormatTextInWindow {
    public String format(String input, int step) {
        StringBuilder inputBuilder = new StringBuilder(input);

        int count = 0;
        boolean haveToSplit = false;
        for (int i = 0; i < inputBuilder.length(); i++) {
            count++;
            if (count % step == 0 | haveToSplit) {
                haveToSplit = true;
                if (inputBuilder.charAt(i) == ' ') {
                    inputBuilder.deleteCharAt(i);
                    inputBuilder.insert(i, "\n\t");
                    haveToSplit = false;
                }
            }
        }

        return inputBuilder.toString();
    }
}
