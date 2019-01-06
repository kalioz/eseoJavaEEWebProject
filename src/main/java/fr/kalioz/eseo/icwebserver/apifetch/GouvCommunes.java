package fr.kalioz.eseo.icwebserver.apifetch;

import fr.kalioz.eseo.icwebserver.models.Ville;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GouvCommunes {

    private GouvCommunes() {
    }

    private static Logger logger = Logger.getLogger(GouvCommunes.class.getName());

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
            logger.fine("error - len(features) != 1;");
            return null;
        }
        JSONObject city = features.getJSONObject(0);
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
            return output;
        }
        JSONObject jsonOutput = new JSONObject(tmp);
        JSONArray features = jsonOutput.getJSONArray("features");

        for (int i = 0; i < features.length(); i++) {
            output.add(new Ville(features.getJSONObject(i)));
        }

        return output;
    }

    public static void fetchAndSaveDepartement(int id) {
        fetchAndSaveDepartement(String.format("%02d", id));
    }


    public static void fetchAndSaveDepartement(String id) {
        List<Ville> villes = getCommuneByDepartement(id);

        for (Ville v : villes) {
            v.saveOrUpdate();
        }
    }

    public static void fetchAndSaveAllDepartements() {
        for (int i = 1; i < 83 + 1; i++) {
            logger.log(Level.FINE, "fetching departement {0}", i);
            System.out.println("departement #" + i);
            if (i == 20) {//corse
                fetchAndSaveDepartement("2A");
                fetchAndSaveDepartement("2B");
                continue;
            }
            fetchAndSaveDepartement(i);
        }
    }

    public static void exportInSQL(String fileName) {
        List<Ville> villes = Ville.getAll();
        try (FileWriter fileWriter = new FileWriter(fileName); PrintWriter printWriter = new PrintWriter(fileWriter)) {
            for (Ville v : villes) {
                System.out.println("writing " + v.getNom());
                printWriter.printf("('%s','%s','%s','%s','%s','%s','%s'),\n",
                        v.getCode(),
                        v.getNom().replace("'", " "),
                        v.getMainCodePostal(),
                        v.getNom().replace("'", " "),
                        "",
                        v.getLatitude(),
                        v.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
