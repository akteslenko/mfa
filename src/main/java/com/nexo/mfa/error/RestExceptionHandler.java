package com.nexo.mfa.error;

import com.nexo.mfa.response.ApiResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleBadRequest(final MethodArgumentNotValidException ex) {

        ApiResponseData responseData = getApiResponse(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }
    private ApiResponseData getApiResponse(String exception) {
        ApiResponseData response = ApiResponseData.builder()
                .message(exception)
                .build();
        log.warn("Request exception: " + exception);

        return response;
    }
}
