import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
class songinfo{
    String id;
    public songinfo(String id){
        this.id = id;

    }
    public String getId(){
        return id;
    }
}
class sprawdzenie {
    // zwraca top 50 trackow(wiecej sie nie da)
    static Integer check(String TOKEN) throws IOException {
        List<String> allTracks = new ArrayList<>();


        String apiUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=long_term&limit=" + 50 + "&offset=" + 0;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

        BufferedReader reader;
        if (connection.getResponseCode() == 200) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        String jsonResponse = reader.lines().collect(Collectors.joining());
        if(jsonResponse.length() > 150){
            return 1;
        }else{
            return 0;
        }

    }
}
class toptrackrok {
    // zwraca top 50 trackow(wiecej sie nie da)
     static List<String> getTopTracks(String TOKEN) throws IOException {
        List<String> allTracks = new ArrayList<>();


            String apiUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=long_term&limit=" + 50 + "&offset=" + 0;

            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

            BufferedReader reader;
            if (connection.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String jsonResponse = reader.lines().collect(Collectors.joining());
            reader.close();
            List<String> tracks = extractTopTracks(jsonResponse);
            if (tracks != null) {
                allTracks.addAll(tracks);
            }

        return allTracks;
    }

    private static List<String> extractTopTracks(String jsonResponse) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray items = json.optJSONArray("items");

            if (items != null) {
                return items.toList().stream()
                        .map(item -> {
                            JSONObject track = new JSONObject((Map<String, Object>) item);
                            String name = track.getString("name");
                            JSONArray artistsArray = track.getJSONArray("artists");
                            String artists = artistsArray.toList().stream()
                                    .map(artist -> {
                                        JSONObject artistObject = new JSONObject((Map<String, Object>) artist);
                                        return artistObject.getString("name");
                                    })
                                    .collect(Collectors.joining(", "));
                            String id = track.getString("uri").split(":")[2];
                            return name + " by " + artists + " @/A" + id;
                        })
                        .collect(Collectors.toList());
            } else {
                System.out.println("No items found in the response.");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
class toptrackpolroku {
    // zwraca top 50 trackow ostatniego pol roku (wiecej sie nie da)

    static List<String> getTopTracks(String TOKEN) throws IOException {
        List<String> allTracks = new ArrayList<>();


        String apiUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=medium_term&limit=" + 50 + "&offset=" + 0;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

        BufferedReader reader;
        if (connection.getResponseCode() == 200) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        String jsonResponse = reader.lines().collect(Collectors.joining());
        reader.close();
        List<String> tracks = extractTopTracks(jsonResponse);
        if (tracks != null) {
            allTracks.addAll(tracks);
        }

        return allTracks;
    }

    private static List<String> extractTopTracks(String jsonResponse) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray items = json.optJSONArray("items");

            if (items != null) {
                return items.toList().stream()
                        .map(item -> {
                            JSONObject track = new JSONObject((Map<String, Object>) item);
                            String name = track.getString("name");
                            JSONArray artistsArray = track.getJSONArray("artists");
                            String artists = artistsArray.toList().stream()
                                    .map(artist -> {
                                        JSONObject artistObject = new JSONObject((Map<String, Object>) artist);
                                        return artistObject.getString("name");
                                    })
                                    .collect(Collectors.joining(", "));
                            String id = track.getString("uri").split(":")[2];
                            return name + " by " + artists + " @/A" + id;
                        })
                        .collect(Collectors.toList());
            } else {
                System.out.println("No items found in the response.");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
class toptrackmiesiac {
    // zwraca top 50 trackow ostatniego miesiaca (wiecej sie nie da)
    static List<String> getTopTracks(String TOKEN) throws IOException {
        List<String> allTracks = new ArrayList<>();


        String apiUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=short_term&limit=" + 50 + "&offset=" + 0;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

        BufferedReader reader;
        if (connection.getResponseCode() == 200) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        String jsonResponse = reader.lines().collect(Collectors.joining());
        reader.close();
        List<String> tracks = extractTopTracks(jsonResponse);
        if (tracks != null) {
            allTracks.addAll(tracks);
        }

        return allTracks;
    }

    private static List<String> extractTopTracks(String jsonResponse) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray items = json.optJSONArray("items");

            if (items != null) {
                return items.toList().stream()
                        .map(item -> {
                            JSONObject track = new JSONObject((Map<String, Object>) item);
                            String name = track.getString("name");
                            JSONArray artistsArray = track.getJSONArray("artists");
                            String artists = artistsArray.toList().stream()
                                    .map(artist -> {
                                        JSONObject artistObject = new JSONObject((Map<String, Object>) artist);
                                        return artistObject.getString("name");
                                    })
                                    .collect(Collectors.joining(", "));
                            String id = track.getString("uri").split(":")[2];
                            return name + " by " + artists + " @/A" + id;
                        })
                        .collect(Collectors.toList());
            } else {
                System.out.println("No items found in the response.");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
class topartists {
    // Klasa pozwalająca pobrać top 50 artystów w liście jeden po drugim. Jako argument potrzebuje TOKEN.
    public static List<String> getTopArtists(String TOKEN) throws IOException {
        List<String> allArtists = new ArrayList<>();

        String apiUrl = "https://api.spotify.com/v1/me/top/artists?time_range=long_term&limit=" + 50 + "&offset=" + 0;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

        BufferedReader reader;
        if (connection.getResponseCode() == 200) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        String jsonResponse = reader.lines().collect(Collectors.joining());
        reader.close();

        List<String> artists = extractTopArtists(jsonResponse);
        if (artists != null) {
            allArtists.addAll(artists);
        }

        return allArtists;
    }

    private static List<String> extractTopArtists(String jsonResponse) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray items = json.optJSONArray("items");

            if (items != null) {
                return items.toList().stream()
                        .map(item -> {
                            JSONObject artistJson = new JSONObject((Map<String, Object>) item);
                            String id = artistJson.getString("id");
                            String name = artistJson.getString("name");
                            return name + "@" + id;
                        })
                        .collect(Collectors.toList());
            } else {
                System.out.println("No items found in the response.");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
class topartistspolroku {
    //klasa pozwalajaca nam pobrac top 50 artystow ostatniego pol roku w liscie jeden po drugim, jako argument porzebuje TOKEN
    static List<String> getTopArtists(String TOKEN) throws IOException {
        List<String> allArtists = new ArrayList<>();

        String apiUrl = "https://api.spotify.com/v1/me/top/artists?time_range=medium_term&limit=" + 50 + "&offset=" + 0;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

        BufferedReader reader;
        if (connection.getResponseCode() == 200) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        String jsonResponse = reader.lines().collect(Collectors.joining());
        reader.close();

        List<String> artists = extractTopArtists(jsonResponse);
        if (artists != null) {
            allArtists.addAll(artists);
        }

        return allArtists;
    }

    private static List<String> extractTopArtists(String jsonResponse) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray items = json.optJSONArray("items");

            if (items != null) {
                return items.toList().stream()
                        .map(item -> {
                            JSONObject artist = new JSONObject((Map<String, Object>) item);
                            String id = artist.getString("id");
                            String name = artist.getString("name");
                            return name + "@" + id;
                        })
                        .collect(Collectors.toList());
            } else {
                System.out.println("No items found in the response.");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
class topartistsmiesiac {
    //klasa pozwalajaca nam pobrac top 50 artystow ostatniego miesiaca w liscie jeden po drugim, jako argument porzebuje TOKEN
    static List<String> getTopArtists(String TOKEN) throws IOException {
        List<String> allArtists = new ArrayList<>();

        String apiUrl = "https://api.spotify.com/v1/me/top/artists?time_range=short_term&limit=" + 50 + "&offset=" + 0;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

        BufferedReader reader;
        if (connection.getResponseCode() == 200) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        String jsonResponse = reader.lines().collect(Collectors.joining());
        reader.close();

        List<String> artists = extractTopArtists(jsonResponse);
        if (artists != null) {
            allArtists.addAll(artists);
        }

        return allArtists;
    }

    private static List<String> extractTopArtists(String jsonResponse) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray items = json.optJSONArray("items");

            if (items != null) {
                return items.toList().stream()
                        .map(item -> {
                            JSONObject artist = new JSONObject((Map<String, Object>) item);
                            String id = artist.getString("id");
                            String name = artist.getString("name");
                            return name + "@" + id;
                        })
                        .collect(Collectors.toList());
            } else {
                System.out.println("No items found in the response.");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
class topalbumrok {
    //klasa pobierajaca 50 top albumow w liscie jeden po drugim, jako argument porzebuje TOKEN
    public static List<String> getTopAlbums(String TOKEN) throws IOException {
        List<String> allAlbums = new ArrayList<>();

        String apiUrl = "https://api.spotify.com/v1/me/top/artists?time_range=long_term&limit=" + 50 + "&offset=" + 0;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

        BufferedReader reader;
        if (connection.getResponseCode() == 200) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        String jsonResponse = reader.lines().collect(Collectors.joining());
        reader.close();
        List<String> albums = extractTopAlbums(jsonResponse, TOKEN);
        if (albums != null) {
            allAlbums.addAll(albums);
        }

        return allAlbums;
    }

    private static List<String> extractTopAlbums(String jsonResponse, String TOKEN) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray items = json.optJSONArray("items");

            if (items != null) {
                return items.toList().stream()
                        .map(item -> {
                            if (item instanceof Map) {
                                JSONObject artist = new JSONObject((Map<String, Object>) item);
                                String id = artist.has("id") ? artist.getString("id") : "";
                                return getAlbumsByArtist(id, TOKEN);
                            } else {
                                return "";
                            }
                        })
                        .collect(Collectors.toList());
            } else {
                System.out.println("No items found in the response.");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getAlbumsByArtist(String artistId, String TOKEN) {
        try {
            String apiUrl = "https://api.spotify.com/v1/artists/" + artistId + "/albums?limit=1";
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

            BufferedReader reader;
            if (connection.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String jsonResponse = reader.lines().collect(Collectors.joining());
            reader.close();

            JSONObject json = new JSONObject(jsonResponse);
            JSONArray items = json.optJSONArray("items");

            if (items != null && items.length() > 0) {
                JSONObject album = items.getJSONObject(0);
                String albumName = album.getString("name");
                String artistName = album.getJSONArray("artists").getJSONObject(0).getString("name");
                String albumId = album.getString("id");
                return albumName + " by " + artistName + " @/A" + albumId;
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
class topalbumpolroku {
    //klasa pobierajaca 50 top albumow z ostatniego pol roku w liscie jeden po drugim, jako argument porzebuje TOKEN
    public static List<String> getTopAlbums(String TOKEN) throws IOException {
        List<String> allAlbums = new ArrayList<>();

        String apiUrl = "https://api.spotify.com/v1/me/top/artists?time_range=medium_term&limit=" + 50 + "&offset=" + 0;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

        BufferedReader reader;
        if (connection.getResponseCode() == 200) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        String jsonResponse = reader.lines().collect(Collectors.joining());
        reader.close();
        List<String> albums = extractTopAlbums(jsonResponse, TOKEN);
        if (albums != null) {
            allAlbums.addAll(albums);
        }

        return allAlbums;
    }

    private static List<String> extractTopAlbums(String jsonResponse, String TOKEN) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray items = json.optJSONArray("items");

            if (items != null) {
                return items.toList().stream()
                        .map(item -> {
                            if (item instanceof Map) {
                                JSONObject artist = new JSONObject((Map<String, Object>) item);
                                String id = artist.has("id") ? artist.getString("id") : "";
                                return getAlbumsByArtist(id, TOKEN);
                            } else {
                                return "";
                            }
                        })
                        .collect(Collectors.toList());
            } else {
                System.out.println("No items found in the response.");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getAlbumsByArtist(String artistId, String TOKEN) {
        try {
            String apiUrl = "https://api.spotify.com/v1/artists/" + artistId + "/albums?limit=1";
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

            BufferedReader reader;
            if (connection.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String jsonResponse = reader.lines().collect(Collectors.joining());
            reader.close();

            JSONObject json = new JSONObject(jsonResponse);
            JSONArray items = json.optJSONArray("items");

            if (items != null && items.length() > 0) {
                JSONObject album = items.getJSONObject(0);
                String albumName = album.getString("name");
                String artistName = album.getJSONArray("artists").getJSONObject(0).getString("name");
                String albumId = album.getString("id");
                return albumName + " by " + artistName + " @/A" + albumId;
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
class topalbummiesiac {
    //klasa pobierajaca 50 top albumow z ostatenigo miesiaca w liscie jeden po drugim, jako argument porzebuje TOKEN
    public static List<String> getTopAlbums(String TOKEN) throws IOException {
        List<String> allAlbums = new ArrayList<>();

        String apiUrl = "https://api.spotify.com/v1/me/top/artists?time_range=short_term&limit=" + 50 + "&offset=" + 0;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

        BufferedReader reader;
        if (connection.getResponseCode() == 200) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        String jsonResponse = reader.lines().collect(Collectors.joining());
        reader.close();
        List<String> albums = extractTopAlbums(jsonResponse, TOKEN);
        if (albums != null) {
            allAlbums.addAll(albums);
        }

        return allAlbums;
    }

    private static List<String> extractTopAlbums(String jsonResponse, String TOKEN) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray items = json.optJSONArray("items");

            if (items != null) {
                return items.toList().stream()
                        .map(item -> {
                            if (item instanceof Map) {
                                JSONObject artist = new JSONObject((Map<String, Object>) item);
                                String id = artist.has("id") ? artist.getString("id") : "";
                                return getAlbumsByArtist(id, TOKEN);
                            } else {
                                return "";
                            }
                        })
                        .collect(Collectors.toList());
            } else {
                System.out.println("No items found in the response.");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getAlbumsByArtist(String artistId, String TOKEN) {
        try {
            String apiUrl = "https://api.spotify.com/v1/artists/" + artistId + "/albums?limit=1";
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

            BufferedReader reader;
            if (connection.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String jsonResponse = reader.lines().collect(Collectors.joining());
            reader.close();

            JSONObject json = new JSONObject(jsonResponse);
            JSONArray items = json.optJSONArray("items");

            if (items != null && items.length() > 0) {
                JSONObject album = items.getJSONObject(0);
                String albumName = album.getString("name");
                String artistName = album.getJSONArray("artists").getJSONObject(0).getString("name");
                String albumId = album.getString("id");
                return albumName + " by " + artistName + " @/A" + albumId;
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
class artistinfo {
    //klasa przyjmuje jako arguemnty (nazwe artysty String, TOKEN) i zwraca nam gatunek muzyczny
    //tego artysty (jako lista String) oraz procent popularnosci (Int)
    static ArtistInfo getArtistInfo(String artistName, String TOKEN) throws IOException {
        String encodedArtistName = URLEncoder.encode(artistName, StandardCharsets.UTF_8.toString());
        String apiUrl = "https://api.spotify.com/v1/search?q=" + encodedArtistName + "&type=artist&limit=1";

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String jsonResponse = reader.lines().collect(Collectors.joining());
            return extractArtistInfo(jsonResponse);
        }
    }

    private static ArtistInfo extractArtistInfo(String jsonResponse) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONObject artist = json.getJSONObject("artists").getJSONArray("items").optJSONObject(0);

            if (artist != null) {
                int popularity = artist.optInt("popularity", 0);
                List<String> genres = extractGenres(artist.getJSONArray("genres"));

                return new ArtistInfo(popularity, genres);
            } else {
                System.out.println("No artist found in the response.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> extractGenres(JSONArray genresArray) {
        List<String> genres = new ArrayList<>();
        for (int i = 0; i < genresArray.length(); i++) {
            genres.add(genresArray.getString(i));
        }
        return genres;
    }

    static class ArtistInfo {
        private final int popularity;
        private final List<String> genres;

        public ArtistInfo(int popularity, List<String> genres) {
            this.popularity = popularity;
            this.genres = genres;
        }

        public int getPopularity() {
            return popularity;
        }

        public List<String> getGenre() {
            return genres;
        }
    }
}
class songsearch {
    //klasa majaca za zadanie wyszukac po podaniu mu (String nazwa piosenki, TOKEN) piosenki i jej wykonawcy
    // jak i jej id, zwraca w formie List<String>
    public List<String> search(String searchQuery, String accessToken) {
        List<String> searchResults = new ArrayList<>();

        try {
            String apiUrl = "https://api.spotify.com/v1/search?q=" + searchQuery + "&type=track";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            if (connection.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String jsonResponse = reader.lines().collect(Collectors.joining());
                    searchResults.addAll(extractTopTracks(jsonResponse));
                }
            } else {
                System.out.println("Błąd podczas wykonania zapytania. Kod odpowiedzi: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

    private List<String> extractTopTracks(String jsonResponse) {
        List<String> search = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONObject tracksObject = json.optJSONObject("tracks");
            JSONArray items = tracksObject != null ? tracksObject.optJSONArray("items") : null;

            if (items != null) {
                search.addAll(items.toList().stream()
                        .map(item -> {
                            JSONObject track = new JSONObject((Map<String, Object>) item);
                            String name = track.getString("name");
                            JSONArray artistsArray = track.getJSONArray("artists");
                            String artists = artistsArray.toList().stream()
                                    .map(artist -> {
                                        JSONObject artistObject = new JSONObject((Map<String, Object>) artist);
                                        return artistObject.getString("name");
                                    })
                                    .collect(Collectors.joining(", "));
                            String id = track.getString("id");
                            return name + " by " + artists + " @/A" + id;
                        })
                        .collect(Collectors.toList()));
            } else {
                System.out.println("No items found in the response.");
            }

            return search;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

class MyFrame extends JFrame{
    JFrame okno;
    RoundedPanel FirstPage, wynik, red;
    RoundedButton balbumy, bpiosenki, bartist, bpolroku, bmiesiac, brok;
    JPanel kontener, Jstats, Jwyborlewy, Jwyborprawy, Jwyborlewyczas, Jwyborprawyczas, records6, records1, records4, albums6, albums4, albums1, artist1, artist4, artist6 ,Jprawa;
    JLabel nazwautw,nazwaart,opisnuty, Jpic, opisnutykat;
    Font font, fonts, fontm, fontxs;
    Integer inwindow = 0;
    Integer symulacjaprawidlowegopobraniatokenu = 0;
    String tokenfake = "BQA3LvdI7u88yQsGeAUiDACdYwB3iqZOYuloia6kLd6dqSCir0LUhixaP4CRw22GQHQzLayQ-Uvrh3cdLwNZiUvIiCQ8t4O6l9qyp-9yRpotq-H4HDzQNBLkVz1z9J8NR81b9thqG9LM1A9FvyM08SBqtm_Z5mEIWfobjmW8YEbnnYnWMvjGRjE_yhvI6l_NVFOPZ4R2lEAj-xhsvESV4UAcy4MGw5PRtotPCXXR2mo434UaWkuYWEPs0ncB4H6nDfsaKiM";
    String token = "BQAVDRVzZ093BC3fu64-kTjfwKEnQEZF3R8SSGrgInE_sjbGOnKlYH8IlbfKzyedn3TsnbrLhWWtUKJWn4E4PwO3YZ82Z0YfYbtAcO6Xrvv38uU84SzUK7go6X644Xt80kmkoiLxVqI0AFJ5mXcinn5HhrKj9jaMj0j-wXzEuZbidg2fSAZPDff_bSAg65Ys2FRNsv0KSYp-fWiSpWMnOFTOp-Rq_SwRXKAkVy8yZKmABEzSPDo8LTSKBgavDqLUMXewsFw";
    String wybor = "artist";
    String wyborczas = "6 Months";

    int blad_log = 0;

    ImageIcon imageIcon;

    MyFrame(){
        font = new Font(Font.SANS_SERIF, Font.BOLD,20);
        fontm = new Font(Font.SANS_SERIF, Font.BOLD,18);
        fonts = new Font(Font.SANS_SERIF, Font.BOLD,16);
        fontxs = new Font(Font.SANS_SERIF, Font.BOLD,12);


        //https://coolors.co/palette/000000-14213d-fca311-e5e5e5-ffffff


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(new Color(255,255,255));
        this.setLayout(null);
        this.setSize(1000,600);
        this.setResizable(false);
        this.setLocation(300,200);
        this.setBackground(new Color(79, 78, 78));
        this.setVisible(true);

        kontener = new JPanel();
        kontener.setBackground(new Color(31, 31, 31));
        kontener.setVisible(true);
        kontener.setLayout(null);
        kontener.setSize(1000,600);
        this.add(kontener);

        RoundedPanel logowanie = new RoundedPanel(93,30,800,500);
        logowanie.setBackground(new Color(0,0,0));
        logowanie.setVisible(false);
        //------------------------------- STATYSYKI OGOLNE ------------------------------------//

        JPanel Jstats = new JPanel();
        Jstats.setBounds(60,10,510,480);
        Jstats.setBackground(new Color(255, 255, 255));
        Jstats.setVisible(false);
        Jstats.setLayout(null);



        //------------------------------- STATYSYKI OGOLNE KONIEC ------------------------------------//

        //------------------------------- SHOW PANEL ----------------------------------------//
        // w tym sa wyniki wyborow z list
        Jprawa = new JPanel();
        Jprawa.setBounds(60,10,510,480);
        Jprawa.setBackground(new Color(0,0,0));
        Jprawa.setVisible(false);
        //prawa.setVisible(true);
        Jprawa.setLayout(null);


        JPanel Jpics = new JPanel();
        Jpics.setBounds(180,20,150,150);
        Jpics.setBackground(new Color(0, 13, 255));
        Jpics.setVisible(true);
        Jpics.setLayout(null);
        Jprawa.add(Jpics);

        red = new RoundedPanel(10,320,490,150);
        red.setBackground(new Color(31, 31, 31));
        red.setVisible(true);
        Jprawa.add(red);

        RoundedButton statystyki = new RoundedButton("Back");
        //statystyki.setFont(fonts);
        statystyki.setBounds(410,20,80,40);
        statystyki.setBackground(new Color(0, 157, 59));
        statystyki.setForeground(new Color(255,255,255));
        statystyki.setVisible(true);
        statystyki.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Jprawa.setVisible(false);
                Jstats.setVisible(true);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                statystyki.setBackground(new Color(2, 185, 71));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                statystyki.setBackground(new Color(0, 157, 59));
            }
        });
        Jprawa.add(statystyki);

        Jpic = new JLabel();
        Jpic.setBounds(0,0,150,150);
        Jpic.setVisible(true);
        Jpic.setLayout(null);
        Jpics.add(Jpic);
        try {
            BufferedImage originalImage = ImageIO.read(new URL("https://i.scdn.co/image/ab67616d0000b273ed638f79a6e617d613528e35"));
            Image scaledImage = originalImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);

            ImageIcon imageIcon = new ImageIcon(scaledImage);
            Jpic.setIcon(imageIcon);
        } catch (IOException f) {
            f.printStackTrace();
        }


        nazwautw = new JLabel("<33");
        nazwautw.setFont(fontxs);
        nazwautw.setVisible(true);
        nazwautw.setBounds(130,172,250,20);
        nazwautw.setHorizontalAlignment(JLabel.CENTER);
        nazwautw.setVerticalAlignment(JLabel.CENTER);
        Jprawa.add(nazwautw);

        nazwaart = new JLabel("Mata");
        nazwaart.setFont(fonts);
        nazwaart.setForeground(new Color(0, 157, 59));
        nazwaart.setVisible(true);
        nazwaart.setBounds(155,192,200,20);
        nazwaart.setHorizontalAlignment(JLabel.CENTER);
        nazwaart.setVerticalAlignment(JLabel.CENTER);
        Jprawa.add(nazwaart);

        opisnutykat = new JLabel("<html>Z albumu: <br>premiera: <br>długość:   <br>posłuch: </html>");
        opisnutykat.setFont(fonts);
        opisnutykat.setVisible(true);
        opisnutykat.setBounds(20,220,105,300);
        opisnutykat.setHorizontalAlignment(JLabel.LEFT);
        opisnutykat.setVerticalAlignment(JLabel.TOP);
        Jprawa.add(opisnutykat);

        opisnuty = new JLabel("<html>most cows <br>3min20sekund  <br>73 </html>");
        opisnuty.setFont(fonts);
        opisnuty.setVisible(true);
        opisnuty.setForeground(new Color(0, 157, 59));
        opisnuty.setBounds(100,220,500,300);
        opisnuty.setHorizontalAlignment(JLabel.LEFT);
        opisnuty.setVerticalAlignment(JLabel.TOP);
        Jprawa.add(opisnuty);


        JPanel odzielnik = new JPanel();
        odzielnik.setBounds(20,215,470,3);
        odzielnik.setBackground(new Color(38, 38, 38));
        odzielnik.setVisible(true);
        Jprawa.add(odzielnik);
        //------------------------------- SHOW PANEL KONIEC ----------------------------------------//

        //------------------------------- INTERFACE ----------------------------------------//
        JPanel Jinterface = new JPanel();
        Jinterface.setBounds(93,30,800,500);
        Jinterface.setBackground(new Color(31,31,31));
        Jinterface.setVisible(false);
        Jinterface.setLayout(null);
        kontener.add(Jinterface);

        RoundedPanel lista = new RoundedPanel(0,0,270,500);
        lista.setBackground(new Color(38, 38, 38));
        lista.setVisible(true);
        Jinterface.add(lista);

        JPanel kontenerstat = new JPanel();
        kontenerstat.setBounds(0,0,800,500);
        kontenerstat.setBackground(new Color(31,31,31));
        kontenerstat.setVisible(true);
        kontenerstat.setLayout(null);

        JPanel kontenerstatprawa = new JPanel();
        kontenerstatprawa.setBounds(270,50,500,400);
        kontenerstatprawa.setBackground(new Color(0, 0, 0));
        kontenerstatprawa.setVisible(true);
        kontenerstatprawa.setLayout(null);
        kontenerstat.add(kontenerstatprawa);

        JLabel witajtekst = new JLabel("<html><center></center><html>");
        witajtekst.setBounds(20,205,230,60);
        witajtekst.setFont(fonts);
        witajtekst.setForeground(new Color(56, 56, 56));
        witajtekst.setBackground(new Color(0, 0, 0));
        //witajtekst.setOpaque(true);
        //kontenerstatprawa.add(witajtekst);

        ImageIcon podlistaicon = new ImageIcon("staty.png");
        JLabel podlistaimage = new JLabel(podlistaicon);
        podlistaimage.setVisible(false);
        podlistaimage.setBounds(52,14,400,350);
        kontenerstatprawa.add(podlistaimage);

        RoundedButton podlista = new RoundedButton("<html><center>Sprawdź swoje statystyki</center><html>");
        podlista.arcHeight = 70;
        podlista.arcWidth = 70;
        podlista.setVisible(true);
        podlista.setFont(fonts);
        podlista.setBackground(new Color(0, 157, 59));
        podlista.setForeground(new Color(255,255,255));
        podlista.setBounds(140,160,220,80);
        kontenerstatprawa.add(podlista);
        podlista.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                kontenerstat.setVisible(false);
                Jstats.setVisible(true);
                List<String> odp = new ArrayList<>();
                try {
                    odp = topartistspolroku.getTopArtists(token);
                } catch (IOException g) {
                    g.printStackTrace();
                }
                setPanelsVisibility(false, records1, records4, records6, albums6, albums4, albums1, artist4, artist6);
                artist1.removeAll();
                artist1.setVisible(true);
                ScrollableJFrame.createAndShowMainFrame(odp,  artist1,token,Jprawa,Jpic,nazwautw,nazwaart,opisnuty,red, opisnutykat, Jstats);

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                podlistaimage.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                podlistaimage.setVisible(false);
            }
        });
        podlista.repaint();


        JLabel obokguzik = new JLabel("<html><center>Już za chwilę pojawią się tu <br>listy twoich ulubionych płyt, artystów i piosenek!</center><html>");
        obokguzik.setBounds(20,205,230,60);
        obokguzik.setFont(fonts);
        obokguzik.setForeground(new Color(56, 56, 56));
        obokguzik.setBackground(new Color(38, 38, 38));
        obokguzik.setOpaque(true);
        kontenerstat.add(obokguzik);



        wynik = new RoundedPanel(220,0,580,500);
        wynik.setBackground(new Color(0,0,0));
        wynik.setVisible(true);
        Jinterface.add(wynik);

        wynik.add(Jprawa);
        wynik.add(Jstats);
        Jinterface.add(kontenerstat);



        RoundedButton bartist = new RoundedButton("Artist");
        bartist.setForeground(new Color(255, 255, 255));
        bartist.arcWidth= 0;
        bartist.arcHeight= 0;
        bartist.setFont(fonts);
        bartist.setVisible(true);
        bartist.setBounds(90,0,90,50);
        bartist.setBackground(new Color(0, 157, 59));
        bartist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!wybor.equals("artist")){
                    kontenerstat.setVisible(false);
                    wybor = "artist";
                    bartist.setBackground(new Color(0, 157, 59));
                    bartist.setForeground(new Color(255, 255, 255));
                    balbumy.setBackground(new Color(42, 42, 42));
                    balbumy.setForeground(new Color(129, 129, 129));
                    bpiosenki.setBackground(new Color(42, 42, 42));
                    bpiosenki.setForeground(new Color(129, 129, 129));
                    Jwyborlewy.setBackground(new Color(42, 42, 42));
                    Jwyborprawy.setBackground(new Color(42, 42, 42));
                    List<String> odp = new ArrayList<>();
                    if(wyborczas.equals("6 Months")) {
                        try {
                            odp = topartistspolroku.getTopArtists(token);
                        } catch (IOException g) {
                            g.printStackTrace();
                        }
                        setPanelsVisibility(false, records1, records4, records6, albums6, albums4, albums1, artist4, artist6);
                        artist1.removeAll();
                        artist1.setVisible(true);
                        ScrollableJFrame.createAndShowMainFrame(odp,  artist1,token,Jprawa,Jpic,nazwautw,nazwaart,opisnuty,red, opisnutykat, Jstats);
                    }
                    if(wyborczas.equals("4 Weeks")) {
                        try {
                            odp = topartistsmiesiac.getTopArtists(token);
                        } catch (IOException g) {
                            g.printStackTrace();
                        }
                        setPanelsVisibility(false, records6, records1, records4, albums6, albums4, albums1, artist1, records4, artist6);
                        artist4.removeAll();
                        artist4.setVisible(true);
                        ScrollableJFrame.createAndShowMainFrame(odp, artist4, token,Jprawa,Jpic,nazwautw,nazwaart,opisnuty,red, opisnutykat, Jstats);
                    }
                    if(wyborczas.equals("1 Year")) {
                        try {
                            odp = topartists.getTopArtists(token);
                        } catch (IOException g) {
                            g.printStackTrace();
                        }
                        setPanelsVisibility(false, records6, records4, albums6, albums4, albums1, records1, artist4, artist6);
                        artist1.removeAll();
                        artist1.setVisible(true);
                        ScrollableJFrame.createAndShowMainFrame(odp, artist1, token,Jprawa,Jpic,nazwautw,nazwaart,opisnuty,red, opisnutykat, Jstats);
                    }

                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(!wybor.equals("artist")){
                    bartist.setBackground(new Color(56, 56, 56));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!wybor.equals("artist")) {
                    bartist.setBackground(new Color(42, 42, 42));
                }
            }
        });
        lista.add(bartist);


        bpiosenki = new RoundedButton("  Songs");
        bpiosenki.setHorizontalAlignment(SwingConstants.LEFT);
        bpiosenki.setForeground(new Color(129, 129, 129));
        bpiosenki.arcWidth = 35;
        bpiosenki.arcHeight = 35;
        bpiosenki.setFont(fonts);
        bpiosenki.setVisible(true);
        bpiosenki.setBounds(0, 0, 130, 50);
        bpiosenki.setBackground(new Color(42, 42, 42));
        bpiosenki.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                kontenerstat.setVisible(false);
                handleCategoryClick("songs", bpiosenki, Jwyborlewy, balbumy, bartist);
                Jwyborprawy.setBackground(new Color(42, 42, 42));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                handleMouseEnter(bpiosenki, Jwyborlewy);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                handleMouseExit(bpiosenki, Jwyborlewy);
            }
        });
        lista.add(bpiosenki);

        balbumy = new RoundedButton("Albums  ");
        balbumy.setHorizontalAlignment(SwingConstants.RIGHT);
        balbumy.arcWidth = 35;
        balbumy.arcHeight = 35;
        balbumy.setForeground(new Color(129, 129, 129));
        balbumy.setFont(fonts);
        balbumy.setVisible(true);
        balbumy.setBounds(140, 0, 130, 50);
        balbumy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                kontenerstat.setVisible(false);
                handleCategoryClick("albums", balbumy, Jwyborprawy, bartist, bpiosenki);
                Jwyborlewy.setBackground(new Color(42, 42, 42));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                handleMouseEnter(balbumy, Jwyborprawy);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                handleMouseExit(balbumy, Jwyborprawy);
            }
        });
        balbumy.setBackground(new Color(42, 42, 42));
        lista.add(balbumy);

        Jwyborlewy = new JPanel();
        Jwyborlewy.setBounds(0,35,90,14);
        Jwyborlewy.setBackground(new Color(42, 42, 42));
        Jwyborlewy.setVisible(true);
        lista.add(Jwyborlewy);

        Jwyborprawy = new JPanel();
        Jwyborprawy.setBounds(200,35,69,14);
        Jwyborprawy.setBackground(new Color(42, 42, 42));
        Jwyborprawy.setVisible(true);
        lista.add(Jwyborprawy);


        RoundedButton bpolroku = new RoundedButton("6 Months");
        bpolroku.setForeground(new Color(255, 255, 255));
        bpolroku.setHorizontalAlignment(SwingConstants.LEFT);
        bpolroku.arcWidth= 0;
        bpolroku.arcHeight= 0;
        bpolroku.setFont(fontxs);
        bpolroku.setVisible(true);
        bpolroku.setBounds(90,450,90,50);
        bpolroku.setBackground(new Color(0, 157, 59));
        bpolroku.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!wyborczas.equals("6 Months")) {
                    kontenerstat.setVisible(false);
                    wyborczas="6 Months";
                    bpolroku.setBackground(new Color(0, 157, 59));
                    bpolroku.setForeground(new Color(255, 255, 255));
                    brok.setBackground(new Color(42, 42, 42));
                    brok.setForeground(new Color(129, 129, 129));
                    bmiesiac.setBackground(new Color(42, 42, 42));
                    bmiesiac.setForeground(new Color(129, 129, 129));
                    Jwyborprawyczas.setBackground(new Color(42, 42, 42));
                    Jwyborlewyczas.setBackground(new Color(42, 42, 42));
                    List<String> odp = new ArrayList<>();
                    if(wybor.equals("albums")) {
                        try {
                            odp = topalbumpolroku.getTopAlbums(token);
                        } catch (IOException g) {
                            g.printStackTrace();
                        }
                        setPanelsVisibility(false, records1, records4, records6, albums4, albums1, artist1, artist4, artist6);
                        albums6.removeAll();
                        albums6.setVisible(true);
                        ScrollableJFrame.createAndShowMainFrame(odp, albums6, token,Jprawa,Jpic,nazwautw,nazwaart,opisnuty,red, opisnutykat, Jstats);
                    }
                    if(wybor.equals("songs")) {
                        try {
                            odp = toptrackpolroku.getTopTracks(token);
                        } catch (IOException g) {
                            g.printStackTrace();
                        }
                        setPanelsVisibility(false, records4, records1, albums6,albums4 , albums1, artist1, artist4, artist6);
                        records6.removeAll();
                        records6.setVisible(true);
                        ScrollableJFrame.createAndShowMainFrame(odp, records6, token,Jprawa,Jpic,nazwautw,nazwaart,opisnuty,red, opisnutykat, Jstats);
                    }
                    if(wybor.equals("artist")) {
                        try {
                            odp = topartistspolroku.getTopArtists(token);
                        } catch (IOException g) {
                            g.printStackTrace();
                        }
                        setPanelsVisibility(false, records6, records4, albums6, albums4, records1, artist1, artist4, albums1);
                        artist6.removeAll();
                        artist6.setVisible(true);
                        ScrollableJFrame.createAndShowMainFrame(odp, artist6, token,Jprawa,Jpic,nazwautw,nazwaart,opisnuty,red, opisnutykat, Jstats);
                    }

                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(!wyborczas.equals("6 Months")){
                    bpolroku.setBackground(new Color(56, 56, 56));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!wyborczas.equals("6 Months")) {
                    bpolroku.setBackground(new Color(42, 42, 42));
                }
            }
        });
        lista.add(bpolroku);


        bmiesiac = new RoundedButton("  4 Weeks");
        bmiesiac.setHorizontalAlignment(SwingConstants.LEFT);
        bmiesiac.setForeground(new Color(129, 129, 129));
        bmiesiac.arcWidth= 35;
        bmiesiac.arcHeight= 35;
        bmiesiac.setFont(fontxs);
        bmiesiac.setVisible(true);
        bmiesiac.setBounds(0,450,130,50);
        bmiesiac.setBackground(new Color(42, 42, 42));
        bmiesiac.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!wyborczas.equals("4 Weeks")){
                    kontenerstat.setVisible(false);
                    wyborczas = "4 Weeks";
                    bmiesiac.setBackground(new Color(0, 157, 59));
                    bmiesiac.setForeground(new Color(255, 255, 255));
                    brok.setBackground(new Color(42, 42, 42));
                    brok.setForeground(new Color(129, 129, 129));
                    bpolroku.setBackground(new Color(42, 42, 42));
                    bpolroku.setForeground(new Color(129, 129, 129));
                    Jwyborprawyczas.setBackground(new Color(42, 42, 42));
                    Jwyborlewyczas.setBackground(new Color(0, 157, 59));
                    List<String> odp = new ArrayList<>();
                    if(wybor.equals("albums")) {
                        try {
                            odp = topalbummiesiac.getTopAlbums(token);
                        } catch (IOException g) {
                            g.printStackTrace();
                        }
                        setPanelsVisibility(false, records1, records4, records6, albums6, albums1, artist1, artist4, artist6);
                        albums4.removeAll();
                        albums4.setVisible(true);
                        ScrollableJFrame.createAndShowMainFrame(odp, albums4, token,Jprawa,Jpic,nazwautw,nazwaart,opisnuty,red, opisnutykat, Jstats);
                    }
                    if(wybor.equals("songs")) {
                        try {
                            odp = toptrackmiesiac.getTopTracks(token);
                        } catch (IOException g) {
                            g.printStackTrace();
                        }
                        setPanelsVisibility(false, records6, records1, albums6,albums4 , albums1, artist1, artist4, artist6);
                        records4.removeAll();
                        records4.setVisible(true);
                        ScrollableJFrame.createAndShowMainFrame(odp, records4, token,Jprawa,Jpic,nazwautw,nazwaart,opisnuty,red, opisnutykat, Jstats);
                    }
                    if(wybor.equals("artist")) {
                        try {
                            odp = topartistsmiesiac.getTopArtists(token);
                        } catch (IOException g) {
                            g.printStackTrace();
                        }
                        setPanelsVisibility(false, records6, records4, albums6, albums4, records1, artist1, artist6, albums1);
                        artist4.removeAll();
                        artist4.setVisible(true);
                        ScrollableJFrame.createAndShowMainFrame(odp, artist4, token,Jprawa,Jpic,nazwautw,nazwaart,opisnuty,red, opisnutykat, Jstats);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(!wyborczas.equals("4 Weeks")){
                    bmiesiac.setBackground(new Color(56, 56, 56));
                    Jwyborlewyczas.setBackground(new Color(56, 56, 56));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!wyborczas.equals("4 Weeks")) {
                    bmiesiac.setBackground(new Color(42, 42, 42));
                    Jwyborlewyczas.setBackground(new Color(42, 42, 42));
                }
            }
        });
        lista.add(bmiesiac);

        brok = new RoundedButton("              Ever");
        brok.setHorizontalAlignment(SwingConstants.LEFT);
        brok.arcWidth= 35;
        brok.arcHeight= 35;
        brok.setForeground(new Color(129, 129, 129));
        brok.setFont(fontxs);
        brok.setVisible(true);
        brok.setBounds(140,450,130,50);
        brok.setBackground(new Color(42, 42, 42));
        brok.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!wyborczas.equals("1 Year")){
                    kontenerstat.setVisible(false);
                    wyborczas = "1 Year";
                    brok.setBackground(new Color(0, 157, 59));
                    brok.setForeground(new Color(255, 255, 255));
                    bpolroku.setBackground(new Color(42, 42, 42));
                    bpolroku.setForeground(new Color(129, 129, 129));
                    bmiesiac.setBackground(new Color(42, 42, 42));
                    bmiesiac.setForeground(new Color(129, 129, 129));
                    Jwyborprawyczas.setBackground(new Color(0, 157, 59));
                    Jwyborlewyczas.setBackground(new Color(42, 42, 42));
                    List<String> odp = new ArrayList<>();
                    if(wybor.equals("albums")) {
                        try {
                            odp = topalbumrok.getTopAlbums(token);
                        } catch (IOException g) {
                            g.printStackTrace();
                        }
                        setPanelsVisibility(false, records1, records4, records6, albums6, albums4, artist1, artist4, artist6);
                        albums1.removeAll();
                        albums1.setVisible(true);
                        ScrollableJFrame.createAndShowMainFrame(odp, albums1, token,Jprawa,Jpic,nazwautw,nazwaart,opisnuty,red, opisnutykat, Jstats);
                    }
                    if(wybor.equals("songs")) {
                        try {
                            odp = toptrackrok.getTopTracks(token);
                        } catch (IOException g) {
                            g.printStackTrace();
                        }
                        setPanelsVisibility(false, records6, records4, albums6,albums4 , albums1, artist1, artist4, artist6);
                        records1.removeAll();
                        records1.setVisible(true);
                        ScrollableJFrame.createAndShowMainFrame(odp, records1, token,Jprawa,Jpic,nazwautw,nazwaart,opisnuty,red, opisnutykat, Jstats);
                    }
                    if(wybor.equals("artist")) {
                        try {
                            odp = topartists.getTopArtists(token);
                        } catch (IOException g) {
                            g.printStackTrace();
                        }
                        setPanelsVisibility(false, records6, records4, albums6, albums4, records1, artist4, artist6, albums1);
                        artist1.removeAll();
                        artist1.setVisible(true);
                        ScrollableJFrame.createAndShowMainFrame(odp, artist1, token,Jprawa,Jpic,nazwautw,nazwaart,opisnuty,red, opisnutykat, Jstats);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(!wyborczas.equals("1 Year")){
                    brok.setBackground(new Color(56, 56, 56));
                    Jwyborprawyczas.setBackground(new Color(56, 56, 56));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!wyborczas.equals("1 Year")) {
                    brok.setBackground(new Color(42, 42, 42));
                    Jwyborprawyczas.setBackground(new Color(42, 42, 42));
                }
            }
        });
        lista.add(brok);

        Jwyborlewyczas = new JPanel();
        Jwyborlewyczas.setBounds(0,450,90,14);
        Jwyborlewyczas.setBackground(new Color(42, 42, 42));
        Jwyborlewyczas.setVisible(true);
        lista.add(Jwyborlewyczas);

        Jwyborprawyczas = new JPanel();
        Jwyborprawyczas.setBounds(200,450,69,14);
        Jwyborprawyczas.setBackground(new Color(42, 42, 42));
        Jwyborprawyczas.setVisible(true);
        lista.add(Jwyborprawyczas);

        records6 = new JPanel();
        records6.setBounds(-1,48,270,400);
        records6.setBackground(new Color(0, 0, 0));
        records6.setVisible(false);
        lista.add(records6);

        records1 = new JPanel();
        records1.setBounds(-1,48,270,400);
        records1.setBackground(new Color(0, 0, 0));
        records1.setVisible(false);
        lista.add(records1);

        records4 = new JPanel();
        records4.setBounds(-1,48,270,400);
        records4.setBackground(new Color(0, 0, 0));
        records4.setVisible(false);
        lista.add(records4);

        albums6 = new JPanel();
        albums6.setBounds(-1,48,270,400);
        albums6.setBackground(new Color(0, 0, 0));
        albums6.setVisible(false);
        lista.add(albums6);

        albums4 = new JPanel();
        albums4.setBounds(-1,48,270,400);
        albums4.setBackground(new Color(0, 0, 0));
        albums4.setVisible(false);
        lista.add(albums4);

        albums1 = new JPanel();
        albums1.setBounds(-1,48,270,400);
        albums1.setBackground(new Color(0, 0, 0));
        albums1.setVisible(false);
        lista.add(albums1);

        artist1 = new JPanel();
        artist1.setBounds(-1,48,270,400);
        artist1.setBackground(new Color(0, 0, 0));
        artist1.setVisible(false);
        lista.add(artist1);

        artist4 = new JPanel();
        artist4.setBounds(-1,48,270,400);
        artist4.setBackground(new Color(0, 0, 0));
        artist4.setVisible(false);
        lista.add(artist4);

        artist6 = new JPanel();
        artist6.setBounds(-1,48,270,400);
        artist6.setBackground(new Color(0, 0, 0));
        artist6.setVisible(false);
        lista.add(artist6);
        //------------------------------- INTERFACE ----------------------------------------//

        //------------------------------- LOGOWANIE RECZNE ----------------------------------------//
        RoundedPanel loadrecznie = new RoundedPanel(0,0,800,500);
        loadrecznie.setBackground(new Color(0,0,0));
        loadrecznie.setVisible(false);

        RoundedPanel zaczekaj = new RoundedPanel(0,0,800,500);
        zaczekaj.setVisible(false);
        zaczekaj.setLayout(null);
        zaczekaj.setBackground(new Color(0,0,0));
        logowanie.add(zaczekaj);

        ImageIcon gifIcon2 = new ImageIcon("giphy.gif");
        JLabel gifLabel2 = new JLabel(gifIcon2);
        gifLabel2.setBounds(350,120,100,100);
        gifLabel2.setVisible(true);
        zaczekaj.add(gifLabel2);

        JLabel zaczekajlabel = new JLabel("<html><center> Super udało nam się odnaleść twoje konto!<br> Już za sekundkę będziesz mógł zobaczyć swoje statystyki.</center></html>");
        zaczekajlabel.setFont(fontm);
        zaczekajlabel.setForeground(new Color(29, 185, 84));
        zaczekajlabel.setBounds(200,230,400,70);
        zaczekajlabel.setVisible(true);
        zaczekaj.add(zaczekajlabel);



        ImageIcon krokipath = new ImageIcon("kroki.png");
        JLabel kroki = new JLabel(krokipath);
        kroki.setBounds(0,0,300,420);
        kroki.setVisible(true);
        loadrecznie.add(kroki);

        JLabel Lkrok1 = new JLabel("Przejdz do strony");
        Lkrok1.setFont(fontm);
        Lkrok1.setForeground(new Color(29, 185, 84));
        Lkrok1.setBounds(200,45,400,30);
        Lkrok1.setVisible(true);
        loadrecznie.add(Lkrok1);



        JLabel Lkrok1link = new JLabel("<html><u>" + "https://developer.spotify.com" + "</u></html>");
        Lkrok1link.setFont(fonts);
        Lkrok1link.setForeground(new Color(0, 171, 255));
        Lkrok1link.setBounds(200,65,230,30);
        Lkrok1link.setVisible(true);
        Lkrok1link.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://developer.spotify.com"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Lkrok1link.setForeground(new Color(0, 119, 198));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Lkrok1link.setForeground(new Color(0, 171, 255));
            }
        });
        loadrecznie.add(Lkrok1link);

        JLabel Lkrok2 = new JLabel("Zaloguj się do swojego konta Spotify.");
        Lkrok2.setFont(fontm);
        Lkrok2.setForeground(new Color(29, 185, 84));
        Lkrok2.setBounds(200,136,400,30);
        Lkrok2.setVisible(true);
        loadrecznie.add(Lkrok2);

        JLabel Lkrok2opis = new JLabel("<html>Jeśli nie masz konta Spotify<br> możesz je stworzyć w tym kroku.</html>");
        Lkrok2opis.setFont(fonts);
        Lkrok2opis.setForeground(new Color(31, 31, 31));
        Lkrok2opis.setBounds(200,165,400,40);
        Lkrok2opis.setVisible(true);
        loadrecznie.add(Lkrok2opis);

        JLabel Lkrok3 = new JLabel("<html>Przejdz na dół strony i nacisni <u>Run code</u>, wyrażając wszystkie niezbędne zgody.</html>");
        Lkrok3.setFont(fontm);
        Lkrok3.setForeground(new Color(29, 185, 84));
        Lkrok3.setBounds(200,220,400,80);
        Lkrok3.setVisible(true);
        loadrecznie.add(Lkrok3);

        JLabel Lkrok4 = new JLabel("<html>Skopiuj drugą linijke kodu z lewej strony okna " +
                "zaczynajacą się \"<span style='color: rgb(0, 171, 255); display: inline;'>const </span>" +
                "<span style='color: rgb(255,255,255); display: inline;'>token</span>\"</html>");
        Lkrok4.setFont(fontm);
        Lkrok4.setForeground(new Color(29, 185, 84));
        Lkrok4.setBounds(200,316,400,80);
        Lkrok4.setVisible(true);
        loadrecznie.add(Lkrok4);

        JLabel Lkrokuwaga = new JLabel("<html><u>Przekopiowana błędna linijka</u></html>");
        Lkrokuwaga.setFont(fonts);
        Lkrokuwaga.setForeground(new Color(255, 255, 255));
        Lkrokuwaga.setBounds(250,400,560,20);
        Lkrokuwaga.setVisible(false);
        loadrecznie.add(Lkrokuwaga);

        RoundedPanel Pwklejtoken = new RoundedPanel(120,420,560,50);
        Pwklejtoken.setVisible(true);
        Pwklejtoken.setBackground(new Color(255,255,255));
        loadrecznie.add(Pwklejtoken);
        Pwklejtoken.arcHeight=50;
        Pwklejtoken.arcWidth=50;

        JTextField JToken = new JTextField();
        JToken.setBounds(30,0,390,49);
        JToken.setBorder(BorderFactory.createEmptyBorder());
        JToken.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    showPopup(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        Pwklejtoken.add(JToken);

        RoundedButton Btoken = new RoundedButton("Zatwierdz");
        Btoken.setBounds(420,0,140,50);
        Btoken.setFont(fonts);
        Btoken.setForeground(new Color(255,255,255));
        Btoken.setBackground(new Color(0, 157, 59));
        Btoken.setVisible(true);
        Btoken.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Btoken.setBackground(new Color(29, 185, 84));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Btoken.setBackground(new Color(0, 157, 59));
            }
            public void mousePressed(MouseEvent e) {
                String var = czyszczenie_lini(JToken.getText());
                try {
                    if (sprawdzenie.check(var) == 0) {
                        Lkrokuwaga.setVisible(true);
                    }
                    if (sprawdzenie.check(var) == 1) {
                        JToken.setVisible(false);
                        Btoken.setVisible(false);
                        zaczekaj.setVisible(true);
                        token = var;

                        new Thread(() -> {
                            proba.panelstats(token, Jstats);

                            SwingUtilities.invokeLater(() -> {
                                logowanie.setVisible(false);
                                Jinterface.setVisible(true);
                            });
                        }).start();
                    }
                }catch (IOException g){}
            }
        });
        Pwklejtoken.add(Btoken);



        //------------------------------- LOGOWANIE RECZNE KONIEC ----------------------------------------//

        //------------------------------- LOGOWANIE ----------------------------------------//
        RoundedPanel loading = new RoundedPanel(0,0,800,500);
        loading.setBackground(new Color(0,0,0));
        loading.setVisible(true);

        ImageIcon gifIcon = new ImageIcon("giphy.gif");
        JLabel gifLabel = new JLabel(gifIcon);
        gifLabel.setBounds(350,120,100,100);
        gifLabel.setVisible(true);
        loading.add(gifLabel);

        JLabel Llod = new JLabel("Dokończ weryfikacje w oknie przeglądarki");
        Llod.setFont(font);
        Llod.setForeground(new Color(29, 185, 84));
        Llod.setBounds(200,220,400,30);
        loading.add(Llod);

        RoundedButton Bloading = new RoundedButton("Coś poszło nie tak?");
        Bloading.setBounds(300,270,200,50);
        Bloading.setFont(fonts);
        Bloading.setForeground(new Color(255,255,255));
        Bloading.setBackground(new Color(0, 157, 59));
        Bloading.setVisible(false);
        Bloading.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Bloading.setBackground(new Color(29, 185, 84));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Bloading.setBackground(new Color(0, 157, 59));
            }
            public void mousePressed(MouseEvent e) {
                loading.setVisible(false);
                loadrecznie.setVisible(true);
            }
        });
        loading.add(Bloading);
        logowanie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if( inwindow == 1){
                    Bloading.setVisible(true);
                }
                int g = 0;
                String tokencheck;
                if(symulacjaprawidlowegopobraniatokenu==1){
                    tokencheck = tokenfake;
                } else {
                    tokencheck = Main.TOKENnew;
                }
                try{
                    g = sprawdzenie.check(tokencheck);
                } catch (IOException c){
                }
                if(  g == 1){
                    Bloading.setVisible(true);
                    token = tokencheck;

                    JToken.setVisible(false);
                    Btoken.setVisible(false);
                    zaczekaj.setVisible(true);

                    new Thread(() -> {
                        proba.panelstats(token, Jstats);

                        SwingUtilities.invokeLater(() -> {
                            logowanie.setVisible(false);
                            Jinterface.setVisible(true);
                        });
                    }).start();

                }else if(blad_log<=2){
                    if(tokencheck.length()>100){
                        System.out.println("Pobrano nie poprawny token " + tokencheck.substring(0,5) + "....." + tokencheck.substring(tokencheck.length()-3,tokencheck.length()));
                        symulacjaprawidlowegopobraniatokenu = 1;
                    } else {
                        //System.out.println("Nie pobrano tokena");
                    }
                    blad_log = 1 + blad_log;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                inwindow = 1;
            }
            public void mousePressed(MouseEvent e) {

            }
        });

        kontener.add(logowanie);
        logowanie.add(loading);
        logowanie.add(loadrecznie);
        //------------------------------- LOGOWANIE KONIEC ----------------------------------------//


        //------------------------------- MENU ----------------------------------------//
        RoundedPanel FirstPage = new RoundedPanel(93,30,800,500);
        FirstPage.setBackground(new Color(0,0,0));
        FirstPage.setVisible(true);
        kontener.add(FirstPage);

        ImageIcon imageIcon = new ImageIcon("menustart.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setVisible(true);
        imageLabel.setBounds(6,15,561,480);
        FirstPage.add(imageLabel);

        /*ImageIcon imageIcon2 = new ImageIcon("menustart2.png");
        JLabel imageLabel2 = new JLabel(imageIcon2);
        imageLabel2.setVisible(true);
        imageLabel2.setBounds(575,263,225,230);
        FirstPage.add(imageLabel2);*/

        JPanel kreska = new JPanel();
        kreska.setBounds(568,0,7,499);
        kreska.setBackground(new Color(31, 31, 31));
        kreska.setVisible(true);
        FirstPage.add(kreska);

        ImageIcon guzikrozmycieicon = new ImageIcon("guzikrozmycie.png");
        JLabel guzikrozmycie = new JLabel(guzikrozmycieicon);
        guzikrozmycie.setVisible(false);
        guzikrozmycie.setBounds(537,97,300,200);
        FirstPage.add(guzikrozmycie);


        RoundedButton BFirstPage = new RoundedButton("Log in");
        BFirstPage.setBounds(610,170,150,50);
        BFirstPage.setFont(font);
        BFirstPage.setForeground(new Color(255,255,255));
        BFirstPage.setBackground(new Color(29, 185, 84));
        BFirstPage.setVisible(true);
        BFirstPage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Zmiana koloru przy najechaniu
                BFirstPage.setBackground(new Color(36, 199, 90));
                guzikrozmycie.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Przywrócenie pierwotnego koloru po opuszczeniu
                BFirstPage.setBackground(new Color(29, 185, 84));
                guzikrozmycie.setVisible(false);
            }
            public void mousePressed(MouseEvent e) {
                // Przywrócenie pierwotnego koloru po opuszczeniu
                FirstPage.setVisible(false);
                logowanie.setVisible(true);
                //Jinterface.setVisible(true);
                Thread serwerthred = new Thread(() -> {
                    try {
                        Strona serwer = new Strona();
                        serwer.main();
                    } catch (IOException g) {
                        g.printStackTrace();
                    }
                });
                serwerthred.start();


            }
        });
        FirstPage.add(BFirstPage);
        FirstPage.setComponentZOrder(guzikrozmycie, 0);
        FirstPage.revalidate();
        FirstPage.repaint();
        //------------------------------- MENU KONIEC----------------------------------------//
    }
    private static void showPopup(Component component, int x, int y) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem cutItem = new JMenuItem(new AbstractAction("Wytnij") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextComponent textComponent = (JTextComponent) component;
                textComponent.cut();
            }
        });
        JMenuItem pasteItem = new JMenuItem(new AbstractAction("Wklej") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextComponent textComponent = (JTextComponent) component;
                textComponent.paste();
            }
        });
        popupMenu.add(cutItem);
        popupMenu.add(pasteItem);
        popupMenu.show(component, x, y);
    }
    String czyszczenie_lini(String var){
        Pattern pattern = Pattern.compile("const token = '([^']*)'");
        Matcher matcher = pattern.matcher(var);


        if (matcher.find()) {
            return matcher.group(1);
        } else {
            if(var.length() == 263){
                return var;
            }else{
                return null;
            }
        }
    }
    public String getToken(){
        return token;
    }
    private static void setPanelsVisibility(boolean isVisible, JPanel... panels) {
        for (JPanel panel : panels) {
            panel.setVisible(isVisible);
        }
    }
    private void handleCategoryClick(String category, RoundedButton clickedButton, Component backgroundChange1, Component backgroundChange2, Component backgroundChange3) {
        if (!wybor.equals(category)) {
            wybor = category;
            clickedButton.setBackground(new Color(0, 157, 59));
            backgroundChange1.setBackground(new Color(0, 157, 59));
            clickedButton.setForeground(new Color(255, 255, 255));
            backgroundChange2.setBackground(new Color(42, 42, 42));
            backgroundChange2.setForeground(new Color(129, 129, 129));
            backgroundChange3.setBackground(new Color(42, 42, 42));
            backgroundChange3.setForeground(new Color(129, 129, 129));

            // Use a background thread for heavy operations
            new Thread(() -> {
                List<String> odp = fetchData(category);
                SwingUtilities.invokeLater(() -> {
                    handlePanelVisibility(category, odp);
                });
            }).start();
        }
    }

    // Define a method to handle mouse enter
    private void handleMouseEnter(RoundedButton button, Component backgroundChange) {
        if (!wybor.equals(button.getText().trim().toLowerCase())) {
            button.setBackground(new Color(56, 56, 56));
            backgroundChange.setBackground(new Color(56, 56, 56));
        }
    }

    // Define a method to handle mouse exit
    private void handleMouseExit(RoundedButton button, Component backgroundChange) {
        if (!wybor.equals(button.getText().trim().toLowerCase())) {
            button.setBackground(new Color(42, 42, 42));
            backgroundChange.setBackground(new Color(42, 42, 42));
        }
    }

    // Define a method to fetch data in a background thread
    private List<String> fetchData(String category) {
        List<String> odp = new ArrayList<>();
        try {
            switch (wyborczas) {
                case "6 Months":
                    odp = ("songs".equals(category)) ? toptrackpolroku.getTopTracks(token) :
                            ("albums".equals(category)) ? topalbumpolroku.getTopAlbums(token) : odp;
                    break;
                case "4 Weeks":
                    odp = ("songs".equals(category)) ? toptrackmiesiac.getTopTracks(token) :
                            ("albums".equals(category)) ? topalbummiesiac.getTopAlbums(token) : odp;
                    break;
                case "1 Year":
                    odp = ("songs".equals(category)) ? toptrackrok.getTopTracks(token) :
                            ("albums".equals(category)) ? topalbumrok.getTopAlbums(token) : odp;
                    break;
            }
        } catch (IOException g) {
            g.printStackTrace();
        }
        return odp;
    }

    // Define a method to handle panel visibility
    private void handlePanelVisibility(String category, List<String> odp) {
        setPanelsVisibility("songs".equals(category), records6, records1, albums6, albums4, albums1, artist1, artist4, artist6);
        records6.removeAll();
        records6.setVisible(true);
        ScrollableJFrame.createAndShowMainFrame(odp, records6, token,Jprawa,Jpic,nazwautw,nazwaart,opisnuty,red, opisnutykat, Jstats);
    }
}

