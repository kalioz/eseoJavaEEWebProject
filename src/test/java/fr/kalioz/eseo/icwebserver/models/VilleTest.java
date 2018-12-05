package fr.kalioz.eseo.icwebserver.models;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class VilleTest {

    private Ville generateVille() {
        return new Ville(new JSONObject("{\"type\":\"Feature\",\"properties\":{\"nom\":\"L'Abergement-Clémenciat\",\"code\":\"01001\",\"codeDepartement\":\"01\",\"codeRegion\":\"84\",\"codesPostaux\":[\"01400\"],\"population\":767},\"geometry\":{\"type\":\"Point\",\"coordinates\":[4.924699203187253,46.15678199203189]}}"));
    }

    @Before
    public void beforeEach() {
        //Ville.cleanTable();
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
        assertNotNull(generateVille().toJson());
    }
}