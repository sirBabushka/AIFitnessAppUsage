package db;

import model.FitnessUsage;
import org.hibernate.Session;

import java.util.List;

/**
 * Datenzugriff fuer die Tabelle AIFitnessAppUsage ueber Hibernate.
 * Die Anwendung liest nur Daten, daher gibt es nur eine Lese-Methode.
 */
public class FitnessUsageDAO {

    /** Liefert alle Datensaetze aus der Tabelle. */
    public List<FitnessUsage> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM FitnessUsage", FitnessUsage.class).list();
        }
    }
}
