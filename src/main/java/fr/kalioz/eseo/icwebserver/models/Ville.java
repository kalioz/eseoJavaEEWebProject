package fr.kalioz.eseo.icwebserver.models;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "VILLES")
public class Ville {

    @Id
    public String code;// == id

    private String nom;
    private String codeDepartement;// == id departement
    private String codeRegion;// == id region
    private int population;

    @ElementCollection
    private List<String> codesPostaux;

    public Ville() {
    }

    public Ville(JSONObject ville) {
        nom = ville.getString("nom");
        //TODO maybe just parse as string and then int ?
        code = ville.getString("code");
        codeDepartement = ville.getString("codeDepartement");
        codeRegion = ville.getString("codeRegion");
        population = ville.getInt("population");

        codesPostaux = new ArrayList<String>() {
        };
        JSONArray cp = ville.getJSONArray("codesPostaux");
        for (int i = 0; i < cp.length(); i++) {
            codesPostaux.add(cp.getString(i));
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
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM " + Ville.class.getName());
        query.executeUpdate();
        session.getTransaction().commit();
    }
}
