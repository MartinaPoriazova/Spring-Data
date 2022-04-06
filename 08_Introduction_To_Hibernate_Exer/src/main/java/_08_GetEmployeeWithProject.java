import entities.Employee;
import entities.Project;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Comparator;
import java.util.Scanner;

public class _08_GetEmployeeWithProject {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        System.out.println("Enter employee id: ");
        Scanner scanner = new Scanner((System.in));
        int employeeId = Integer.parseInt(scanner.nextLine());

        entityManager
                .createQuery("SELECT e FROM Employee e" +
                                " WHERE  e.id = :id",
                        Employee.class)
                .setParameter("id", employeeId)
                .getResultList()
                .forEach(e -> {
                    String format = String.format("%s %s - %s",
                            e.getFirstName(), e.getLastName(), e.getJobTitle());

                    System.out.println(format);

                    e.getProjects().stream()
                            .sorted(Comparator.comparing(Project::getName))
                            .forEach(project -> System.out.printf("\t%s%n", project.getName()));
                });

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
