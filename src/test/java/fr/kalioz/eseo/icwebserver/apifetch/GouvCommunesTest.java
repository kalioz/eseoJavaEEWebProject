package fr.kalioz.eseo.icwebserver.apifetch;

import fr.kalioz.eseo.icwebserver.models.Ville;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GouvCommunesTest {

    @BeforeEach
    void beforeEach() {
        Ville.cleanTable();
    }

    @Test
    void getCommune() {
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
    void getCommuneByDepartement() {
        List<Ville> output = GouvCommunes.getCommuneByDepartement(1);
        assertEquals(407, output.size());
    }

    @Test
    void fetchAllDepartements() {
        assertEquals(0, Ville.getAll().size());
        GouvCommunes.fetchAndSaveAllDepartements();
        assertTrue(Ville.getAll().size() > 0);
    }
}