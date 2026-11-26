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
                        + task.getEstimatedHours() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving assignments.");
        }
    }

    public static ArrayList<Assignment> loadAssignments() {
       ArrayList<Assignment> assignments = AssignmentStorage.loadAssignments();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                String name = parts[0];
                if (parts.length != 4) {
    continue;
}
                LocalDate deadline = LocalDate.parse(parts[1]);
                int difficulty = Integer.parseInt(parts[2]);
                double estimatedHours = Double.parseDouble(parts[3]);

                assignments.add(new Assignment(name, deadline, difficulty, estimatedHours));
            }

        } catch (FileNotFoundException e) {
            // No saved assignments yet
        } catch (IOException e) {
            System.out.println("Error loading assignments.");
        }

        return assignments;
    }
}
