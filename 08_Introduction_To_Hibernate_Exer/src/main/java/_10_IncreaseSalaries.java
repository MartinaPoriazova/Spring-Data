import entities.Employee;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class _10_IncreaseSalaries {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        List<String> departmentsNames = List.of("Engineering", "Tool Design", "Marketing", "Information Services");

        entityManager
                .createQuery("UPDATE Employee e" +
                        " SET e.salary = e.salary * 1.12" +
                        " WHERE  e.department.id IN :ids")
                .setParameter("ids", Set.of(1, 2, 4, 11))
                .executeUpdate();

        entityManager
                .createQuery("SELECT e FROM Employee e" +
                                " WHERE  e.department.name IN (:department_names)",
                        Employee.class)
                .setParameter("department_names", departmentsNames)
                .getResultList()
                .forEach(e -> {
                    String format = String.format("%s %s ($%.2f)",
                            e.getFirstName(), e.getLastName(), e.getSalary());

                    System.out.println(format);
                });

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
