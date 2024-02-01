import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class SpotifySongYear{


    public static List<List<String>> getYearlyStatistics(String TOKEN) throws IOException {
        List<String> allTracks = new ArrayList<>();


        allTracks.addAll(getTopTracks(TOKEN, "long_term", 50));

        allTracks.addAll(getTopTracks(TOKEN, "medium_term", 50));

        // Przetwarzanie informacji o rokach powstania piosenek
        Map<Integer, Integer> yearlyCounts = new HashMap<>();
        for (String track : allTracks) {
            int year = getTrackYear(TOKEN, track);
            yearlyCounts.put(year, yearlyCounts.getOrDefault(year, 0) + 1);
        }

        // Sortowanie wyników według ilości piosenek w danym roku
        List<Map.Entry<Integer, Integer>> sortedList = yearlyCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        // Tworzenie wynikowej listy do zwrócenia
        List<List<String>> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : sortedList) {
            result.add(Arrays.asList(entry.getKey().toString(), entry.getValue().toString()));
        }

        return result;
    }

    private static List<String> getTopTracks(String TOKEN, String timeRange, int limit) throws IOException {
        String apiUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=" + timeRange + "&limit=" + limit;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

        List<String> tracks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String jsonResponse = reader.lines().collect(Collectors.joining());
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray items = json.optJSONArray("items");

            if (items != null) {
                tracks = items.toList().stream()
                        .map(item -> {
                            JSONObject track = new JSONObject((Map<String, Object>) item);
                            return track.getString("id");
                        })
                        .collect(Collectors.toList());
            }
        }

        return tracks;
    }

    private static int getTrackYear(String TOKEN, String trackId) throws IOException {
        String apiUrl = "https://api.spotify.com/v1/tracks/" + trackId;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String jsonResponse = reader.lines().collect(Collectors.joining());
            JSONObject json = new JSONObject(jsonResponse);

            JSONObject album = json.optJSONObject("album");
            if (album != null) {
                String releaseDate = album.optString("release_date", "");
                return !releaseDate.isEmpty() ? Integer.parseInt(releaseDate.split("-")[0]) : 0;
            } else {
                System.out.println("No album information found for the track.");
                return 0;
            }
        }
    }
}