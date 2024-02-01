import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SpotifySimilarSongs {

    private static final String SPOTIFY_API_URL = "https://api.spotify.com/v1/recommendations";
    private static final String SPOTIFY_API_KEY = "BQCiKPfxX5Cps2x_zL6k9q0W7rb2DFDN7JHW9c_axYaCpBNzqkF5_tkK1x2v2s3J98ISxlkaV8lkJ_zvMRTkxQhH2J3LqhoCqJdqdmIs920M77coYwL19dIbNP0OaG_jT_MUgzqbcVb5xTunH9tSnnd9G4TcoRyPHgdwEj1jRZNr3ne7yJ0K6PvmoFkn8dAXPV3k8WM83-IIg53iQfHAFBg40HL80e-lS3RSOmeY3ExlkngcAN1LNaBwaCuzVN2VPdsUb_Y"; // Zastąp własnym kluczem API Spotify

    public static List<List<String>> getSimilarSongs(String songId, String SPOTIFY_API_KEY) {
        try {
            URL url = new URL(SPOTIFY_API_URL + "?seed_tracks=" + songId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + SPOTIFY_API_KEY);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return extractSimilarSongs(response.toString());
            } else {
                System.out.println("Error retrieving similar songs. Response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static List<List<String>> extractSimilarSongs(String jsonResponse) {
        List<List<String>> similarSongs = new ArrayList<>();

        JSONObject json = new JSONObject(jsonResponse);
        JSONArray tracks = json.getJSONArray("tracks");

        for (int i = 0; i < tracks.length(); i++) {
            JSONObject track = tracks.getJSONObject(i);

            String name = track.getString("name");
            String artist = track.getJSONArray("artists").getJSONObject(0).getString("name");
            String imageUrl = track.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url");

            List<String> songInfo = new ArrayList<>();
            songInfo.add(name);
            songInfo.add(artist);
            songInfo.add(imageUrl);

            similarSongs.add(songInfo);
        }

        return similarSongs;
    }
}