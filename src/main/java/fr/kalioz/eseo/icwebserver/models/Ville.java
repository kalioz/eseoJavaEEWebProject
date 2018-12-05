package fr.kalioz.eseo.icwebserver.models;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Entity
@Table(name = "VILLES")
public class Ville {

    private static final String GEOMETRY = "geometry";

    private static Logger logger = Logger.getLogger(Ville.class.getName());

    @Id
    public String code;// == id

    private String nom;
    private String codeDepartement;// == id departement
    private String codeRegion;// == id region
    private int population;

    private float longitude;//vertical
    private float latitude;//horizontal

    @ElementCollection
    private List<String> codesPostaux;

    public Ville() {
    }

    public Ville(JSONObject ville) {
        try {
            JSONObject properties = ville.getJSONObject("properties");
            nom = properties.getString("nom");
            code = properties.getString("code");
            codeDepartement = properties.getString("codeDepartement");
            codeRegion = properties.getString("codeRegion");
            if (ville.has("population")) {
                population = properties.getInt("population");
            }

            codesPostaux = new ArrayList<String>() {
            };
            JSONArray cp = properties.getJSONArray("codesPostaux");
            for (int i = 0; i < cp.length(); i++) {
                codesPostaux.add(cp.getString(i));
            }
            if (ville.has(GEOMETRY)) {
                longitude = ville.getJSONObject(GEOMETRY).getJSONArray("coordinates").getFloat(0);
                latitude = ville.getJSONObject(GEOMETRY).getJSONArray("coordinates").getFloat(1);
            } else {
                logger.fine("ERROR - no GEOMETRY param found in json");
            }
        } catch (JSONException e) {
            logger.finest("JSON couldn't be parsed - " + ville.toString() + " --- " + e.getMessage());
            throw e;
        }
    }

    public String getNom() {
        return nom;
    }

    public String getCode() {
        return code;
    }

    public String getCodeDepartement() {
        return codeDepartement;
    }

    public String getCodeRegion() {
        return codeRegion;
    }

    public int getPopulation() {
        return population;
    }

    public List<String> getCodesPostaux() {
        return codesPostaux;
    }

    public JSONObject toJson() {
        return new JSONObject(this);
    }

    @Override
    public String toString() {
        return "Ville{" +
                "nom='" + nom + '\'' +
                ", code=" + code +
                ", codeDepartement=" + codeDepartement +
                ", codeRegion=" + codeRegion +
                ", population=" + population +
                ", codesPostaux=" + codesPostaux +
                '}';
    }

    // --------------------------------------------- hibernate specific --------------------------------------------------------

    public void saveOrUpdate() {
        Session session = HibernateModel.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();
        session.saveOrUpdate(this);
        session.getTransaction().commit();
    }

    public static List<Ville> getByName(String name, int maxResults) {
        Session session = HibernateModel.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        String sql = "FROM " + Ville.class.getName() + " AS v WHERE v.nom LIKE ?1 ORDER BY v.code";

        Query<Ville> query = session.createQuery(sql);
        query.setParameter(1, "%" + name + "%");
        query.setMaxResults(maxResults);

        List<Ville> output = query.list();

        session.getTransaction().commit();

        return output;
    }

    public static List<Ville> getAll() {
        return getByName("", 10000000);
    }

    public static void cleanTable() {
        Session session = HibernateModel.getSessionFactory().getCurrentSession();
        Query query;

        session.getTransaction().begin();
        query = session.createQuery("DELETE FROM " + Ville.class.getName());
        query.executeUpdate();
        session.getTransaction().commit();
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }


}
