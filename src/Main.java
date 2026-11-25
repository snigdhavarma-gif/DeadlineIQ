import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to DeadlineIQ!");
        System.out.println("Your personal assignment workload planner.");
        Scanner scanner = new Scanner(System.in);

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

    if (choice.equals("6")) {
        System.out.println("Goodbye!");
        break;
    }
}

scanner.close();
    }
}
