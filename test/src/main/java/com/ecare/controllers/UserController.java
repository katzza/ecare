package com.ecare.controllers;

import com.ecare.dto.UserDto;
import com.ecare.error.UserAlreadyExistException;
import com.ecare.error.UserNotFoundException;
import com.ecare.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.file.attribute.UserPrincipalNotFoundException;

@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")   //JSP Action
    public String createNewUser(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "newuser";
    }

    @PostMapping("/saveuser")
    public String saveUser(@ModelAttribute("userDto") @Valid UserDto userDto, HttpServletRequest request) {
        userService.saveUser(userDto);
        userService.authWithHttpServletRequest(request, userDto.getEmail(), userDto.getPassword());
        return "redirect:/client/homepage";
    }

    @GetMapping("/login")
    public ModelAndView signIn() {
        return new ModelAndView();
    }

}
