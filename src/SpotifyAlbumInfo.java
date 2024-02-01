import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SpotifyAlbumInfo {

    private static final String SPOTIFY_API_URL = "https://api.spotify.com/v1/albums/";

    public static List<String> getAlbumInfo(String albumId, String ACCESS_TOKEN) {
        try {
            URL url = new URL(SPOTIFY_API_URL + albumId);
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
                return extractAlbumInfo(jsonResponse);
            } else {
                List<String> L = new ArrayList<>();
                return L;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static List<String> extractAlbumInfo(JSONObject jsonResponse) {
        List<String> albumInfo = new ArrayList<>();

        String artist = jsonResponse.getJSONArray("artists").getJSONObject(0).getString("name");
        String imageUrl = jsonResponse.getJSONArray("images").getJSONObject(0).getString("url");
        int popularity = jsonResponse.getInt("popularity");
        String name = jsonResponse.getString("name");
        String releaseDate = jsonResponse.getString("release_date");
        String totalTracks = Integer.toString(jsonResponse.getInt("total_tracks"));

        albumInfo.add(artist);
        albumInfo.add(imageUrl);
        albumInfo.add(Integer.toString(popularity));
        albumInfo.add(name);
        albumInfo.add(releaseDate);
        albumInfo.add(totalTracks);

        JSONArray tracksArray = jsonResponse.getJSONObject("tracks").getJSONArray("items");
        for (int i = 0; i < tracksArray.length(); i++) {
            JSONObject trackObject = tracksArray.getJSONObject(i);
            String trackName = trackObject.getString("name");
            albumInfo.add(trackName);
        }

        return albumInfo;
    }
}
