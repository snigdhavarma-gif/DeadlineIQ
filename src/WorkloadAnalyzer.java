import java.util.ArrayList;
import java.util.TreeMap;
import java.time.LocalDate;

public class WorkloadAnalyzer {
    public static void analyzeWorkload(ArrayList<Assignment> assignments) {
        TreeMap<LocalDate, Double> workload = new TreeMap<>();

        for (Assignment task : assignments) {
            if (task.isCompleted()) {
                continue;
            }
            LocalDate date = task.getDeadline();

            double hours = workload.getOrDefault(date, 0.0);
            workload.put(date, hours + task.getEstimatedHours());
        }

        for (LocalDate date : workload.keySet()) {
            double hours = workload.get(date);

            System.out.println(date + ": " + hours + " hours");

            if (hours > 6) {
                System.out.println("Warning: Heavy workload!");
            }
        }
    }
}
