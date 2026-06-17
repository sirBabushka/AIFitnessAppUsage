import ui.MainWindow;

import javax.swing.*;

/**
 * Einstiegspunkt der Anwendung.
 *
 * INFO: bitte Compiler auf 25 geben!! es hat bei uns funktoniert
 *
 * Startet das Hauptfenster, das beim Laden die Daten aus der Datenbank
 * holt, ein Diagramm anzeigt und die beiden Export-Buttons bereitstellt.
 */
public class Main {

    public static void main(String[] args) {
        applySystemLookAndFeel();
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }

    /** Verwendet das native Aussehen des Betriebssystems. */
    private static void applySystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Faellt auf das Standard-Look-and-Feel zurueck - unkritisch
        }
    }
}