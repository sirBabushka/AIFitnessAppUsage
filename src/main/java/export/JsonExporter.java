package export;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.FitnessUsage;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Exportiert die geladenen Datensaetze als JSON-Datei.
 * Verwendet die Jackson-Bibliothek.
 *
 * Die JSON-Datei dient als Austauschformat fuer benachbarte Systeme.
 */
public class JsonExporter {

    private final ObjectMapper mapper;

    public JsonExporter() {
        this.mapper = new ObjectMapper();
        // Modul fuer LocalDate (sonst kann Jackson das Datum nicht schreiben)
        mapper.registerModule(new JavaTimeModule());
        // Datum als "2026-01-15" statt als Zahlen-Array
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // Lesbar einrueckt formatieren
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * Schreibt alle Datensaetze als JSON in die angegebene Datei.
     *
     * @param data       die zu exportierenden Objekte
     * @param outputFile Zieldatei (im output-Ordner)
     */
    public void export(List<FitnessUsage> data, File outputFile) throws IOException {
        mapper.writeValue(outputFile, data);
    }
}
