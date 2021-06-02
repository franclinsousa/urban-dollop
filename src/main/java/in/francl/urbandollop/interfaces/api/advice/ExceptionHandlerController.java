package in.francl.urbandollop.interfaces.api.advice;

import in.francl.urbandollop.domain.datatransfer.exception.RestResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public final class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    
    private final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);
    
    public ExceptionHandlerController() {
        super();
    }
    
    @ExceptionHandler({ResponseStatusException.class})
    public final ResponseEntity<Object> handleExceptionResponseStatus(ResponseStatusException ex, WebRequest request) {
        return this.handleExceptionInternal(ex, null, HttpHeaders.EMPTY, ex.getStatus(), request);
    }
    
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }
        
        var response = new RestResponseException(status.value(), ex.getMessage());
        
        return ResponseEntity.status(status).headers(headers).body(response);
    }
    
}
