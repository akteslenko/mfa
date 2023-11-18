package com.nexo.mfa.exceptionHandler;

import com.nexo.mfa.exception.OTPCodeException;
import com.nexo.mfa.response.ApiResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleBadRequest(final MethodArgumentNotValidException exception) {

        List<String> errors = exception.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

//        ApiResponseData responseData = getApiResponse(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        ApiResponseData responseData = getApiResponse(errors);

        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OTPCodeException.class)
    public ResponseEntity<Object> handleOTPException(final OTPCodeException exception) {
        ApiResponseData responseData = getApiResponse(exception.getReason());

        return new ResponseEntity<>(responseData, exception.getStatusCode());
    }

    private ApiResponseData getApiResponse(Object exception) {
        ApiResponseData response = ApiResponseData.builder()
                .message(exception)
                .build();
        log.warn("Request exception: " + exception);

        return response;
    }
}
