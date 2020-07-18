package com.gabrielspassos.poc.controller.v1.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrielspassos.poc.controller.v1.response.ErrorResponse;
import com.gabrielspassos.poc.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.gabrielspassos.poc.config.ErrorConstants.INVALID_INPUT_CODE;
import static com.gabrielspassos.poc.config.ErrorConstants.SYSTEM_UNEXPECTED_ERROR_CODE;
import static com.gabrielspassos.poc.config.ErrorConstants.SYSTEM_UNEXPECTED_ERROR_MESSAGE;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class GlobalErrorHandler {

    private ObjectMapper objectMapper;

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Throwable throwable) {
        if(throwable instanceof BusinessException) {
            BusinessException e = (BusinessException) throwable;
            ErrorResponse error = objectMapper.convertValue(e.getErrorDTO(), ErrorResponse.class);
            return ResponseEntity.status(e.getStatus()).body(error);
        }

        log.error("Erro não esperado", throwable);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createError(SYSTEM_UNEXPECTED_ERROR_CODE, SYSTEM_UNEXPECTED_ERROR_MESSAGE));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createError(INVALID_INPUT_CODE, errorMessage));
    }

    private ErrorResponse createError(String code, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(code);
        errorResponse.setMessage(message);
        return errorResponse;
    }
}
