package com.ecare.controllers;

import com.ecare.dto.ClientDto;
import com.ecare.dto.UserDto;
import com.ecare.services.ClientService;
import com.ecare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;

    @RequestMapping("/ecare")
    public String showFirstView() {
        return "home";
    }

    @GetMapping("/client/homepage")
    public String showClient(Model model, Principal principal) {
        ClientDto clientDto = clientService.findClientByUserEmail(principal.getName());
        model.addAttribute("client", clientDto);
        return "client/clientinfo";
    }

    @GetMapping("/employee/employeehomepage")
    public String showEmployeeHomePage(Model model, Principal principal) {
        UserDto userDto = userService.findByEmail(principal.getName());
        model.addAttribute("user", userDto);

        return "employee/employeepage";
    }

    @RequestMapping("/admin/console")
    public String showAdminHomePage() {
        return "admin/console";
    }

}
