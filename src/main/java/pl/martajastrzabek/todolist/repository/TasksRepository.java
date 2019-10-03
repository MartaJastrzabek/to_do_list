package pl.martajastrzabek.todolist.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.martajastrzabek.todolist.entity.Task;

import javax.persistence.*;
import java.util.List;

@Repository
public class TasksRepository {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public TasksRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void addTask(Task task) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(task);
        em.getTransaction().commit();
        em.close();
    }

    public Task getTask(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Task> query = em.createQuery("select t from Task t where id = :id", Task.class);
        query.setParameter("id", id);
        Task task = query.getSingleResult();
        em.close();

        return task;
    }

    public List<Task> getAllTasks() {
        EntityManager em = entityManagerFactory.createEntityManager();
        String queryText = "select t from Task t";
        TypedQuery<Task> query = em.createQuery(queryText, Task.class);
        List<Task> resultList = query.getResultList();
        em.close();

        return resultList;
    }

    public List<Task> getAllTasks(boolean value) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Task> query = em.createQuery("select t from Task t where isDone = :value", Task.class);
        query.setParameter("value", value);
        List<Task> resultList = query.getResultList();
        em.close();

        return resultList;
    }

    public List<Task> getAllTasksSortedByDate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Task> query = em.createQuery("select t from Task t order by deadlineDate asc", Task.class);
        List<Task> resultList = query.getResultList();
        em.close();

        return resultList;
    }

    public List<Task> getAllTasksSortedByDate(boolean value) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Task> query = em.createQuery("select t from Task t where isDone = :value order by deadlineDate asc", Task.class);
        query.setParameter("value", value);
        List<Task> resultList = query.getResultList();
        em.close();

        return resultList;
    }

    public void updateIsDone(long id, boolean value) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("update Task t set isDone = :value where id = :id");
        query.setParameter("value", value);
        query.setParameter("id", id);
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    public void editTask(Task task) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("update Task t set name = :name, deadlineDate = :deadlineDate, category = :category, isDone = :isDone  where id = :id");
        query.setParameter("name", task.getName());
        query.setParameter("deadlineDate", task.getDeadlineDate());
        query.setParameter("category", task.getCategory());
        query.setParameter("isDone", task.getIsDone());
        query.setParameter("id", task.getId());
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
