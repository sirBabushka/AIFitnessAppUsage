# Technical Documentation - AI Fitness App Usage

## Purpose of the Application

This Java Swing application reads fitness-usage data from a database table,
visualizes it as a chart, and lets the user export the chart as a PDF and the
data as a JSON file. When the application starts, it loads all records from the
database, builds a bar chart and displays it in a window together with two
export buttons.

## Technologies and Libraries Used

| Technology      | Purpose |
|-----------------|---------|
| Java + Swing    | Application and graphical user interface |
| Hibernate ORM   | Database access (object-relational mapping) |
| Lombok          | Generates getters/setters automatically (`@Data`) |
| Jackson         | Converts Java objects to JSON |
| JFreeChart      | Creates the chart |
| Apache PDFBox   | Exports the chart to PDF |
| Maven           | Build and dependency management |
| Git             | Version control |

## Structure of the Main Classes

The project is divided into clear layers:

```
src/main/java/
 ├─ Main.java                  Entry point, starts the main window
 ├─ model/
 │   └─ FitnessUsage.java      Entity (maps the database table)
 ├─ db/
 │   ├─ HibernateUtil.java     Provides the Hibernate SessionFactory
 │   └─ FitnessUsageDAO.java   Reads the data from the database
 ├─ ui/
 │   ├─ MainWindow.java        Window with chart and export buttons
 │   └─ FitnessChartFactory.java  Builds the chart from the data
 └─ export/
     ├─ JsonExporter.java      Exports the data as JSON (Jackson)
     └─ PdfExporter.java       Exports the chart as PDF (PDFBox)
```

- **UI layer** (`ui`) builds the window and reacts to button clicks.
- **Data layer** (`db`) handles all database access through Hibernate.
- **Model** (`model`) is the entity mapped to the table.
- **Export** (`export`) contains the JSON and PDF export logic.

The UI never talks to Hibernate directly; it goes through the DAO.

## Database Mapping

The table `AIFitnessAppUsage` is mapped to the entity class `FitnessUsage`
using Hibernate annotations:

- `@Entity` and `@Table(name = "AIFitnessAppUsage")` link the class to the table.
- `@Id` marks `usageId` as the primary key (column `UsageID`).
- `@Column(name = "...")` maps each Java field to its database column, e.g.
  `caloriesBurned` to `CaloriesBurned`.
- Lombok's `@Data` generates all getters and setters.

| Database column   | Java field        | Type      |
|-------------------|-------------------|-----------|
| UsageID (PK)      | usageId           | int       |
| UserName          | userName          | String    |
| AgeGroup          | ageGroup          | String    |
| DeviceType        | deviceType        | String    |
| WorkoutType       | workoutType       | String    |
| AppFeature        | appFeature        | String    |
| UsageDate         | usageDate         | LocalDate |
| DurationMinutes   | durationMinutes   | Integer   |
| CaloriesBurned    | caloriesBurned    | Integer   |
| SubscriptionType  | subscriptionType  | String    |

The connection settings are in `src/main/resources/hibernate.cfg.xml`.
`hibernate.hbm2ddl.auto` is set to `none`, so Hibernate never modifies the
table - the application only reads.

## Functionality of the Chart

The chart is built in `FitnessChartFactory`. The chosen analysis is the
**average calories burned per workout type**: the records are grouped by
`WorkoutType`, and for each group the average of `CaloriesBurned` is computed.
The result is shown as a bar chart (JFreeChart). The chart is placed in the
center of the window inside a `ChartPanel`.

## Functionality of the PDF Export

`PdfExporter` (using Apache PDFBox) exports the chart:

1. The chart is rendered into an image (`createBufferedImage`).
2. A PDF document with a single page is created.
3. The image is embedded and drawn onto the page.
4. The PDF is saved as `output/chart.pdf`.

## Functionality of the JSON Export

`JsonExporter` (using Jackson) converts the loaded Java objects into JSON:

- An `ObjectMapper` serializes the list of `FitnessUsage` objects.
- The `JavaTimeModule` is registered so `LocalDate` is written as a readable
  date (e.g. `2026-01-15`).
- Indented output makes the file human-readable.
- The result is saved as `output/fitness_data.json` and serves as an exchange
  format for neighboring systems.

## Instructions for Running the Application

1. Open the project in IntelliJ (Maven loads all dependencies automatically).
2. Enable the Lombok plugin and annotation processing
   (Settings > Build > Compiler > Annotation Processors).
3. Enter the database connection details in
   `src/main/resources/hibernate.cfg.xml`.
4. Run `Main.java`.
5. The window opens and shows the chart. Use the two buttons to export the
   chart as PDF or the data as JSON. Both files are written to the `output`
   folder in the project directory.
