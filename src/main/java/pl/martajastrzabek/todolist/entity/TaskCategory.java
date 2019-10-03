package pl.martajastrzabek.todolist.entity;

public enum TaskCategory {
    CHORES("obowiązki domowe"),
    PROFESSIONAL("obowiązki zawodowe"),
    SELFDEVELOPMENT("samorozwój"),
    FREE_TIME_ACTIVITY("czas wolny");

    String name;

    private TaskCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
