import java.time.LocalDate;

public class Assignment {
    private String name;
    private LocalDate deadline;
    private int difficulty;
    private double estimatedHours;

    public Assignment(String name, LocalDate deadline,
    int difficulty, double estimatedHours) {
    this.name = name;
    this.deadline = deadline;
    this.difficulty = difficulty;
    this.estimatedHours = estimatedHours;
}

public String getName() {
    return name;
}

public LocalDate getDeadline() {
    return deadline;
}

public int getDifficulty() {
    return difficulty;
}

public double getEstimatedHours() {
    return estimatedHours;
}
}