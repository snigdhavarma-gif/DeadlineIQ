import java.io.*;
import java.util.ArrayList;
import java.time.LocalDate;

public class AssignmentStorage {
    private static final String FILE_NAME = "assignments.csv";

    public static void saveAssignments(ArrayList<Assignment> assignments) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (Assignment task : assignments) {
                writer.write(task.getName() + ","
                        + task.getDeadline() + ","
                        + task.getDifficulty() + ","
                        + task.getEstimatedHours() + ","
                        + task.isCompleted() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving assignments.");
        }
    }

    public static ArrayList<Assignment> loadAssignments() {
        ArrayList<Assignment> assignments = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length != 4 && parts.length != 5) {
                    continue;
}

                String name = parts[0];
                LocalDate deadline = LocalDate.parse(parts[1]);
                int difficulty = Integer.parseInt(parts[2]);
                double estimatedHours = Double.parseDouble(parts[3]);

               Assignment task = new Assignment(name, deadline, difficulty, estimatedHours);

                if (parts.length == 5 && Boolean.parseBoolean(parts[4])) {
                    task.markCompleted();
                }

                assignments.add(task);
            }

        } catch (FileNotFoundException e) {
            // No saved assignments yet
        } catch (IOException e) {
            System.out.println("Error loading assignments.");
        }

        return assignments;
    }
}