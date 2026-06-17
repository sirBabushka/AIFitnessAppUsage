package ui;

import model.FitnessUsage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Erstellt das Diagramm aus den Fitness-Daten.
 *
 * Gewaehlte Auswertung: durchschnittlich verbrannte Kalorien je Workout-Typ.
 * Die Daten werden also nach WorkoutType gruppiert und der Mittelwert
 * der CaloriesBurned je Gruppe berechnet.
 */
public class FitnessChartFactory {

    private FitnessChartFactory() {
        // Utility-Klasse - keine Instanzen
    }

    /**
     * Baut ein Balkendiagramm: durchschnittliche Kalorien pro Workout-Typ.
     */
    public static JFreeChart createCaloriesByWorkoutChart(List<FitnessUsage> data) {
        DefaultCategoryDataset dataset = buildDataset(data);

        return ChartFactory.createBarChart(
                "Average Calories Burned per Workout Type",  // Titel
                "Workout Type",                              // X-Achse
                "Average Calories Burned",                   // Y-Achse
                dataset,
                PlotOrientation.VERTICAL,
                false,   // keine Legende noetig
                true,    // Tooltips
                false);  // keine URLs
    }

    /** Gruppiert nach WorkoutType und berechnet den Durchschnitt der Kalorien. */
    private static DefaultCategoryDataset buildDataset(List<FitnessUsage> data) {
        // Summe und Anzahl je Workout-Typ sammeln
        Map<String, int[]> stats = new LinkedHashMap<>();   // [0]=Summe, [1]=Anzahl

        for (FitnessUsage u : data) {
            String type = (u.getWorkoutType() != null) ? u.getWorkoutType() : "Unknown";
            int calories = (u.getCaloriesBurned() != null) ? u.getCaloriesBurned() : 0;

            stats.putIfAbsent(type, new int[]{0, 0});
            stats.get(type)[0] += calories;
            stats.get(type)[1] += 1;
        }

        // Durchschnitt berechnen und ins Dataset legen
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, int[]> entry : stats.entrySet()) {
            int sum   = entry.getValue()[0];
            int count = entry.getValue()[1];
            double average = (count > 0) ? (double) sum / count : 0;
            dataset.addValue(average, "Calories", entry.getKey());
        }
        return dataset;
    }
}
