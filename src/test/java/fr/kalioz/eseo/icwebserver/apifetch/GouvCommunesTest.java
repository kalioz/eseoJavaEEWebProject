package fr.kalioz.eseo.icwebserver.apifetch;

import fr.kalioz.eseo.icwebserver.models.Ville;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GouvCommunesTest {

    @BeforeEach
    public void beforeEach() {
        //Ville.cleanTable();
    }

    @Test
    public void getCommune() {
        Ville output = GouvCommunes.getCommune(41018);//Blois
        assertNotNull(output);
        assertEquals("Blois", output.getNom());
        assertEquals("41018", output.getCode());
        assertEquals("24", output.getCodeRegion());
        assertEquals("41", output.getCodeDepartement());
        assertEquals(1, output.getCodesPostaux().size());
        assertEquals("41000", output.getCodesPostaux().get(0));
    }

    @Test
    public void getCommuneByDepartement() {
        List<Ville> output = GouvCommunes.getCommuneByDepartement(1);
        assertEquals(407, output.size());
    }

    @Test
    public void fetchAndSaveDepartement() {
        assertEquals(0, Ville.getAll().size());
        GouvCommunes.fetchAndSaveDepartement(1);
        assertTrue(Ville.getAll().size() > 0);
    }
}