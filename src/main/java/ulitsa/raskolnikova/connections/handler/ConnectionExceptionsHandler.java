package ulitsa.raskolnikova.connections.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ulitsa.raskolnikova.connections.exception.ConnectionExistsException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ConnectionExceptionsHandler {
    @ExceptionHandler({IllegalArgumentException.class, SQLException.class, ConnectionExistsException.class})
    public ResponseEntity<String> handleBadRequestExceptions(Exception ex) {
        return ResponseEntity.badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({IOException.class, InterruptedException.class})
    public ResponseEntity<String> handleIOException(Exception ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

}
