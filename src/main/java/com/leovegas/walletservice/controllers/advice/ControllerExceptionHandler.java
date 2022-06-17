package com.leovegas.walletservice.controllers.advice;

import com.leovegas.walletservice.exceptions.TransactionIdAlreadyExists;
import com.leovegas.walletservice.exceptions.WalletNotFoundException;
import com.leovegas.walletservice.exceptions.WalletUniqueConstraintViolationException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWalletNotFoundException(WalletNotFoundException exception, WebRequest request) {
        return this.logAndReturnResult(HttpStatus.NOT_FOUND, exception, request);
    }

    @ExceptionHandler(WalletUniqueConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleWalletUniqueConstraintViolationException(
            WalletUniqueConstraintViolationException exception, WebRequest request) {
        return this.logAndReturnResult(HttpStatus.CONFLICT, exception, request);
    }

    @ExceptionHandler(TransactionIdAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleTransactionIdAlreadyExists(TransactionIdAlreadyExists exception, WebRequest request) {
        return this.logAndReturnResult(HttpStatus.CONFLICT, exception, request);
    }

    private ResponseEntity<ErrorResponse> logAndReturnResult(HttpStatus status, Exception exception, WebRequest request) {
        log.error("{}", exception.getMessage(), exception);
        return ResponseEntity.status(status)
                .body(new ErrorResponse(((ServletWebRequest) request).getRequest().getRequestURI(), exception.getMessage()));
    }

    @Getter
    class ErrorResponse {
        private final String status = "ERROR";
        private final LocalDateTime timestamp = LocalDateTime.now();
        private final String requestUrl;
        private final String message;

        public ErrorResponse(String requestUrl, String message) {
            this.requestUrl = requestUrl;
            this.message = message;
        }
    }
}
