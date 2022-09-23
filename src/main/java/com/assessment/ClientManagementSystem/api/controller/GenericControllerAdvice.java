package com.assessment.ClientManagementSystem.api.controller;

import com.assessment.ClientManagementSystem.api.controller.model.error.ApiError;
import com.assessment.ClientManagementSystem.api.controller.model.error.ApiErrorResponse;
import com.assessment.ClientManagementSystem.api.exception.GenericException;
import com.assessment.ClientManagementSystem.api.exception.InvalidFieldException;
import com.assessment.ClientManagementSystem.api.exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.assessment.ClientManagementSystem.api.controller.model.error.ApiErrorType.NOT_FOUND_ERROR;
import static com.assessment.ClientManagementSystem.api.controller.model.error.ApiErrorType.VALIDATION_ERROR;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice(assignableTypes = {
        ClientController.class
})
public class GenericControllerAdvice {

    @ExceptionHandler({
        InvalidFieldException.class,
        DataIntegrityViolationException.class
    })
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    private ApiErrorResponse validationErrors(Exception e) {
      var cause = e.getCause();

      var ipe = (GenericException) e;
      if (hasUnderlyingCause(cause)) {
        return new ApiErrorResponse(new ApiError(VALIDATION_ERROR, ipe.getErrorCode(), cause.getCause().getMessage(), null));
      }
      return new ApiErrorResponse(new ApiError(VALIDATION_ERROR, ipe.getErrorCode(),  e.getMessage(), null));
    }

    @ExceptionHandler({
        NotFoundException.class
    })
    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    private ApiErrorResponse handleNotFoundError(Exception e) {
      var cause = e.getCause();

      var ipe = (GenericException) e;
      if (hasUnderlyingCause(cause)) {
        return new ApiErrorResponse(new ApiError(NOT_FOUND_ERROR, ipe.getErrorCode(), cause.getCause().getMessage(), null));
      }
      return new ApiErrorResponse(new ApiError(NOT_FOUND_ERROR, ipe.getErrorCode(),  e.getMessage(), null));
    }

    private boolean hasUnderlyingCause(Throwable cause) {
      return cause != null && cause.getCause() != null;
    }
}
