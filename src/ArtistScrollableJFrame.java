import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ArtistScrollableJFrame {


    public static void createAndShowMainFrame(List<String> var, JPanel mainFrame, String TOKEN) {
        mainFrame.removeAll();
        mainFrame.setLayout(new GridLayout(0, 1));

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(var.size(), 1));

        for (int i = 0; i < var.size(); i++) {
            JPanel panel = createPanelWithNumber(var.get(i), TOKEN);
            containerPanel.add(panel);
        }

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());

        mainFrame.add(scrollPane);
    }

    private static JPanel createPanelWithNumber(String var, String token) {
        List<String> plyta = SpotifyAlbumInfo.getAlbumInfo(var,token);
        Font fontxs = new Font(Font.SANS_SERIF, Font.BOLD, 11);
        Font fonts = new Font(Font.SANS_SERIF, Font.BOLD, 14);
        Font fontm = new Font(Font.SANS_SERIF, Font.BOLD, 19);
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setLayout(null);

        int panelWidth = 490;
        int panelHeight = 50;

        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        JLabel label0 = new JLabel();
        label0.setBounds(5, 5, 40, 40);
        panel.add(label0);
        try {
            BufferedImage originalImage = ImageIO.read(new URL(plyta.get(1)));
            Image scaledImage = originalImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);

            ImageIcon imageIcon = new ImageIcon(scaledImage);
            label0.setIcon(imageIcon);
        } catch (IOException f) {
            f.printStackTrace();
        }

        JLabel label = new JLabel(plyta.get(3));
        label.setForeground(Color.WHITE);
        label.setFont(fontxs);
        label.setBounds(60, 15, 300, 20);
        panel.add(label);

        JLabel popularnosc = new JLabel("<html> premiera: <span style='color: rgb(0, 157, 59); display: inline;'>"+ plyta.get(4)+"</span>"+"            " +
                "       posłuch: <span style='color: rgb(0, 157, 59); display: inline;'>"+ plyta.get(2) +"</span>       ilość utworów: <span style='color: rgb(0, 157, 59); display: inline;'>"+ plyta.get(5) +"</span></html>");
        popularnosc.setForeground(new Color(72, 72, 72));
        popularnosc.setFont(fontxs);
        popularnosc.setBounds(170, 30, 300, 20);
        panel.add(popularnosc);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
        return panel;
    }

    public static class CustomScrollBarUI extends BasicScrollBarUI {

        @Override
        protected void configureScrollBarColors() {
            this.trackColor = new Color(31, 31, 31); // Kolor tła paska przewijania
            this.thumbColor = new Color(29, 185, 84); // Kolor kciuka paska przewijania
            this.thumbHighlightColor = new Color(0, 157, 59); // Kolor cienia dolnego kciuka
            this.thumbDarkShadowColor = new Color(0, 157, 59); // Kolor podświetlenia kciuka
            this.thumbLightShadowColor = new Color(29, 185, 84); // Kolor górnego cienia kciuka
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            Dimension zeroDim = new Dimension(0, 0);
            button.setPreferredSize(zeroDim);
            button.setMinimumSize(zeroDim);
            button.setMaximumSize(zeroDim);
            return button;
        }
    }
}