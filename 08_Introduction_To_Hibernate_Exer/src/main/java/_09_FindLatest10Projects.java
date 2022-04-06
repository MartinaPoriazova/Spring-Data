import entities.Project;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class _09_FindLatest10Projects {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager
                .createQuery("FROM Project p" +
                                " ORDER BY p.startDate DESC",
                        Project.class)
                .setMaxResults(10)
                .getResultStream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> {
                    String format = String.format("Project name: %s\n" +
                                    "\tProject Description: %s\n" +
                                    "\tProject Start Date: %s\n" +
                                    "\tProject End Date: %s",
                            p.getName(),
                            p.getDescription(),
                            p.getStartDate().minusHours(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s")),
                            p.getEndDate());

                    System.out.println(format);
                });

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
