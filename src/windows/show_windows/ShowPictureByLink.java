package windows.show_windows;

import db_objects.Picture;
import instruments.window_messages.show_windows.ShowPictureByLinkMessage;
import windows.Window;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class ShowPictureByLink implements Window {
    private final CountDownLatch latch;
    private final URL link;
    private final ShowPictureByLinkMessage messageEngine;
    private final boolean isProfileLink;

    public ShowPictureByLink(URL link, boolean isProfileLink) {
        this.link = link;
        this.messageEngine = new ShowPictureByLinkMessage();
        this.latch = new CountDownLatch(1);
        this.isProfileLink = isProfileLink;
    }

    private void displayImage(BufferedImage image) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon(image);
        JLabel label = new JLabel(icon);
        frame.add(label, BorderLayout.CENTER);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame
                .DISPOSE_ON_CLOSE);

        frame.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        messageEngine.outputOnClosing();
                        latch.countDown();
                    }
                }
        );
        frame.setVisible(true);
    }

    @Override
    public void execute() {
        try {
            BufferedImage image = ImageIO.read(link);

            if (image != null) {
                displayImage(image);
                latch.await();
            } else {
                if (!isProfileLink) {
                    messageEngine.outputFailedToLoad();
                }
            }
        } catch (IOException e) {
            messageEngine.outputError(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
