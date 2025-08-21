package io.irwansyahdev96.readcollection.base.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import io.irwansyahdev96.readcollection.base.dto.res.BaseTransactionResDto;
import io.irwansyahdev96.readcollection.base.dto.validation.ValidationRuntimeException;



@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // technical error
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseTransactionResDto> handleRuntimeException(RuntimeException ex) {
        BaseTransactionResDto res = new BaseTransactionResDto();
        ex.printStackTrace();
        res.setMessage("Technical Error"); // pesan dari service
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }

    // validation via annotation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        BaseTransactionResDto res = new BaseTransactionResDto();

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );

        res.setErrors(errors);
        res.setMessage("Field validation");

        return ResponseEntity.badRequest().body(res);
    }

    // validation via service
    @ExceptionHandler(ValidationRuntimeException.class)
    public ResponseEntity<?> handleBindException(ValidationRuntimeException ex) {
        BaseTransactionResDto res = new BaseTransactionResDto();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        res.setErrors(errors);
        res.setMessage("Field validation");
        return ResponseEntity.badRequest().body(res);
    }

    // about data
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleBindException(ResponseStatusException ex) {
        BaseTransactionResDto res = new BaseTransactionResDto();
        res.setMessage(ex.getReason());
        return ResponseEntity.status(ex.getStatus()).body(res);
    }
}
