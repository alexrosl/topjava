package ru.javawebinar.topjava.util;

import org.postgresql.util.PSQLException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.javawebinar.topjava.HasId;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.validation.*;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    public static String getRootCauseCustomMessage(Throwable rootCause) {
        String causeMsg;
        if (rootCause instanceof ConstraintViolationException) {
            StringBuilder builder = new StringBuilder();
            ((ConstraintViolationException) rootCause)
                    .getConstraintViolations()
                    .forEach(cv -> builder.append(cv.getPropertyPath())
                            .append(" ")
                            .append(cv.getMessage())
                            .append("\n"));
            causeMsg = builder.toString();
        } else if (rootCause instanceof PSQLException) {
            PSQLException psqlException = (PSQLException) rootCause;
            if ("users_unique_email_idx".equalsIgnoreCase(psqlException.getServerErrorMessage().getConstraint())) {
                causeMsg = "User with this email already exists";
            } else if ("meals_unique_user_datetime_idx".equalsIgnoreCase(psqlException.getServerErrorMessage().getConstraint())) {
                causeMsg = "Meal with this dateTime already exists";
            } else {
                causeMsg = rootCause.toString();
            }
        } else {
            causeMsg = rootCause.toString();
        }
        return causeMsg;
    }

    private static final Validator validator;

    static {
        //  From Javadoc: implementations are thread-safe and instances are typically cached and reused.
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        //  From Javadoc: implementations of this interface must be thread-safe
        validator = factory.getValidator();
    }

    public static <T> void validate(T bean) {
        // https://alexkosarev.name/2018/07/30/bean-validation-api/
        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public static ResponseEntity<String> getErrorResponse(BindingResult result) {
        return ResponseEntity.unprocessableEntity().body(
                result.getFieldErrors().stream()
                        .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                        .collect(Collectors.joining("<br>"))
        );
    }
}