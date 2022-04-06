package softuni.exam.instagraphlite.util;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidationUtil {

    <E> boolean isValid(E entity);

//    <T>Set<ConstraintViolation<T>> violation(T entity);
}
