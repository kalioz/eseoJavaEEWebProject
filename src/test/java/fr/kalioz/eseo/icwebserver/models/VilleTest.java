package fr.kalioz.eseo.icwebserver.models;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VilleTest {

    public Ville generateVille() {
        return new Ville(new JSONObject("{'nom':'LAbergement-Clémenciat','code':'01001','codeDepartement':'01','codeRegion':'84','codesPostaux':['01400'],'population':767}"));
    }

    @BeforeEach
    public void beforeEach() {
        Ville.cleanTable();
    }

    @Test
    public void saveOrUpdate() {
        Ville ville = generateVille();
        ville.saveOrUpdate();
        //TODO add fetch
    }

    @Test
    public void getByName() {
        Ville ville = generateVille();
        ville.saveOrUpdate();

        List<Ville> output = Ville.getByName("Abergement", 5);
        assertTrue(output.size() >= 1);
    }

    @Test
    public void getAll() {
    }

    @Test
    public void toJson() {
        System.out.println(generateVille().toJson());
    }
}