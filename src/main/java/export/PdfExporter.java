package export;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.JFreeChart;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Exportiert ein JFreeChart-Diagramm als PDF-Datei.
 * Verwendet Apache PDFBox.
 *
 * Ablauf: das Diagramm wird zuerst in ein Bild gerendert,
 * dieses Bild wird dann auf eine PDF-Seite gezeichnet.
 */
public class PdfExporter {

    private static final int CHART_WIDTH  = 800;
    private static final int CHART_HEIGHT = 600;

    /**
     * Schreibt das Diagramm als PDF in die angegebene Datei.
     *
     * @param chart      das zu exportierende Diagramm
     * @param outputFile Zieldatei (im output-Ordner)
     */
    public void export(JFreeChart chart, File outputFile) throws IOException {
        // 1. Diagramm in ein Bild rendern
        BufferedImage chartImage = chart.createBufferedImage(CHART_WIDTH, CHART_HEIGHT);

        // 2. PDF-Dokument mit einer Seite erstellen
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(new PDRectangle(CHART_WIDTH, CHART_HEIGHT));
            document.addPage(page);

            // 3. Bild ins PDF einbetten und auf die Seite zeichnen
            PDImageXObject pdfImage = LosslessFactory.createFromImage(document, chartImage);
            try (PDPageContentStream content = new PDPageContentStream(document, page)) {
                content.drawImage(pdfImage, 0, 0, CHART_WIDTH, CHART_HEIGHT);
            }

            // 4. Datei speichern
            document.save(outputFile);
        }
    }
}
