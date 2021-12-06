package tech.ohuru.astrosoft_api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AstrosoftResultExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(AstroResultException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String astroSoftResultHandler(AstroResultException exception) {
        return exception.getMessage();
    }
}
