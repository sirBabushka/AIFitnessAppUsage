package db;

import model.FitnessUsage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Stellt die zentrale Hibernate-SessionFactory bereit.
 *
 * Die SessionFactory ist ein schwergewichtiges Objekt und wird daher
 * nur EINMAL erzeugt und im gesamten Programm wiederverwendet.
 * Aus ihr oeffnet das DAO bei Bedarf kurzlebige Sessions.
 *
 * Die Konfiguration (DB-Verbindung) steht in hibernate.cfg.xml,
 * die Entity-Klasse wird hier registriert.
 */
public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private HibernateUtil() {
        // Utility-Klasse - keine Instanzen
    }

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(FitnessUsage.class)
                    .buildSessionFactory();
        } catch (RuntimeException e) {
            throw new IllegalStateException(
                    "Hibernate konnte nicht initialisiert werden. "
                  + "Bitte hibernate.cfg.xml (DB-Verbindung) pruefen.", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    /** Schliesst die SessionFactory (beim Programm-Ende). */
    public static void shutdown() {
        SESSION_FACTORY.close();
    }
}
