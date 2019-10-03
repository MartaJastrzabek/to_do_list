package pl.martajastrzabek.todolist.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadlineDate;

    @Enumerated(EnumType.STRING)
    private TaskCategory category;

    private boolean isDone;

    public Task() {
    }

    public Task(String name, LocalDate deadlineDate, TaskCategory category, boolean isDone) {
        this.name = name;
        this.deadlineDate = deadlineDate;
        this.category = category;
        this.isDone = isDone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public TaskCategory getCategory() {
        return category;
    }

    public void setCategory(TaskCategory category) {
        this.category = category;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        return "id:" + id +
                ", nazwa: " + name +
                ", data końcowa: " + deadlineDate +
                ", kategoria: " + category.getName() +
                ", wykonane: " + isDone + ".";
    }
}
