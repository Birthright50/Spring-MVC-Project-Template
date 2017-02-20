package com.birthright.web.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by birth on 01.02.2017.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    //  @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String pageNotFound() {
        return "redirect:/404";
    }

    @ExceptionHandler(MultipartException.class)
    public RedirectView uploadError(MultipartException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
        return new RedirectView("/uploadStatus");
    }


}