public class Main{
    static String TOKENnew = "";
    static String TOKEN;
    public static void main(String[] args){
        String TOKEN = "BQAuc3ngkWcxQOJC-0NopCIEJmwf1V_Bmy_Ja8IWBDAPm__Dp__hY9q_stn3mqTwMFzhckP4iLCjHizdr4Q-kV6IwqEATlsujzXWKPWi3NqHyOLE2WwVqxXCPX9MKVpnstNo-7jjftG_bOr0RxA_r-ymmAdS20ABhwTpPEPmg4DgAtFkBUAWZBqMopFwyxc2hykbMYHZ5s5HiM15-ALlSGUGc6lKxAcYcErUqnDY7-SRUazL5lekUzN1NmKa-Fwli9Lhu-U";

        //serwerthred.start();

        /*try {
            ///Thread.sleep(9000);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            // Reading data using readLine
            String name = reader.readLine();

            // Printing the read line
        } catch (IOException g){

        }
        System.out.println("token " + TOKENnew);*/
        List<String> odp = new ArrayList<>();
        try {
            //System.out.println(topartistspolroku.getTopArtists(TOKEN));
            System.out.println(toptrackpolroku.getTopTracks(TOKEN));
            //cleanview(toptrackrok.getTopTracks(TOKEN));
            //cleanview(topalbumrok.getTopAlbums(TOKEN));
            //cleanview(toptrackpolroku.getTopTracks(TOKEN));
            //odp = toptrackmiesiac.getTopTracks(TOKEN);
        } catch (IOException g) {
            g.printStackTrace();
        }
        //ScrollableJFrame.createAndShowMainFrame(odp);
        /*try{
            String name = "Lana del rey";
            artistinfo.ArtistInfo artistInfo = artistinfo.getArtistInfo(name, TOKEN);
            System.out.println(name + " " + Integer.toString(artistInfo.getPopularity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = "Runway";
        songsearch song = new songsearch();
        //List<String> wynik = new ArrayList<>(song.search(name, TOKEN));
        //cleanview(wynik);*/

        MyFrame aplikacja = new MyFrame();



    }

    static void cleanview(List<String> topold){
        List<String> top = new ArrayList<>(topold);
        if (top != null) {
            for (int i = 0; i < top.size(); i++) {
                if (top.get(i).contains("@/A")){
                    String[] parts = top.get(i).split("@/A");
                    System.out.println((i + 1) + ". " + parts[0]);
                }else{
                    System.out.println((i + 1) + ". " + top.get(i));
                }

            }
        } else {
            System.out.println("Failed to retrieve top tracks.");
        }

    }
}
