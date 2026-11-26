import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to DeadlineIQ!");
        System.out.println("Your personal assignment workload planner.");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Assignment> assignments = new ArrayList<>();

        while (true) {
            System.out.println("\n===== DeadlineIQ =====");
            System.out.println("1. Add assignment");
            System.out.println("2. View assignments");
            System.out.println("3. View priorities");
            System.out.println("4. Analyze workload");
            System.out.println("5. Recommend study schedule");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                try {
                    System.out.print("Enter assignment name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter deadline (YYYY-MM-DD): ");
                    LocalDate deadline = LocalDate.parse(scanner.nextLine());

                    System.out.print("Enter difficulty (1-5): ");
                    int difficulty = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter estimated hours: ");
                    double estimatedHours = Double.parseDouble(scanner.nextLine());

                    if (difficulty < 1 || difficulty > 5) {
                        System.out.println("Difficulty must be between 1 and 5.");
                        continue;
                    }

                    if (!Double.isFinite(estimatedHours) || estimatedHours <= 0) {
                        System.out.println("Estimated hours must be a positive number.");
                        continue;
                    }
                    if (name.isBlank() || name.contains(",")) {
                    System.out.println("Enter a valid name without commas.");
                    continue;
                }
                    assignments.add(new Assignment(name, deadline, difficulty, estimatedHours));
                    AssignmentStorage.saveAssignments(assignments);
                    System.out.println("Assignment added successfully!");

                } catch (Exception e) {
                    System.out.println("Invalid input. Please try again.");
                }

            } else if (choice.equals("2")) {
                if (assignments.isEmpty()) {
                    System.out.println("No assignments added yet.");
                } else {
                    for (Assignment task : assignments) {
                        System.out.println("Name: " + task.getName());
                        System.out.println("Deadline: " + task.getDeadline());
                        System.out.println("Difficulty: " + task.getDifficulty());
                        System.out.println("Estimated hours: " + task.getEstimatedHours());
                        System.out.println("--------------------");
                    }
                }

            } else if (choice.equals("6")) {
                System.out.println("Goodbye!");
                break;
            }
        }

        scanner.close();
    }
}
