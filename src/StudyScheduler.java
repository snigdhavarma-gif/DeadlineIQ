import java.util.ArrayList;
import java.time.LocalDate;

public class StudyScheduler {
    public static void recommendSchedule(
            ArrayList<Assignment> assignments, double dailyHours) {
                for (Assignment task : assignments) {
    long daysNeeded = (long) Math.ceil(
        task.getEstimatedHours() / dailyHours
    );

    LocalDate startDate = task.getDeadline()
            .minusDays(daysNeeded - 1);

            if (task.getDeadline().isBefore(LocalDate.now())) {
    System.out.println("Warning: Assignment is overdue!");
} else if (startDate.isBefore(LocalDate.now())) {
    System.out.println("Warning: Start immediately! You may need extra study hours.");
}

    System.out.println("Assignment: " + task.getName());
    System.out.println("Days needed: " + daysNeeded);
    System.out.println("Recommended start: " + startDate);
    System.out.println("Deadline: " + task.getDeadline());
    System.out.println("--------------------");
}

    }
}
