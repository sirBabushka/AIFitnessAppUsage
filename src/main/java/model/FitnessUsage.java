package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

/**
 * Entity fuer die Tabelle AIFitnessAppUsage.
 *
 * Jede Zeile der Tabelle entspricht einem Objekt dieser Klasse.
 * Die Spalten werden ueber @Column auf die Java-Felder abgebildet.
 * Lombok @Data erzeugt automatisch Getter, Setter, toString usw.
 */
@Entity
@Table(name = "AIFitnessAppUsage")
@Data
public class FitnessUsage {

    @Id
    @Column(name = "UsageID")
    private int usageId;

    @Column(name = "UserName")
    private String userName;

    @Column(name = "AgeGroup")
    private String ageGroup;

    @Column(name = "DeviceType")
    private String deviceType;

    @Column(name = "WorkoutType")
    private String workoutType;

    @Column(name = "AppFeature")
    private String appFeature;

    @Column(name = "UsageDate")
    private LocalDate usageDate;

    @Column(name = "DurationMinutes")
    private Integer durationMinutes;

    @Column(name = "CaloriesBurned")
    private Integer caloriesBurned;

    @Column(name = "SubscriptionType")
    private String subscriptionType;
}
