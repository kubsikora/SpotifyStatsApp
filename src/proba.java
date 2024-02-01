import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicLabelUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

class SpotifyUserInfo {

    public static List<String> getUserInfo(String TOKEN) throws IOException {
        List<String> userInfo = new ArrayList<>();

        String apiUrl = "https://api.spotify.com/v1/me";

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String jsonResponse = reader.lines().collect(Collectors.joining());
                userInfo = extractUserInfo(jsonResponse);
            }
        } else {
            System.out.println("Error Response Code: " + responseCode);

            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                String errorResponse = errorReader.lines().collect(Collectors.joining());
                System.out.println("Error Response: " + errorResponse);
            }

            throw new IOException("HTTP error code: " + responseCode);
        }

        return userInfo;
    }

    private static List<String> extractUserInfo(String jsonResponse) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            String displayName = json.optString("display_name", "N/A");

            String imageUrl = "N/A";
            if (json.has("images")) {
                JSONArray images = json.getJSONArray("images");
                if (images.length() > 0) {
                    imageUrl = images.getJSONObject(0).getString("url");
                }
            }

            return List.of(displayName, imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return List.of("N/A", "N/A");
    }
}



public class proba{

    public static void createAndShowMainFrame(Integer var, JPanel mainFrame, String TOKEN) {
        mainFrame.removeAll();
        mainFrame.setLayout(new GridLayout(0, 1));

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(var, 1));

        for (int i = 0; i < var; i++) {
            JPanel panel = createPanelWithNumber(i, TOKEN, mainFrame);
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

    private static JPanel createPanelWithNumber(Integer i, String token, JPanel mainFrame) {
        Font fontxs = new Font(Font.SANS_SERIF, Font.BOLD, 11);
        Font fontms = new Font(Font.SANS_SERIF, Font.BOLD, 12);
        Font fontls = new Font(Font.SANS_SERIF, Font.BOLD, 13);
        Font fonts = new Font(Font.SANS_SERIF, Font.BOLD, 14);
        Font fontm = new Font(Font.SANS_SERIF, Font.BOLD, 19);
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
        Font fontl = new Font(Font.SANS_SERIF, Font.BOLD, 32);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setLayout(null);

        int panelWidth = 490;
        int panelHeight = 340;

        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        if(i==0){
            JLabel label0 = new JLabel();
            label0.setBounds(5, 5, 40, 40);
            panel.add(label0);
            List<String> topart = new ArrayList<>();
            List<String> toptrack = new ArrayList<>();
            List<String> toptrackpol = new ArrayList<>();
            String urlartimg = "";
            try {
                String[] parts= topartists.getTopArtists(token).get(0).split("@");
                topart.add(parts[0]);
                topart.add(parts[1]);
                String[] parts2= topartistspolroku.getTopArtists(token).get(0).split("@");
                topart.add(parts2[0]);
                topart.add(parts2[1]);
                toptrack = toptrackrok.getTopTracks(token);
                toptrackpol = toptrackpolroku.getTopTracks(token);
                //urlartimg = SpotifyArtistInfo.getArtistInfo(odp.get(1),token).get(2);
            } catch (IOException g) {
                g.printStackTrace();
            }
            RoundedPanel podguzikiem = new RoundedPanel(10,20,420,80);
            podguzikiem.setVisible(false);
            podguzikiem.setBackground(new Color(220, 220, 220));
            panel.add(podguzikiem);

            RoundedButton hoverButtonfake = new RoundedButton("");
            hoverButtonfake.setFont(fontxs);
            hoverButtonfake.setForeground(new Color(255,255,255));
            hoverButtonfake.setVisible(true);
            hoverButtonfake.setBounds(380,40,30,30);
            hoverButtonfake.setBackground(new Color(0, 157, 59));
            podguzikiem.add(hoverButtonfake);
            hoverButtonfake.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    podguzikiem.setVisible(true);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    podguzikiem.setVisible(false);
                }
            });

