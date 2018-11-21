package fr.kalioz.eseo.icwebserver.apifetch;

import fr.kalioz.eseo.icwebserver.models.Ville;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GouvCommunes {
    public static Ville getCommune(int id) {
        Map<String, String> params = new HashMap<>();
        params.put("format", "geojson");
        params.put("code", String.valueOf(id));
        String output = EasyHttpClient.doRequest("/communes", params);
        if (output == null) {
            return null;
        }
        JSONObject jsonOutput = new JSONObject(output);
        JSONArray features = jsonOutput.getJSONArray("features");
        if (features.length() != 1) {
            System.out.println("error - len(features) != 1; features : " + features.toString());
            return null;
        }
        JSONObject city = features.getJSONObject(0).getJSONObject("properties");
        return new Ville(city);
    }

    public static List<Ville> getCommuneByDepartement(int id) {
        return getCommuneByDepartement(String.format("%02d", id));
    }

    public static List<Ville> getCommuneByDepartement(String id) {
        List<Ville> output = new ArrayList<>();

        Map<String, String> params = new HashMap<>();
        params.put("format", "geojson");
        String tmp = EasyHttpClient.doRequest("/departements/" + id + "/communes", params);
        if (tmp == null) {
            return null;
        }
        JSONObject jsonOutput = new JSONObject(tmp);
        JSONArray features = jsonOutput.getJSONArray("features");

        for (int i = 0; i < features.length(); i++) {
            output.add(new Ville(features.getJSONObject(0).getJSONObject("properties")));
        }

        return output;
    }

    public static void fetchAndSaveDepartement(int id) {
        fetchAndSaveDepartement(String.format("%02d", id));
    }


    public static void fetchAndSaveDepartement(String id) {
        List<Ville> villes = getCommuneByDepartement(id);
        if (villes == null) {
            return;
        }
        for (Ville v : villes) {
            v.saveOrUpdate();
        }
    }

    public static void fetchAndSaveAllDepartements() {
        for (int i = 1; i < 83 + 1; i++) {
            if (i == 20) {//corse
                fetchAndSaveDepartement("2A");
                fetchAndSaveDepartement("2B");
                continue;
            }
            fetchAndSaveDepartement(i);
        }
    }


}
