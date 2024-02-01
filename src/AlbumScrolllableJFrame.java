import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class AlbumScrollableJFrame {

    static Integer il = 1;

    public static void createAndShowMainFrame(List<String> var, JPanel mainFrame) {
        mainFrame.removeAll();
        il = 1;
        mainFrame.setLayout(new GridLayout(0, 1));

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(var.size(), 1));

        for (int i = 0; i < var.size(); i++) {
            JPanel panel = createPanelWithNumber(var.get(i));
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

    private static JPanel createPanelWithNumber(String var) {
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

        JLabel label0 = new JLabel(Integer.toString(il));
        label0.setForeground(new Color(72, 72, 72));
        label0.setFont(font);
        label0.setBounds(5, 15, 30, 20);
        panel.add(label0);

        JLabel label = new JLabel(var);
        label.setForeground(Color.WHITE);
        label.setFont(fontxs);
        label.setBounds(40, 15, 300, 20);
        panel.add(label);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Obsługa kliknięcia
            }
        });
        il = 1 + il;
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