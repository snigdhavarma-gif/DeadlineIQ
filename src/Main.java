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
            System.out.println("6. Mark assignment as complete");
            System.out.println("7. Reset all assignments");
            System.out.println("8. Exit");
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
                        System.out.println("Status: " + 
                        (task.isCompleted() ? "Completed" : "Pending"));
                        System.out.println("--------------------");
                    }
                }

            } else if (choice.equals("3")) {
                if (assignments.isEmpty()) {
                    System.out.println("No assignments added yet.");
                } else {
                    assignments.sort(
                        Comparator.comparingDouble(PriorityCalculator::calculatePriority)
                                  .reversed()
                    );

                    for (Assignment task : assignments) {
                        if (task.isCompleted()) {
                            continue;
                        }
                        System.out.printf("%s | Priority: %.2f%n",
                                task.getName(),
                                PriorityCalculator.calculatePriority(task));
                    }
                }

            } 
                         else if (choice.equals("4")) {
                WorkloadAnalyzer.analyzeWorkload(assignments);

            } else if (choice.equals("5")) {
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
                } else if (choice.equals("6")) {
    if (assignments.isEmpty()) {
        System.out.println("No assignments available.");
        continue;
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
        } else {
            Assignment task = assignments.get(number - 1);

            if (task.isCompleted()) {
                System.out.println("Assignment is already completed.");
            } else {
                task.markCompleted();
                AssignmentStorage.saveAssignments(assignments);
                System.out.println(task.getName() + " marked as complete!");
            }
        }
    } catch (NumberFormatException e) {
        System.out.println("Please enter a valid number.");
    }
    } else if (choice.equals("7")) {
    System.out.print("Delete all assignments? (yes/no): ");
    String confirmation = scanner.nextLine();

    if (confirmation.equalsIgnoreCase("yes")) {
        assignments.clear();
        AssignmentStorage.saveAssignments(assignments);
        System.out.println("All assignments have been reset.");
    } else {
        System.out.println("Reset cancelled.");
    }

            } else if (choice.equals("8")) {
                System.out.println("Goodbye!");
                break;
            }
             else {
    System.out.println("Invalid choice. Please enter a number from 1 to 8.");
}
        }

        scanner.close();
    }
}
