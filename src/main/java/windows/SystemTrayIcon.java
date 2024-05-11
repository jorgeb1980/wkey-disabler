package windows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public enum SystemTrayIcon {

    ICON;

    private TrayIcon icon = null;

    public void load() {
        if (SystemTray.isSupported()) {
            try (var is = SystemTrayIcon.class.getClassLoader().getResourceAsStream("—Pngtree—black iron lock icon_4438288.png")) {
                icon = new TrayIcon(
                    imageSize(
                        ImageIO.read(is),
                        BufferedImage.TYPE_INT_ARGB,
                        16,
                        16
                    ),
                    "WKey Locked - Click to unlock!"
                );
                //get the system tray
                final SystemTray tray = SystemTray.getSystemTray();
                tray.add(icon);

                // Listen to events
                icon.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        DisablerService.SERVICE.enableWKey();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
            } catch (Exception e) { e.printStackTrace(); }
        } else System.err.println("Can't load system tray icon");
    }

    private static BufferedImage imageSize(BufferedImage image, int type, int width, int height) {
        var resizedImage = new BufferedImage(width, height, type);
        var g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();

        return resizedImage;
    }
}