            JLabel popularnosc = new JLabel("<html>Na wykresach możesz zobaczyć<span style='color: rgb(0, 157, 59); display: inline;'> 5 artystów którzy mieli największy wpływ</span> na ranking twoich top utworów kiedykolwiek i w ostanim pół roku. Numerki oznaczaja jak dużo numerów tych artystów jest w twoim top 50.</html>");
            popularnosc.setForeground(new Color(0, 0, 0));
            popularnosc.setFont(fontms);
            popularnosc.setBounds(20,10,350,60);
            popularnosc.setVisible(true);
            podguzikiem.add(popularnosc);

            JLabel label = new JLabel("<html>Twoj ulubiony artysta to <span style='color: rgb(0, 157, 59); display: inline;'>" + topart.get(0)+"</span><br>" +
                    "Chodz w ostanim pół roku twoje serce kupił <span style='color: rgb(0, 157, 59); display: inline;'>" + topart.get(2)+"</span></html>");
            label.setFont(fonts);
            label.setForeground(new Color(72, 72, 72));
            label.setFont(fontls);
            label.setBounds(30, 10, 370, 40);
            panel.add(label);

            RoundedPanel grafslop = new RoundedPanel(10,50,420,230);
            grafslop.setVisible(true);
            grafslop.setLayout(null);
            grafslop.setBackground(new Color(72, 72, 72));
            panel.add(grafslop);

            JPanel kreska = new JPanel();
            kreska.setVisible(true);
            kreska.setLayout(null);
            kreska.setBounds(10,280,420,2);
            kreska.setBackground(new Color(0, 0, 0));
            //panel.add(kreska);

            Map<String, Integer> trackCounter = new Hashtable<>();
            for (int j = 0; j < toptrack.size(); j++) {
                String trackName = extractInformation(toptrack.get(j));

                if (trackCounter.containsKey(trackName)) {
                    int count = trackCounter.get(trackName);
                    trackCounter.put(trackName, count + 1);
                } else {
                    trackCounter.put(trackName, 1);
                }
            }
            List<List<String>> posorotwane = sortMapToList(trackCounter);
            for(int k = 0; k < 5; k++){
                Integer max = Integer.parseInt(posorotwane.get(0).get(1));
                JPanel slupek = new JPanel();
                slupek.setBackground(new Color(0, 157, 59));
                slupek.setBounds(35*(k+1),150-(100/max) * Integer.parseInt(posorotwane.get(k).get(1)),20,200);
                JLabel nazwa = new JLabel(posorotwane.get(k).get(0));
                nazwa.setUI(new VerticalLabelUI(false));
                nazwa.setFont(fontxs);
                nazwa.setForeground(new Color(255,255,255));
                nazwa.setBounds(35*(k+1),110,20,100);
                JLabel ilscever = new JLabel(" "+posorotwane.get(k).get(1));
                if(posorotwane.get(k).get(1).length()>1){
                    ilscever.setFont(fontm);
                    ilscever.setText(posorotwane.get(k).get(1));
                }else{
                    ilscever.setFont(fontm);
                }
                ilscever.setFont(fontm);
                ilscever.setForeground(new Color(0, 157, 59));
                ilscever.setBounds(35*(k+1),150-(100/max) * Integer.parseInt(posorotwane.get(k).get(1))-30,20,20);
                grafslop.add(ilscever);
                grafslop.add(nazwa);
                grafslop.add(slupek);
            }
            RoundedPanel polroczne = new RoundedPanel(210,0,210,230);
            polroczne.setBackground(new Color(50, 50, 50));
            polroczne.setVisible(true);
            polroczne.setLayout(null);
            grafslop.add(polroczne);

