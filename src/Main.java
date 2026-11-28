import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to DeadlineIQ!");
        System.out.println("Your personal assignment workload planner.");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Assignment> assignments = AssignmentStorage.loadAssignments();

        while (true) {
            System.out.println("\n===== DeadlineIQ =====");
            System.out.println("1. Add assignment");
            System.out.println("2. View assignments");
            System.out.println("3. View priorities");
            System.out.println("4. Analyze workload");
            System.out.println("5. Recommend study schedule");
            System.out.println("6. Smart Study Planner");
            System.out.println("7. Mark assignment as complete");
            System.out.println("8. Reset all assignments");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addAssignment(scanner, assignments);
                    break;
                case "2":
                    viewAssignments(assignments);
                    break;
                case "3":
                    viewPriorities(assignments);
                    break;
                case "4":
                    if (noPendingAssignments(assignments)) {
                        System.out.println("No pending assignments to analyze.");
                    } else {
                        WorkloadAnalyzer.analyzeWorkload(assignments);
                    }
                    break;
                case "5":
                    recommendSchedule(scanner, assignments);
                    break;
                case "6":
                    smartPlanner(scanner, assignments);
                    break;
                case "7":
                    completeAssignment(scanner, assignments);
                    break;
                case "8":
                    resetAssignments(scanner, assignments);
                    break;
                case "9":
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 9.");
            }
        }
    }

    public static void addAssignment(Scanner scanner, ArrayList<Assignment> assignments) {
        try {
            System.out.print("Enter assignment name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter deadline (YYYY-MM-DD): ");
            LocalDate deadline = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter difficulty (1-5): ");
            int difficulty = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter estimated hours: ");
            double estimatedHours = Double.parseDouble(scanner.nextLine());

            if (difficulty < 1 || difficulty > 5) {
                System.out.println("Difficulty must be between 1 and 5.");
                return;
            }

            if (!Double.isFinite(estimatedHours) || estimatedHours <= 0) {
                System.out.println("Estimated hours must be a positive number.");
                return;
            }

            // Commas are not allowed because assignments are stored in CSV.
            if (name.isBlank() || name.contains(",")) {
                System.out.println("Enter a valid name without commas.");
                return;
            }

            assignments.add(new Assignment(name, deadline, difficulty, estimatedHours));
            AssignmentStorage.saveAssignments(assignments);
            System.out.println("Assignment added successfully!");

        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    public static void viewAssignments(ArrayList<Assignment> assignments) {
        if (assignments.isEmpty()) {
            System.out.println("No assignments added yet.");
            return;
        }

        for (Assignment task : assignments) {
            System.out.println("Name: " + task.getName());
            System.out.println("Deadline: " + task.getDeadline());
            System.out.println("Difficulty: " + task.getDifficulty());
            System.out.println("Estimated hours: " + task.getEstimatedHours());
            System.out.println("Status: " +
                    (task.isCompleted() ? "Completed" : "Pending"));
            System.out.println("--------------------");
        }
    }

    public static void viewPriorities(ArrayList<Assignment> assignments) {
        if (noPendingAssignments(assignments)) {
            System.out.println("No pending assignments.");
            return;
        }

        // Sort a copy to preserve the original assignment order.
        ArrayList<Assignment> sortedAssignments = new ArrayList<>(assignments);

        sortedAssignments.sort(
                Comparator.comparingDouble(PriorityCalculator::calculatePriority)
                        .reversed()
        );

        for (Assignment task : sortedAssignments) {
            if (!task.isCompleted()) {
                System.out.printf("%s | Priority: %.2f%n",
                        task.getName(),
                        PriorityCalculator.calculatePriority(task));
            }
        }
    }

    public static void recommendSchedule(Scanner scanner, ArrayList<Assignment> assignments) {
        if (noPendingAssignments(assignments)) {
            System.out.println("No pending assignments to schedule.");
            return;
        }

        try {
            System.out.print("Available study hours per day: ");
            double dailyHours = Double.parseDouble(scanner.nextLine());

            if (!Double.isFinite(dailyHours) || dailyHours <= 0 || dailyHours > 24) {
                System.out.println("Enter valid daily study hours.");
            } else {
                StudyScheduler.recommendSchedule(assignments, dailyHours);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Enter a number.");
        }
    }

    public static void smartPlanner(Scanner scanner, ArrayList<Assignment> assignments) {
        if (noPendingAssignments(assignments)) {
            System.out.println("No pending assignments.");
            return;
        }

        while (true) {
            System.out.println("\n===== SMART STUDY PLANNER =====");
            System.out.println("1. Deadline Risk Radar");
            System.out.println("2. Today's Game Plan");
            System.out.println("3. Return to main menu");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("3")) {
                return;
            }

            if (!choice.equals("1") && !choice.equals("2")) {
                System.out.println("Invalid choice. Please enter 1, 2 or 3.");
                continue;
            }

            try {
                if (choice.equals("1")) {
                    System.out.print("Available study hours per day: ");
                } else {
                    System.out.print("Available study hours today: ");
                }

                double hours = Double.parseDouble(scanner.nextLine());

                if (!Double.isFinite(hours) || hours <= 0 || hours > 24) {
                    System.out.println("Enter valid study hours.");
                    continue;
                }

                if (choice.equals("1")) {
                    SmartStudyPlanner.riskRadar(assignments, hours);
                } else {
                    SmartStudyPlanner.gamePlan(assignments, hours);
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static void completeAssignment(Scanner scanner, ArrayList<Assignment> assignments) {
        if (assignments.isEmpty()) {
            System.out.println("No assignments available.");
            return;
        }

        for (int i = 0; i < assignments.size(); i++) {
            Assignment task = assignments.get(i);

            System.out.println((i + 1) + ". " + task.getName()
                    + " | Due: " + task.getDeadline()
                    + " | " + (task.isCompleted() ? "Completed" : "Pending"));
        }

        System.out.print("Enter assignment number: ");

        try {
            int number = Integer.parseInt(scanner.nextLine());

            if (number < 1 || number > assignments.size()) {
                System.out.println("Invalid assignment number.");
                return;
            }

            // Convert the user's selection to a zero-based index.
            Assignment task = assignments.get(number - 1);

            if (task.isCompleted()) {
                System.out.println("Assignment is already completed.");
            } else {
                task.markCompleted();
                AssignmentStorage.saveAssignments(assignments);
                System.out.println(task.getName() + " marked as complete!");
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    public static void resetAssignments(Scanner scanner, ArrayList<Assignment> assignments) {
        if (assignments.isEmpty()) {
            System.out.println("No assignments to reset.");
            return;
        }

        // Require confirmation to prevent accidental data loss.
        System.out.print("Delete all assignments? (yes/no): ");
        String confirmation = scanner.nextLine().trim();

        if (confirmation.equalsIgnoreCase("yes")) {
            assignments.clear();
            AssignmentStorage.saveAssignments(assignments);
            System.out.println("All assignments have been reset.");
        } else if (confirmation.equalsIgnoreCase("no")) {
            System.out.println("Reset cancelled.");
        } else {
            System.out.println("Invalid input. Please enter yes or no.");
        }
    }

    public static boolean noPendingAssignments(ArrayList<Assignment> assignments) {
        for (Assignment task : assignments) {
            if (!task.isCompleted()) {
                return false;
            }
        }

        return true;
    }
}
