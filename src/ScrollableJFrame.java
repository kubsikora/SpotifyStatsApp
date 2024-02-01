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



public class ScrollableJFrame {

    static Integer il = 1;
    static List<String> art;
    static List<String> L = new ArrayList<>();
    static JPanel panel, Jlista, Jplyty;
    static JLabel nazwautw,nazwaart,opisnuty, Jpic, inne;
    static String TOKEN;
    public static void createAndShowMainFrame(List<String> var, JPanel mainFrame, String token, JPanel wynik, JLabel pic, JLabel piosenka, JLabel artysta, JLabel opis, JPanel red, JLabel punkty, JPanel Jstat) {
        mainFrame.removeAll();
        TOKEN = token;
        Jpic = pic;
        nazwautw = piosenka;
        nazwaart = artysta;
        opisnuty = opis;
        il = 1;
        mainFrame.setLayout(new GridLayout(0, 1));
        int numberOfPanels = var.size();

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(numberOfPanels, 1));

        for (int i = 0; i < var.size(); i++) {
            JPanel panel = createPanelWithNumber(var.get(i),wynik, red, punkty, Jstat);
            containerPanel.add(panel);
        }

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Wartość może być dostosowana

        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());


        mainFrame.add(scrollPane);
        mainFrame.setSize(270, 400);
        mainFrame.setVisible(true);
    }

    private static JPanel createPanelWithNumber(String var, JPanel wynik, JPanel red,JLabel punkty, JPanel Jstat) {
        Font fontxs = new Font(Font.SANS_SERIF, Font.BOLD,11);
        Font fonts = new Font(Font.SANS_SERIF, Font.BOLD,14);
        Font fontm = new Font(Font.SANS_SERIF, Font.BOLD,19);
        Font font = new Font(Font.SANS_SERIF, Font.BOLD,25);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(38, 38, 38));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setLayout(null);
        List<String> odp = splitText(var);

        int panelWidth = 268;
        int panelHeight = 80;

        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        if(odp.size()>=3) {

            JLabel label0 = new JLabel(Integer.toString(il));
            label0.setForeground(new Color(72, 72, 72));
            label0.setFont(font);
            label0.setBounds(5, 30, 30, 20);
            panel.add(label0);

            JLabel label = new JLabel(odp.get(0));
            label.setForeground(Color.WHITE);
            label.setFont(fontxs);
            label.setBounds(40, 20, 220, 20);
            panel.add(label);

            JLabel label2 = new JLabel(odp.get(1));
            label2.setForeground(new Color(0, 157, 59));
            label2.setFont(fonts);
            label2.setBounds(40, 40, 220, 20);
            panel.add(label2);


            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String wyszukaj = odp.get(2).substring(2,odp.get(2).length());
                    /*if(Jstat.isVisible()==true) {
                        Jstat.setVisible(false);
                    }*/
                    if(SpotifySongInfo.getSongInfo(wyszukaj, TOKEN).size()!=0){


                        inne = new JLabel("Inne podobne piosenki które mogą ci sie spodobać");
                        inne.setFont(fontxs);
                        inne.setVisible(true);
                        inne.setForeground(new Color(31, 31, 31));
                        inne.setBounds(105,302,300,20);
                        inne.setHorizontalAlignment(JLabel.CENTER);
                        inne.setVerticalAlignment(JLabel.CENTER);
                        wynik.add(inne);
                        if(Jlista != null) {
                            Jlista.setVisible(false);
                        }
                        if(Jplyty != null) {
                            Jplyty.setVisible(false);
                        }

                        punkty.setText("<html>Z albumu: <br>premiera: <br>długość:   <br>posłuch: </html>");
                        List<List<String>> recommendations = SpotifySimilarSongs.getSimilarSongs(wyszukaj,TOKEN);
                        red.setVisible(true);
                        podobne(red, wyszukaj);

                        wynik.setVisible(true);
                        List<String> L2 = new ArrayList<>(SpotifySongInfo.getSongInfo(wyszukaj, TOKEN));
                        nazwaart.setText(L2.get(1));
                        nazwautw.setText(L2.get(0));
                        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                            @Override
                            protected Void doInBackground() throws Exception {
                                try {
                                    BufferedImage originalImage = ImageIO.read(new URL(L2.get(L2.size() - 1)));
                                    Image scaledImage = originalImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);

                                    ImageIcon imageIcon = new ImageIcon(scaledImage);
                                    SwingUtilities.invokeLater(() -> Jpic.setIcon(imageIcon));
                                } catch (IOException f) {
                                    f.printStackTrace();
                                }
                                return null;
                            }
                        };
                        worker.execute();
                        opisnuty.setText("<html>"+ L2.get(3) +"<br>"+ L2.get(5)+"<br>"+ sektotime(L2.get(2)) +"<br>"+ L2.get(4) +"</html>");
                    }else if(SpotifyAlbumInfo.getAlbumInfo(wyszukaj,TOKEN).size()!=0){
                        List<String> L3 = SpotifyAlbumInfo.getAlbumInfo(wyszukaj,TOKEN);
                        //System.out.println(L3);
                        wynik.setVisible(true);
                        red.setVisible(false);
                        if(inne == null) {
                            inne = new JLabel("");
                        } else{
                            inne.setVisible(false);
                        }
                        inne.setText("");
                        if(Jplyty != null) {
                            Jplyty.setVisible(false);
                        }
                        wynik.add(inne);
                        if (Jlista == null) {
                            Jlista = new JPanel();
                            //Jlista.setBackground(new Color(0, 0, 0));
                            Jlista.setBounds(15, 270, 475, 200);
                            Jlista.setVisible(true);
                            wynik.add(Jlista);
                        } else {
                            Jlista.removeAll();
                            Jlista.setVisible(true);
                        }
                        List<String> listpiosenek = createSublist(L3,6, L3.size());
                        AlbumScrollableJFrame.createAndShowMainFrame(listpiosenek,Jlista);
                        nazwaart.setText(L3.get(0));
                        nazwautw.setText(L3.get(3));
                        punkty.setText("<html>premiera:<br>posłuch: </html>");
                        opisnuty.setText("<html>"+ L3.get(4)+"<br>"+ L3.get(2) +"</html>");
                        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                            @Override
                            protected Void doInBackground() throws Exception {
                                try {
                                    BufferedImage originalImage = ImageIO.read(new URL(L3.get(1)));
                                    Image scaledImage = originalImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);

                                    ImageIcon imageIcon = new ImageIcon(scaledImage);
                                    SwingUtilities.invokeLater(() -> Jpic.setIcon(imageIcon));
                                } catch (IOException f) {
                                    f.printStackTrace();
                                }
                                return null;
                            }
                        };
                        worker.execute();

                    }else{
                        System.out.println("ID szukanego albumu: " + wyszukaj);
                    }

                }
            });
            il = 1 + il;
            return panel;
        } else if (odp.size()==1 || odp.size()==2) {
            JLabel label0 = new JLabel(Integer.toString(il));
            label0.setForeground(new Color(72, 72, 72));
            label0.setFont(font);
            label0.setBounds(5, 30, 30, 20);
            panel.add(label0);

            JLabel label2 = new JLabel(odp.get(0));
            label2.setForeground(new Color(0, 157, 59));
            label2.setFont(fontm);
            label2.setBounds(40, 25, 220, 35);
            panel.add(label2);

            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        art = SpotifyArtistInfo.getArtistInfo(odp.get(1), TOKEN);
                        //List<String> plyta = SpotifyAlbumInfo.getAlbumInfo("7vYR7oLh93zb38m880M8bd", TOKEN);
                    }catch (IOException j){
                        art = new ArrayList<>();
                        System.out.println("Bład wczytywania informacji o artyscie ScrollableJFrame216");
                    }
                    wynik.setVisible(true);
                    nazwaart.setText("<html><h2>"+art.get(0)+"</h2></html>");
                    nazwautw.setText(art.get(4)+" obserwujących");
                    punkty.setText("<html>gatunek:<br>posłuch: </html>");
                    opisnuty.setText("<html>"+ art.get(1)+"<br>"+ art.get(3) +"</html>");
                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            try {
                                BufferedImage originalImage = ImageIO.read(new URL(art.get(2)));
                                Image scaledImage = originalImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);

                                ImageIcon imageIcon = new ImageIcon(scaledImage);
                                SwingUtilities.invokeLater(() -> Jpic.setIcon(imageIcon));
                            } catch (IOException f) {
                                f.printStackTrace();
                            }
                            return null;
                        }
                    };
                    worker.execute();
                    red.setVisible(false);
                    if(inne == null) {
                        inne = new JLabel("");
                    } else{
                        inne.setVisible(false);
                    }
                    inne.setText("");
                    if(Jlista != null) {
                        Jlista.setVisible(false);
                    }
                    wynik.add(inne);
                    if (Jplyty == null) {
                        Jplyty = new JPanel();
                        //Jlista.setBackground(new Color(0, 0, 0));
                        Jplyty.setBounds(15, 270, 475, 200);
                        Jplyty.setVisible(true);
                        wynik.add(Jplyty);
                    } else {
                        Jplyty.removeAll();
                        Jplyty.setVisible(true);
                    }
                    String[] id_plyt = art.get(5).split(",\\s*");
                    ArtistScrollableJFrame.createAndShowMainFrame(Arrays.asList(id_plyt),Jplyty,TOKEN);
                    //System.out.println(art.get(5));
                }
            });
            il = 1 + il;
            return panel;
        }
        else{
            System.out.println(odp.size()+"|"+odp );
            return panel;
        }
    }
    public static String sektotime(String inputMilliseconds) {
        try {
            long totalMilliseconds = Long.parseLong(inputMilliseconds);

            if (totalMilliseconds < 0) {
                throw new IllegalArgumentException("Input should be a non-negative integer.");
            }

            long totalSeconds = totalMilliseconds / 1000;
            long minutes = totalSeconds / 60;
            long seconds = totalSeconds % 60;

            return String.format("%d min %d sec", minutes, seconds);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Please provide a valid integer.");
        }
    }

    public static List<String> splitText(String inputText) {
        List<String> resultList = new ArrayList<>();

        // Dzielimy tekst na trzy części: przed "by", między "by" a "@", i po "@"
        String[] parts = inputText.split("by|@");

        // Usuwamy zbędne białe znaki z każdej części
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        // Dodajemy do listy
        for (String part : parts) {
            if (!part.isEmpty()) {
                resultList.add(part);
            }
        }

        return resultList;
    }
    public static class CustomScrollBarUI extends BasicScrollBarUI {

        @Override
        protected void configureScrollBarColors() {
            this.trackColor = new Color(72, 72, 72); // Kolor tła paska przewijania
            this.thumbColor = new Color(29, 185, 84); // Kolor kciuka paska przewijania
            this.thumbHighlightColor = new Color(0, 157, 59); // Kolor cienia dolnego kciuka
            this.thumbDarkShadowColor = new Color(0, 157, 59); // Kolor podświetlenia kciuka
            this.thumbLightShadowColor = new Color(29, 185, 84); // Kolor górnego cienia kciuka
            //this.trackHighlightColor = new Color(0, 157, 59); // Kolor podświetlenia tła paska przewijania
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
    static void podobne(JPanel red,String wyszukaj){
        List<List<String>> recommendations = SpotifySimilarSongs.getSimilarSongs(wyszukaj,TOKEN);
        red.removeAll();
        for (int i=0; i < 3; i++){
            JLabel pic = new JLabel();
            pic.setBackground(new Color(255,0,0));
            pic.setVisible(true);
            int x;
            if (i == 0) {
                x = 25;
            } else if (i == 1) {
                x = 195;
            } else {
                x = 365;
            }

            pic.setBounds(x, 10, 100, 100);
            red.add(pic);
            try {
                BufferedImage originalImage = ImageIO.read(new URL(recommendations.get(i).get(2)));
                Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

                ImageIcon imageIcon = new ImageIcon(scaledImage);
                pic.setIcon(imageIcon);
            } catch (IOException f) {
                f.printStackTrace();
            }
            JLabel nazwa = new JLabel(recommendations.get(i).get(0));
            nazwa.setVisible(true);
            nazwa.setBounds(x, 110, 100, 20);
            nazwa.setHorizontalAlignment(JLabel.CENTER);
            nazwa.setVerticalAlignment(JLabel.CENTER);
            red.add(nazwa);
            JLabel auto = new JLabel(recommendations.get(i).get(1));
            auto.setForeground(new Color(0, 157, 59));
            auto.setVisible(true);
            auto.setBounds(x, 130, 100, 20);
            auto.setHorizontalAlignment(JLabel.CENTER);
            auto.setVerticalAlignment(JLabel.CENTER);
            red.add(auto);


        }

    }
    private static <T> List<T> createSublist(List<T> originalList, int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > originalList.size() || fromIndex > toIndex) {
            throw new IllegalArgumentException("Invalid index range");
        }

        return new ArrayList<>(originalList.subList(fromIndex, toIndex));
    }
}
