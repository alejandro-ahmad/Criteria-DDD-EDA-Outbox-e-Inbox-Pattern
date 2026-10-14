package com.example.demo.shared.infrastructure.error;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.example.demo.shared.application.exceptions.AlreadyExistsException;
import com.example.demo.shared.application.exceptions.NotFoundException;
import com.example.demo.shared.domain.bus.query.QueryHandlerExecutionError;
import com.example.demo.shared.domain.exceptions.InvalidArgumentException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.accept.InvalidApiVersionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request
    ) {
        String message = ex.getMostSpecificCause() != null
                ? ex.getMostSpecificCause().getMessage()
                : ex.getMessage();

        /*
         * PostgreSQL:
         * SQLState 23503 = foreign_key_violation
         */
        if (message != null && message.contains("SQLState: 23503")) {
            return buildResponse(
                    ProblemType.ARGUMENT_NOT_VALID,
                    extractForeignKeyMessage(message),
                    request,
                    ex
            );
        }

        /*
         * Por si el SQLState no aparece en el message del cause,
         * revisamos directamente toda la excepción.
         */
        String fullMessage = ex.toString();

        if (fullMessage.contains("violates foreign key constraint")) {
            return buildResponse(
                    ProblemType.ARGUMENT_NOT_VALID,
                    extractForeignKeyMessage(fullMessage),
                    request,
                    ex
            );
        }

        /*
         * Tu caso específico anterior del domicilio.
         */
        if (ex.getMessage() != null
                && ex.getMessage().contains("fk1nobhxvjcvrqa33n7pnntkx9s")) {

            return buildResponse(
                    ProblemType.RESOURCE_NOT_FOUND,
                    "No se pudo registrar el domicilio. "
                            + "El empleado especificado no existe en el sistema.",
                    request,
                    ex
            );
        }

        return buildResponse(
                ProblemType.INTERNAL_SERVER_ERROR,
                "Error de integridad de datos en la base de datos.",
                request,
                ex
        );
    }

    @ExceptionHandler(InvalidArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle(
            InvalidArgumentException ex,
            HttpServletRequest request
    ) {
        return buildBadRequestResponse(
                ex.getMessage(),
                request,
                ex
        );
    }



    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle(
            PropertyReferenceException ex,
            HttpServletRequest request
    ) {
        return buildBadRequestResponse(
                ex.getMessage(),
                request,
                ex
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handle(
            NotFoundException ex,
            HttpServletRequest request
    ) {
        return buildResponse(
                ProblemType.RESOURCE_NOT_FOUND,
                ex.getMessage(),
                request,
                ex
        );
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handle(
            AlreadyExistsException ex,
            HttpServletRequest request
    ) {
        return buildResponse(
                ProblemType.RESOURCE_ALREADY_EXISTS,
                ex.getMessage(),
                request,
                ex
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<ErrorDetail> details =
                ex.getBindingResult()
                  .getFieldErrors()
                  .stream()
                  .map(error ->
                               new ErrorDetail(
                                       error.getField()
                                               + ": "
                                               + error.getDefaultMessage()
                               )
                  )
                  .toList();

        return new ErrorResponse(
                new Error(
                        ProblemType.ARGUMENT_NOT_VALID.getType(),
                        ProblemType.ARGUMENT_NOT_VALID.getCode(),
                        ProblemType.ARGUMENT_NOT_VALID.getStatus().value(),
                        ProblemType.ARGUMENT_NOT_VALID.getTitle(),
                        "Error de validación en la petición HTTP",
                        getRequestPath(request),
                        details
                )
        ).toResponseEntity(ex);
    }

    /**
     * Errores producidos por Bean Validation fuera de la validación
     * estándar del @RequestBody.
     *
     * Ejemplo:
     * @NotNull
     * @NotBlank
     * @Size
     * @Min
     * @Max
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle(
            ConstraintViolationException ex,
            HttpServletRequest request
    ) {
        List<ErrorDetail> details =
                ex.getConstraintViolations()
                  .stream()
                  .map(this::toErrorDetail)
                  .toList();

        return new ErrorResponse(
                new Error(
                        ProblemType.ARGUMENT_NOT_VALID.getType(),
                        ProblemType.ARGUMENT_NOT_VALID.getCode(),
                        ProblemType.ARGUMENT_NOT_VALID.getStatus().value(),
                        ProblemType.ARGUMENT_NOT_VALID.getTitle(),
                        "Error de validación de los datos.",
                        getRequestPath(request),
                        details
                )
        ).toResponseEntity(ex);
    }

    private ErrorDetail toErrorDetail(
            ConstraintViolation<?> violation
    ) {
        String propertyPath =
                violation.getPropertyPath() != null
                        ? violation.getPropertyPath().toString()
                        : "campo";

        String message =
                violation.getMessage() != null
                        ? violation.getMessage()
                        : "Valor inválido.";

        return new ErrorDetail(
                propertyPath + ": " + message
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        String detailMessage =
                "El cuerpo de la petición (JSON) es inválido o está mal formado.";

        if (ex.getCause() instanceof UnrecognizedPropertyException unex) {

            detailMessage = String.format(
                    "La propiedad '%s' no es reconocida.",
                    unex.getPropertyName()
            );

        } else if (ex.getCause() instanceof InvalidFormatException ifex) {

            String fieldName =
                    ifex.getPath().isEmpty()
                            ? "campo desconocido"
                            : ifex.getPath().getLast().getFieldName();

            detailMessage = String.format(
                    "El valor '%s' no es válido para el campo '%s'.",
                    ifex.getValue(),
                    fieldName
            );
        }

        return buildBadRequestResponse(
                detailMessage,
                request,
                ex
        );
    }

    @ExceptionHandler(InvalidApiVersionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle(
            InvalidApiVersionException ex,
            HttpServletRequest request
    ) {
        return buildBadRequestResponse(
                "Versión de API no soportada o inválida: "
                        + ex.getVersion(),
                request,
                ex
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        String name = ex.getName();

        String type =
                ex.getRequiredType() != null
                        ? ex.getRequiredType().getSimpleName()
                        : "desconocido";

        Object value = ex.getValue();

        String message = String.format(
                "El parámetro '%s' con valor '%s' no es válido. "
                        + "Se esperaba un formato de tipo %s.",
                name,
                value,
                type
        );

        return buildBadRequestResponse(
                message,
                request,
                ex
        );
    }

    @ExceptionHandler(QueryHandlerExecutionError.class)
    public ResponseEntity<ErrorResponse> handle(
            QueryHandlerExecutionError ex,
            HttpServletRequest request
    ) {
        Throwable cause = ex.getCause();

        if (cause instanceof NotFoundException notFoundException) {
            return handle(notFoundException, request);
        }

        if (cause instanceof InvalidArgumentException invalidArgumentException) {
            return handle(invalidArgumentException, request);
        }

        if (cause instanceof AlreadyExistsException alreadyExistsException) {
            return handle(alreadyExistsException, request);
        }


        if (cause instanceof PropertyReferenceException propertyReferenceException) {
            return handle(propertyReferenceException, request);
        }

        return handleAll(ex, request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleAll(
            Exception ex,
            HttpServletRequest request
    ) {
        return buildResponse(
                ProblemType.INTERNAL_SERVER_ERROR,
                ex.getMessage() != null
                        ? ex.getMessage()
                        : "Ocurrió un error inesperado.",
                request,
                ex
        );
    }

    private ResponseEntity<ErrorResponse> buildBadRequestResponse(
            String message,
            HttpServletRequest request,
            Exception ex
    ) {
        return buildResponse(
                ProblemType.ARGUMENT_NOT_VALID,
                message,
                request,
                ex
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            ProblemType problemType,
            String message,
            HttpServletRequest request,
            Exception ex
    ) {
        return new ErrorResponse(
                new Error(
                        problemType.getType(),
                        problemType.getCode(),
                        problemType.getStatus().value(),
                        problemType.getTitle(),
                        message,
                        getRequestPath(request),
                        List.of()
                )
        ).toResponseEntity(ex);
    }

    private String getRequestPath(HttpServletRequest request) {
        return request.getMethod()
                      .concat(" ")
                      .concat(request.getRequestURI());
    }

    private String extractForeignKeyMessage(String message) {

        if (message.contains("Key (estamento_id)=")) {
            return "El estamento especificado no existe en el sistema.";
        }

        if (message.contains("Key (empleado_id)=")) {
            return "El empleado especificado no existe en el sistema.";
        }

        if (message.contains("Key (categoria_id)=")) {
            return "La categoría especificada no existe en el sistema.";
        }

        return "Uno de los registros relacionados no existe en el sistema.";
    }
}
