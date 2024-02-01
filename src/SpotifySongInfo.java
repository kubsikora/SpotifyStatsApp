import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class SpotifySongInfo {

    private static final String SPOTIFY_API_URL = "https://api.spotify.com/v1/tracks/";

    public static List<String> getSongInfo(String songId, String ACCESS_TOKEN) {
        try {
            URL url = new URL(SPOTIFY_API_URL + songId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                return extractSongInfo(jsonResponse);
            } else {
                List<String> L2 = new ArrayList<>();
                return L2;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static List<String> extractSongInfo(JSONObject jsonResponse) {
        List<String> L = new ArrayList<>();

        String imageUrl = jsonResponse.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url");
        int popularity = jsonResponse.getInt("popularity");
        String name = jsonResponse.getString("name");
        String artist = jsonResponse.getJSONArray("artists").getJSONObject(0).getString("name");
        String durationMs = Integer.toString(jsonResponse.getInt("duration_ms"));
        String album = jsonResponse.getJSONObject("album").getString("name");
        String releaseDate = jsonResponse.getJSONObject("album").getString("release_date");

        L.add(name);
        L.add(artist);
        L.add(durationMs);
        L.add(album);
        L.add(Integer.toString(popularity));
        L.add(releaseDate);
        L.add(imageUrl);


        return L;
    }
    private String imageUrl;
    private int popularity;
    private String name;
    private String artist;
    private String durationMs;
    private String album;
    public void SongInfo(String imageUrl, int popularity, String name, String artist, String durationMs, String album) {
        this.imageUrl = imageUrl;
        this.popularity = popularity;
        this.name = name;
        this.artist = artist;
        this.durationMs = durationMs;
        this.album = album;
    }
}