            Map<String, Integer> trackCounterpol = new Hashtable<>();
            for (int j = 0; j < toptrackpol.size(); j++) {
                String trackNamepol = extractInformation(toptrackpol.get(j));

                if (trackCounterpol.containsKey(trackNamepol)) {
                    int count = trackCounterpol.get(trackNamepol);
                    trackCounterpol.put(trackNamepol, count + 1);
                } else {
                    trackCounterpol.put(trackNamepol, 1);
                }
            }
            List<List<String>> posorotwanepol = sortMapToList(trackCounterpol);
            if(posorotwanepol.get(0).get(0).equals(topart.get(0))){
                label.setText("<html>Twój ulubiony artysta to <span style='color: rgb(0, 157, 59); display: inline;'>" + topart.get(0)+"</span></html>");
            } else{
                label.setText("<html>Twoj ulubiony artysta to <span style='color: rgb(0, 157, 59); display: inline;'>" + topart.get(0)+"</span><br>" +
                        "Choć w ostanim pół roku twoje serce kupił <span style='color: rgb(0, 157, 59); display: inline;'>" + posorotwanepol.get(0).get(0)+"</span></html>");
            }
            for(int k = 0; k < 5; k++){
                Integer max = Integer.parseInt(posorotwanepol.get(0).get(1));
                JPanel slupek = new JPanel();
                slupek.setBackground(new Color(0, 157, 59));
                slupek.setBounds(35*(k+1),150-(100/max) * Integer.parseInt(posorotwanepol.get(k).get(1)),20,199);
                JLabel nazwa = new JLabel(posorotwanepol.get(k).get(0));
                nazwa.setUI(new VerticalLabelUI(false));
                nazwa.setFont(fontxs);
                nazwa.setForeground(new Color(255,255,255));
                nazwa.setBounds(35*(k+1),110,20,100);
                JLabel ilscever = new JLabel(" "+posorotwanepol.get(k).get(1));
                if(posorotwanepol.get(k).get(1).length()>1){
                    ilscever.setFont(fonts);
                }else{
                    ilscever.setFont(fontm);
                }
                ilscever.setForeground(new Color(0, 157, 59));
                ilscever.setBounds(35*(k+1),150-(100/max) * Integer.parseInt(posorotwanepol.get(k).get(1))-30,30,20);
                polroczne.add(ilscever);
                polroczne.add(nazwa);
                polroczne.add(slupek);
            }
            JLabel pol = new JLabel("Statystyki z ostatnich 6 miesięcy");
            pol.setForeground(new Color(50,50,50));
            pol.setFont(fontxs);
            pol.setBounds(250,278,210,20);
            pol.setVisible(true);
            panel.add(pol);

