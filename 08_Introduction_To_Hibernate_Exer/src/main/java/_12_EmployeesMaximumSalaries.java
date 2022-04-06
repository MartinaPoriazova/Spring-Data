import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class _12_EmployeesMaximumSalaries {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Object[]> resultList = entityManager.createNativeQuery(
                "SELECT MAX(e.salary) AS 'max_salary', d.name" +
                        " FROM employees e" +
                        " JOIN departments d ON d.department_id = e.department_id" +
                        " GROUP BY d.name" +
                        " HAVING max_salary NOT BETWEEN 30000 AND 70000;")
                .getResultList();

        resultList.forEach(result -> System.out.printf("%s %s%n", result[1], result[0]));

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}