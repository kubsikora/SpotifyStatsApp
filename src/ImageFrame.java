import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

class ImageFrame extends JFrame {

    public ImageFrame(String imageUrl, JPanel up) {


        try {
            BufferedImage originalImage = ImageIO.read(new URL(imageUrl));
            Image scaledImage = originalImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);

            ImageIcon imageIcon = new ImageIcon(scaledImage);
            JLabel label = new JLabel(imageIcon);

            up.add(label);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}