            RoundedButton hoverButton = new RoundedButton("");
            hoverButton.setFont(fontxs);
            hoverButton.setForeground(new Color(255,255,255));
            hoverButton.setVisible(true);
            hoverButton.setBounds(170,10,30,30);
            hoverButton.setBackground(new Color(0, 157, 59));
            polroczne.add(hoverButton);
            hoverButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    if(mainFrame.isVisible()==true) {
                        podguzikiem.setVisible(true);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    podguzikiem.setVisible(false);
                }
            });

            JLabel ever = new JLabel("Statystyki od początku");
            ever.setForeground(new Color(72,72,72));
            ever.setFont(fontxs);
            ever.setBounds(60,278,210,20);
            ever.setVisible(true);
            panel.add(ever);


            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }
            });
            return panel;
        } else if (i==1) {
            String typy = "";
            try {
                List<String> top = topartists.getTopArtists(token);
                for (int j = 0; j < top.size(); j++){
                    String[] topid = top.get(j).split("@");
                    List<String> topinfoogol = SpotifyArtistInfo.getArtistInfo(topid[1],token);
                    typy = typy + ", " + topinfoogol.get(1);
                }
            } catch (IOException g) {
                g.printStackTrace();
            }
            List<String> listtop = Stringpodzialprzecinki(typy);

            RoundedPanel gatunki = new RoundedPanel(10,20,420,230);
            gatunki.setLayout(null);
            gatunki.setVisible(true);
            gatunki.setBackground(new Color(96, 96, 96));
            panel.add(gatunki);
            Integer max=10;
            for (int k = 0; k < 5; k++){
                List<String> striktetyp = new ArrayList<>(Arrays.asList(listtop.get(k).split("@")));
                if(k==0) {
                    max = Integer.parseInt(striktetyp.get(1));
                }
                JPanel slupek = new JPanel();
                slupek.setBackground(new Color(0, 157, 59));
                slupek.setBounds(0,35*(k+1),(100/max)*Integer.parseInt(striktetyp.get(1))+150,20);
                JLabel ilscever = new JLabel(" "+striktetyp.get(0));
                ilscever.setFont(fonts);
                ilscever.setForeground(new Color(255, 255, 255));
                ilscever.setBounds(10,35*(k+1),160,20);
                gatunki.add(ilscever);
                gatunki.add(slupek);
            }
            JLabel kom = new JLabel("<html><div style=\"text-align: right;\">Jesteś człowiekiem wielu gatunków ale szczególnie gustuje ci <span style='color: rgb(255, 255, 255); display: inline;'>" + (Arrays.asList(listtop.get(0).split("@"))).get(0)+"</span></div></html>");
            kom.setVisible(true);
            kom.setBounds(240, 0,160,190);
            kom.setFont(fonts);
            kom.setForeground(new Color(0, 0, 0));
            gatunki.add(kom);
            return panel;
        } else if (i==2) {
            RoundedPanel grafslop = new RoundedPanel(10,0,420,230);
            grafslop.setVisible(true);
            grafslop.setLayout(null);
            grafslop.setBackground(new Color(173, 173, 173));
            panel.add(grafslop);

            List<List<String>> result = new ArrayList<>();
            try {
                result = SpotifySongYear.getYearlyStatistics(token);
            } catch (IOException e) {
                e.printStackTrace();
            }
            JLabel popularnosc = new JLabel("<html>Każdy ma swoje ulubione czasy muzyki, twoim ewidetnie jest rok <span style='color: rgb(0, 157, 59); display: inline;'>" + result.get(0).get(0)
                    +", </span> choć nie było to łatwe zwycięstwo.</html>");
            popularnosc.setForeground(new Color(255, 255, 255));
            popularnosc.setFont(fontms);
            popularnosc.setBounds(20,230,410,60);
            popularnosc.setVisible(true);
            panel.add(popularnosc);

            JLabel pierwsze = new JLabel(result.get(0).get(0));
            pierwsze.setFont(fontl);
            pierwsze.setForeground(new Color(0,0,0));
            pierwsze.setBounds(185,65,101,30);
            grafslop.add(pierwsze);

            RoundedPanel top3 = new RoundedPanel(170,75,101,30);
            top3.arcWidth=80;
            top3.arcHeight=80;
            top3.setVisible(true);
            top3.setBackground(new Color(224, 188, 0));
            grafslop.add(top3);

            JPanel slup3 = new JPanel();
            slup3.setVisible(true);
            slup3.setBackground(new Color(255,215,0));
            slup3.setBounds(170,90,100,139);
            grafslop.add(slup3);

            JLabel liczba3 = new JLabel("1");
            liczba3.setBounds(40,200,20,20);
            liczba3.setFont(fontl);
            liczba3.setForeground(new Color(224, 188, 0));
            slup3.add(liczba3);

            JLabel drugie = new JLabel(result.get(1).get(0));
            drugie.setFont(fontl);
            drugie.setForeground(new Color(0,0,0));
            drugie.setBounds(105,95,81,30);
            grafslop.add(drugie);

            RoundedPanel top2 = new RoundedPanel(100,105,81,30);
            top2.arcWidth=80;
            top2.arcHeight=80;
            top2.setVisible(true);
            top2.setBackground(new Color(183, 183, 183));
            grafslop.add(top2);

            JPanel slup2 = new JPanel();
            slup2.setVisible(true);
            slup2.setBackground(new Color(211,211,211));
            slup2.setBounds(100,120,80,109);
            grafslop.add(slup2);

            JLabel liczba2 = new JLabel("2");
            liczba2.setBounds(30,200,20,20);
            liczba2.setFont(fontl);
            liczba2.setForeground(new Color(183, 183, 183));
            slup2.add(liczba2);

            JLabel trzecie = new JLabel(result.get(2).get(0));
            trzecie.setFont(fontl);
            trzecie.setForeground(new Color(0,0,0));
            trzecie.setBounds(265,115,81,30);
            grafslop.add(trzecie);

            RoundedPanel top4 = new RoundedPanel(260,125,81,30);
            top4.arcWidth=80;
            top4.arcHeight=80;
            top4.setVisible(true);
            top4.setBackground(new Color(155, 90, 26));
            grafslop.add(top4);

            JPanel slup4 = new JPanel();
            slup4.setVisible(true);
            slup4.setBackground(new Color(184,115,51));
            slup4.setBounds(260,140,80,89);
            grafslop.add(slup4);

            JLabel liczba4 = new JLabel("3");
            liczba4.setBounds(30,200,20,20);
            liczba4.setFont(fontl);
            liczba4.setForeground(new Color(155, 90, 26));
            slup4.add(liczba4);

            JLabel lewa = new JLabel(result.get(3).get(0));
            lewa.setFont(fontl);
            lewa.setForeground(new Color(0,0,0));
            lewa.setBounds(33,139,80,30);
            grafslop.add(lewa);

            RoundedPanel top1 = new RoundedPanel(30,149,71,30);
            top1.arcWidth=80;
            top1.arcHeight=80;
            top1.setVisible(true);
            top1.setBackground(new Color(136, 136, 136));
            grafslop.add(top1);

            JPanel slup1 = new JPanel();
            slup1.setVisible(true);
            slup1.setBackground(new Color(146, 146, 146));
            slup1.setBounds(30,164,70,65);
            grafslop.add(slup1);

            JLabel prawa = new JLabel(result.get(4).get(0));
            prawa.setFont(fontl);
            prawa.setForeground(new Color(0,0,0));
            prawa.setBounds(325,139,80,30);
            grafslop.add(prawa);

            RoundedPanel top5 = new RoundedPanel(330,149,71,30);
            top5.arcWidth=80;
            top5.arcHeight=80;
            top5.setVisible(true);
            top5.setBackground(new Color(136, 136, 136));
            grafslop.add(top5);

            JPanel slup5 = new JPanel();
            slup5.setVisible(true);
            slup5.setBackground(new Color(146, 146, 146));
            slup5.setBounds(330,164,70,65);
            grafslop.add(slup5);

            return panel;
        } else {
            return panel;
        }
    }
    public static List<List<String>> sortMapToList(Map<String, Integer> map) {
        List<List<String>> sortedList = map.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(entry -> Arrays.asList(entry.getKey(), entry.getValue().toString()))
                .collect(Collectors.toList());

        return sortedList;
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
    public static String extractInformation(String input) {
        int startIndex = input.indexOf("by") + 2;
        int endIndex = input.indexOf("@");

        if (startIndex >= 0 && endIndex >= 0 && startIndex < endIndex) {
            String[] dop = input.substring(startIndex, endIndex).trim().split(",");
            return dop[0];
        } else {
            System.out.println("Invalid input format.");
            return "";
        }
    }

    public static List<String> Stringpodzialprzecinki(String genresString) {
        List<String> genresList = Arrays.asList(genresString.split(",\\s*"));

        Map<String, Integer> genreCountMap = new HashMap<>();

        for (String genre : genresList) {
            genre = genre.trim();
            int count = genreCountMap.getOrDefault(genre, 0);
            genreCountMap.put(genre, count + 1);
        }

        List<Map.Entry<String, Integer>> sortedGenresList = new ArrayList<>(genreCountMap.entrySet());

        // Sortowanie po liczbie wystąpień w odwrotnej kolejności
        sortedGenresList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        List<String> resultGenresList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedGenresList) {
            resultGenresList.add(entry.getKey() + "@" + entry.getValue());
        }

        return resultGenresList;
    }
    static void panelstats(String token, JPanel Jstats) {

        List<String> userJson;

        JPanel Jstats2 = new JPanel();
        Jstats2.setBounds(0,0,510,480);
        Jstats2.setBackground(new Color(0, 0, 0));
        Jstats2.setVisible(true);
        Jstats2.setLayout(null);
        Jstats.add(Jstats2);

        JPanel uzytkownik = new JPanel();
        uzytkownik.setBackground(new Color(0, 0, 0));
        uzytkownik.setLayout(null);
        uzytkownik.setBounds(300,20,190,40);
        uzytkownik.setVisible(true);
        Jstats2.add(uzytkownik);

        JLabel nazwa = new JLabel();
        nazwa.setForeground(new Color(255,255,255));
        nazwa.setBounds(0,15,110,20);
        nazwa.setVisible(true);
        uzytkownik.add(nazwa);

        JLabel uzytpic = new JLabel();
        //uzytpic.setForeground(new Color(255,255,255));
        uzytpic.setBounds(100,10,50,40);
        uzytpic.setVisible(true);
        uzytkownik.add(uzytpic);

        JPanel scroll = new JPanel();
        scroll.setBackground(new Color(0, 0, 0));
        scroll.setBounds(20,100,455,340);
        scroll.setVisible(true);
        Jstats2.add(scroll);
        createAndShowMainFrame(3,scroll,token);




        try {
            userJson = SpotifyUserInfo.getUserInfo(token);
        } catch (IOException e) {
            userJson = new ArrayList<>();
        }

        try {
            BufferedImage originalImage = ImageIO.read(new URL(userJson.get(1)));
            Image scaledImage = originalImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);

            ImageIcon imageIcon = new ImageIcon(scaledImage);
            uzytpic.setIcon(imageIcon);
        } catch (IOException f) {
            f.printStackTrace();
        }
        nazwa.setText(userJson.get(0));
    }
}
class VerticalLabelUI extends BasicLabelUI {

