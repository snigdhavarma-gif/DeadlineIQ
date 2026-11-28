import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PriorityCalculator {
    public static double calculatePriority(Assignment task) {
        long daysRemaining = ChronoUnit.DAYS.between(
            LocalDate.now(), task.getDeadline()
        );

        return (task.getDifficulty() * task.getEstimatedHours())
        / Math.max(1, daysRemaining);
    }
}