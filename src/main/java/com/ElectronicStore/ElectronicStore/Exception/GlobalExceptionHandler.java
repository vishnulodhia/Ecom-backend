package com.ElectronicStore.ElectronicStore.Exception;


import com.ElectronicStore.ElectronicStore.DTO.APiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

   @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){

       logger.info("Exception Handler invoked");
   APiResponseMessage response = APiResponseMessage.builder().message(ex.getMessage()).success(true).status(HttpStatus.NOT_FOUND).build();
   return new ResponseEntity(response,HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<Map<String,Object>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){
     List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
     Map<String,Object> response = new HashMap<>();

     allErrors.stream().forEach(objectError -> {
         response.put(((FieldError) objectError).getField(),objectError.getDefaultMessage());
     } );

     return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

   }


    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<APiResponseMessage> handleBadApiRequest(ResourceNotFoundException ex){

        logger.info("Bad api request");
        APiResponseMessage response = APiResponseMessage.builder().message(ex.getMessage()).success(false).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity(response,HttpStatus.NOT_FOUND);
    }






}
