package ti2ex4;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class Main {
	
	private static final String ENDPOINT = "http://aadd55e6-e2a3-476b-b4e4-6309786b04ba.southcentralus.azurecontainer.io/score";
	private static final String API_KEY = "FVntFTpVMGm7VyrrE4lZHKgyBSxK8nGq";

	public static void main(String[] args) {
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ENDPOINT)).headers("Content-Type", "application/json", "Authorization", "Bearer " + API_KEY).POST(HttpRequest.BodyPublishers.ofString(sampleData())).build();
		
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			List<Map<String, Object>> classification = responseMapBody(response.body());
			
            System.out.println(classification);
		}catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static String sampleData() {
        JSONArray array = new JSONArray();
        JSONObject item = new JSONObject();
        JSONObject mainItem = new JSONObject();
        item.put("class", "");
        item.put("valence", 3.9394);
        item.put("energy", 1);
        item.put("liveness", 1);
        item.put("speechiness", 1);
        item.put("instrumentalness", 1);
        item.put("tempo", 150);
        item.put("danceability", 8.393);
        item.put("acousticness", 0.992);

        array.put(item);
        
        System.out.println(array.toString());

        return array.toString();
    }
	
	private static List<Map<String, Object>> responseMapBody(String body) {
        Map<String, Object> hm;
        List<Map<String, Object>> res = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(body);
        
        JSONArray objs = (JSONArray) jsonObject.get("result"); 

        for (Object _obj : objs) {
            hm = new HashMap<>();
            for (Object o : ((JSONObject) _obj).keySet()) {
                String key = (String) o;
                hm.put(key, ((JSONObject) _obj).get(key));
            }
            res.add(hm);
        }

        return res;
    }

}
