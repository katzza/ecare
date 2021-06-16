package com.ecare.controllers;

import com.ecare.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {


    @RequestMapping("/error/userexist")
    public String showErrorUserExist() {
        return "error/userexist";
    }

    @RequestMapping("/error/userorpassword")
    public String showErrorUserOrPassword() {
        return "error/userorpassword";
    }

    @RequestMapping("/error/usernotfound")
    public String showErrorUserNotFound() {
        return "error/usernotfound";
    }

    @RequestMapping("/error/baseobjectexception")
    public String showBaseObjectException() {
        return "error/baseobjectexception";
    }


    @RequestMapping(value = "errors", method = RequestMethod.GET) //не работает
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("errors");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Resource not found";
                break;
            }
            case 405: {
                errorMsg = "Http Error Code: 405. Method Not Allowed";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }
}
