import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class SpotifyArtistInfo {

    public static List<String> getArtistInfo(String artistId, String TOKEN) throws IOException {
        List<String> artistInfo = new ArrayList<>();

        String apiUrl = "https://api.spotify.com/v1/artists/" + artistId;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String jsonResponse = reader.lines().collect(Collectors.joining());
                List<String> info = extractArtistInfo(jsonResponse, TOKEN);
                if (info != null) {
                    artistInfo.addAll(info);
                }
            }
        } else {
            System.out.println("Error Response Code: " + responseCode);

            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                String errorResponse = errorReader.lines().collect(Collectors.joining());
                System.out.println("Error Response: " + errorResponse);
            }

            throw new IOException("HTTP error code: " + responseCode);
        }

        return artistInfo;
    }

    private static List<String> extractArtistInfo(String jsonResponse, String TOKEN) {
        try {
            JSONObject artistJson = new JSONObject(jsonResponse);
            String name = artistJson.getString("name");
            String id = artistJson.getString("id");
            String genre = extractGenre(artistJson);
            String imageUrl = extractImageUrl(artistJson);
            String popularity = Integer.toString(artistJson.getInt("popularity"));
            String followers = extractFollowers(artistJson);

            List<String> albumIds = extractAlbums(id,TOKEN);
            String albums = albumIds.stream().collect(Collectors.joining(", "));

            return List.of(name, genre, imageUrl, popularity, followers, albums);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String extractGenre(JSONObject artist) {
        JSONArray genresArray = artist.optJSONArray("genres");
        return genresArray != null ? genresArray.toList().stream().map(Object::toString).collect(Collectors.joining(", ")) : "";
    }

    private static String extractImageUrl(JSONObject artist) {
        JSONArray imagesArray = artist.optJSONArray("images");
        return imagesArray != null && imagesArray.length() > 0 ? imagesArray.getJSONObject(0).getString("url") : "";
    }

    private static String extractFollowers(JSONObject artist) {
        JSONObject followersObject = artist.optJSONObject("followers");
        return followersObject != null ? Integer.toString(followersObject.getInt("total")) : "";
    }

    private static List<String> extractAlbums(String artistId, String TOKEN) {
        List<String> albumIds = new ArrayList<>();

        try {
            String apiUrl = "https://api.spotify.com/v1/artists/" + artistId + "/albums?limit=10";

            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String jsonResponse = reader.lines().collect(Collectors.joining());
                    albumIds = extractAlbumIds(jsonResponse);
                }
            } else {
                System.out.println("Error Response Code: " + responseCode);

                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    String errorResponse = errorReader.lines().collect(Collectors.joining());
                    System.out.println("Error Response: " + errorResponse);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return albumIds;
    }

    private static List<String> extractAlbumIds(String jsonResponse) {
        List<String> albumIds = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray items = json.optJSONArray("items");

            if (items != null) {
                albumIds = items.toList().stream()
                        .map(item -> {
                            JSONObject album = new JSONObject((Map<String, Object>) item);
                            return album.getString("id");
                        })
                        .collect(Collectors.toList());
            } else {
                System.out.println("No items found in the response.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return albumIds;
    }
}