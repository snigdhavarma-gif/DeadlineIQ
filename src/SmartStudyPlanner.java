import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

public class SmartStudyPlanner {

    public static void riskRadar(ArrayList<Assignment> assignments, double dailyHours) {

        System.out.println("\n===== DEADLINE RISK RADAR =====");

        for (Assignment task : assignments) {
            if (task.isCompleted()) {
                continue;
            }

            long daysLeft = ChronoUnit.DAYS.between(
                    LocalDate.now(), task.getDeadline()) + 1;

            String risk;

            if (daysLeft <= 0) {
                risk = "CRITICAL - Overdue";
            } else {
                double availableHours = daysLeft * dailyHours;
                double percentage = task.getEstimatedHours() / availableHours * 100;

                if (percentage > 100) {
                    risk = "CRITICAL";
                } else if (percentage >= 75) {
                    risk = "AT RISK";
                } else {
                    risk = "ON TRACK";
                }
            }

            System.out.println("Assignment: " + task.getName());
            System.out.println("Deadline: " + task.getDeadline());
            System.out.println("Estimated hours: " + task.getEstimatedHours());
            System.out.println("Risk level: " + risk);
            System.out.println("--------------------------------");
        }
    }
    public static void gamePlan(ArrayList<Assignment> assignments, double dailyHours) {
    ArrayList<Assignment> pending = new ArrayList<>();

    for (Assignment task : assignments) {
        if (!task.isCompleted()) {
            pending.add(task);
        }
    }

    if (pending.isEmpty()) {
        System.out.println("No pending assignments.");
        return;
    }

    // Work on higher-priority assignments first.
    pending.sort(
        Comparator.comparingDouble(PriorityCalculator::calculatePriority)
                  .reversed()
    );

    double remainingHours = dailyHours;

    System.out.println("\n===== TODAY'S GAME PLAN =====");
    System.out.println("Available study time: " + dailyHours + " hours");
    System.out.println("----------------------------------------");

    int number = 1;

    for (Assignment task : pending) {
        if (remainingHours <= 0) {
            break;
        }

        // Allocate only the hours available or needed.
        double allocated = Math.min(task.getEstimatedHours(), remainingHours);

        System.out.printf("%d. %-20s %.2f hours%n",
                number, task.getName(), allocated);

        remainingHours -= allocated;
        number++;
    }

    System.out.println("----------------------------------------");

    System.out.printf("Total allocated: %.2f hours%n",
            dailyHours - remainingHours);

    System.out.printf("Remaining time: %.2f hours%n",
            remainingHours);
}
}