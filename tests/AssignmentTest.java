import java.time.LocalDate;

public class AssignmentTest {
    public static void main(String[] args) {
        Assignment task = new Assignment(
            "Calculus Homework",
            LocalDate.of(2026, 11, 29),
            4,
            2.5
        );

        System.out.println(task.getName());
        System.out.println(task.getDeadline());
        System.out.println(task.getDifficulty());
        System.out.println(task.getEstimatedHours());
    }
}
