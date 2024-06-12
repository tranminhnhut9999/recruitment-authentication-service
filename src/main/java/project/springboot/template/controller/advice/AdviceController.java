package project.springboot.template.controller.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import project.springboot.template.entity.common.ApiException;
import project.springboot.template.entity.common.ApiResponse;
import project.springboot.template.util.MapperUtil;
import project.springboot.template.util.MessageUtil;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AdviceController extends ResponseEntityExceptionHandler {

    private final MessageUtil messageUtil;

    public AdviceController(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    /**
     * Handle api e, e throw if api error
     *
     * @param e ApiException
     * @return ResponseEntity
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<?>> handleApiException(ApiException e) {
        e.printStackTrace();
        if (HttpStatus.FORBIDDEN.equals(e.getStatus()) && e.getMessage() == null) {
            e.setMessage("Bạn không có quyền truy cập!");
        }
        return ResponseEntity.status(e.getStatus())
                .body(ApiResponse.failed(e.getMessage()));
    }


//    /**
//     * Handle jpa exception, exception throw if call data error
//     * @param e ApiException
//     * @return ResponseEntity
//     */
//    @ExceptionHandler(value = JpaSystemException.class)
//    public ResponseEntity<ApiResponse<?>> handleJpaSystemException(JpaSystemException e) {
//        logger.error(e.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(ApiResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getCause().getCause().toString()));
//    }

    /**
     * Handle bad request, exception throw if request body not match
     *
     * @param e ApiException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        logger.warn("Returning HTTP 400 Bad Request", e);
    }

    /**
     * Handle method argument not valid, exception throw if method argument not valid
     *
     * @param e ApiException
     * @return ResponseEntity
     */
    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status, @NonNull WebRequest request) {
        e.printStackTrace();
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        try {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.failed(MapperUtil.getInstance().getMapper()
                            .writeValueAsString(errors)));
        } catch (JsonProcessingException ex) {
            throw ApiException.create(HttpStatus.INTERNAL_SERVER_ERROR).withMessage(ex.getMessage());
        }
    }

    /**
     * Handle exception, exception throw while running project
     *
     * @param exception ApiException
     * @return ResponseEntity
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception exception) {
        exception.printStackTrace();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(ApiResponse.failed(messageUtil.getLocalMessage("internal_server_error")));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failed(exception.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, @NonNull HttpHeaders headers,
                                                                  HttpStatus status, @NonNull WebRequest request) {
        String message = ex.getMessage();
        if (message != null && message.contains("JSON parse error")) {
            message = messageUtil.getLocalMessage("data_invalid");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failed(message));
    }

    @ExceptionHandler(value = InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<ApiResponse<?>> handleException(InvalidDataAccessResourceUsageException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failed("invalid field in your request"));
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failed("Bạn không có quyền truy cập!"));
    }
}