    static {
        labelUI = new VerticalLabelUI(false);
    }

    protected boolean clockwise;

    public VerticalLabelUI(boolean clockwise) {
        super();
        this.clockwise = clockwise;
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        Dimension dim = super.getPreferredSize(c);
        return new Dimension( dim.height, dim.width );
    }

    private static Rectangle paintIconR = new Rectangle();
    private static Rectangle paintTextR = new Rectangle();
    private static Rectangle paintViewR = new Rectangle();
    private static Insets paintViewInsets = new Insets(0, 0, 0, 0);

    @Override
    public void paint(Graphics g, JComponent c) {
        JLabel label = (JLabel)c;
        String text = label.getText();
        Icon icon = (label.isEnabled()) ? label.getIcon() : label.getDisabledIcon();

        if (icon == null && text == null) {
            return;
        }

        FontMetrics fm = g.getFontMetrics();
        paintViewInsets = c.getInsets(paintViewInsets);

        paintViewR.x = paintViewInsets.left;
        paintViewR.y = paintViewInsets.top;

        // Use inverted height &amp; width
        paintViewR.height = c.getWidth() - (paintViewInsets.left + paintViewInsets.right);
        paintViewR.width = c.getHeight() - (paintViewInsets.top + paintViewInsets.bottom);

        paintIconR.x = paintIconR.y = paintIconR.width = paintIconR.height = 0;
        paintTextR.x = paintTextR.y = paintTextR.width = paintTextR.height = 0;

        String clippedText = layoutCL(label, fm, text, icon, paintViewR, paintIconR, paintTextR);

        Graphics2D g2 = (Graphics2D) g;
        AffineTransform tr = g2.getTransform();
        if (clockwise) {
            g2.rotate( Math.PI / 2 );
            g2.translate( 0, - c.getWidth() );
        } else {
            g2.rotate( - Math.PI / 2 );
            g2.translate( - c.getHeight(), 0 );
        }

        if (icon != null) {
            icon.paintIcon(c, g, paintIconR.x, paintIconR.y);
        }

        if (text != null) {
            int textX = paintTextR.x;
            int textY = paintTextR.y + fm.getAscent();

            if (label.isEnabled()) {
                paintEnabledText(label, g, clippedText, textX, textY);
            } else {
                paintDisabledText(label, g, clippedText, textX, textY);
            }
        }
        g2.setTransform( tr );
    }

}