import entities.Employee;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class _11_FindEmployeesByFirstName {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        System.out.println("Enter letters:");
        Scanner scanner = new Scanner((System.in));
        String firstLetters = scanner.nextLine();

        entityManager
                .createQuery("SELECT e FROM Employee e " +
                        " WHERE e.firstName LIKE :name",
                        Employee.class)
                .setParameter("name", firstLetters + '%')
                .getResultList()
                .forEach(e -> {
                    String format = String.format("%s %s - %s - ($%.2f)",
                            e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary());

                    System.out.println(format);
                });

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}