package ui;

import db.FitnessUsageDAO;
import export.JsonExporter;
import export.PdfExporter;
import model.FitnessUsage;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * Hauptfenster der Anwendung.
 *
 * Zeigt beim Start ein Diagramm der Fitness-Daten und bietet zwei
 * Buttons: Export des Diagramms als PDF und Export der Daten als JSON.
 * Beide Dateien landen im Ordner "output".
 */
public class MainWindow extends JFrame {

    private static final String OUTPUT_DIR = "output";

    private final FitnessUsageDAO dao = new FitnessUsageDAO();
    private final JsonExporter jsonExporter = new JsonExporter();
    private final PdfExporter  pdfExporter  = new PdfExporter();

    private final List<FitnessUsage> data;
    private final JFreeChart chart;

    public MainWindow() {
        setTitle("AI Fitness App Usage - Dashboard");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Daten laden und Diagramm bauen
        data  = dao.findAll();
        chart = FitnessChartFactory.createCaloriesByWorkoutChart(data);

        add(buildHeader(),  BorderLayout.NORTH);
        add(new ChartPanel(chart), BorderLayout.CENTER);
        add(buildButtons(), BorderLayout.SOUTH);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        header.setBackground(Color.WHITE);

        JLabel title = new JLabel("AI Fitness App Usage");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(title);

        JLabel subtitle = new JLabel("(" + data.size() + " records loaded)");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(Color.GRAY);
        header.add(subtitle);

        return header;
    }

    private JPanel buildButtons() {
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JButton btnPdf  = new JButton("Export Chart as PDF");
        JButton btnJson = new JButton("Export Data as JSON");

        btnPdf.addActionListener(e -> onExportPdf());
        btnJson.addActionListener(e -> onExportJson());

        buttons.add(btnPdf);
        buttons.add(btnJson);
        return buttons;
    }

    /** Exportiert das Diagramm als PDF in den output-Ordner. */
    private void onExportPdf() {
        try {
            File file = outputFile("chart.pdf");
            pdfExporter.export(chart, file);
            showInfo("Chart exported to:\n" + file.getAbsolutePath());
        } catch (Exception ex) {
            showError("PDF export failed: " + ex.getMessage());
        }
    }

    /** Exportiert die Daten als JSON in den output-Ordner. */
    private void onExportJson() {
        try {
            File file = outputFile("fitness_data.json");
            jsonExporter.export(data, file);
            showInfo("Data exported to:\n" + file.getAbsolutePath());
        } catch (Exception ex) {
            showError("JSON export failed: " + ex.getMessage());
        }
    }

    /** Liefert eine Datei im output-Ordner und legt den Ordner bei Bedarf an. */
    private File outputFile(String name) {
        File dir = new File(OUTPUT_DIR);
        if (!dir.exists()) dir.mkdirs();
        return new File(dir, name);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Export successful",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}

