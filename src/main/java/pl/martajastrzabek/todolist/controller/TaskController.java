package pl.martajastrzabek.todolist.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.martajastrzabek.todolist.entity.Task;
import pl.martajastrzabek.todolist.entity.TaskCategory;
import pl.martajastrzabek.todolist.repository.TasksRepository;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TaskController {

    TasksRepository tasks;

    public TaskController(TasksRepository tasks) {
        this.tasks = tasks;
    }

    @GetMapping("/")
    public String home(Model model, @RequestParam(required = false) String show, @RequestParam(required = false) String sort) {
        List<Task> showTasks = null;
        String tasksHeader = "";

        if ("date".equals(sort)) {
            if (show == null) {
                showTasks = tasks.getAllTasksSortedByDate();
                tasksHeader = "Wszystkie dodane zadania";
            } else if (show.equals("done")) {
                showTasks = tasks.getAllTasksSortedByDate(true);
                tasksHeader = "Wszystkie zrealizowane zadania";
            } else if (show.equals("todo")) {
                showTasks = tasks.getAllTasksSortedByDate(false);
                tasksHeader = "Wszystkie zadania do zrobienia";
            }
        } else {
            if (show == null) {
                showTasks = tasks.getAllTasks();
                tasksHeader = "Wszystkie dodane zadania";
            } else if (show.equals("done")) {
                showTasks = tasks.getAllTasks(true);
                tasksHeader = "Wszystkie zrealizowane zadania";
            } else if (show.equals("todo")) {
                showTasks = tasks.getAllTasks(false);
                tasksHeader = "Wszystkie zadania do zrobienia";
            }
        }
        LocalDate today = LocalDate.now();
        model.addAttribute("today", today);
        model.addAttribute("tasksHeader", tasksHeader);
        model.addAttribute("showTasks", showTasks);

        if (show != null) {
            model.addAttribute("show", show);
        }
        return "index";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        TaskCategory[] categoriesValues = TaskCategory.values();
        model.addAttribute("taskCategories", categoriesValues);
        return "addTask";
    }

    @PostMapping("/add")
    public String addNewTask(Task task) {
        tasks.addTask(task);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editForm(Model model, @RequestParam long id) {
        Task task = tasks.getTask(id);
        model.addAttribute("task", task);
        TaskCategory[] categoriesValues = TaskCategory.values();
        model.addAttribute("taskCategories", categoriesValues);
        return "editTask";
    }

    @PostMapping("/edit")
    public String editTask(Task task) {
        tasks.editTask(task);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateTaskStatus(@RequestParam long taskid, @RequestParam(required = false, defaultValue = "false") boolean taskstate) {
        tasks.updateIsDone(taskid, taskstate);
        return "redirect:/";
    }

}
