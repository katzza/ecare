package com.ecare.controllers;

import com.ecare.dto.ClientDto;
import com.ecare.dto.OptionDto;
import com.ecare.dto.TariffDto;
import com.ecare.services.*;
import com.ecare.dto.UserDto;
import com.ecare.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;
    @Autowired
    ContractService contractService;
    @Autowired
    TariffService tariffService;
    @Autowired
    OptionService optionService;


    @RequestMapping("/users")
    public String getAllUsers(Model model) {
        List<ClientDto> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "employee/users";
    }

    @RequestMapping("/clients")
    public String getAllClients(Model model) {
        List<ClientDto> listClients = clientService.getAllClients();
        model.addAttribute("clients", listClients);
        return "employee/clients";
    }

    @RequestMapping("/options")
    public String getAllOptions(Model model) {
        List<OptionDto> listOptions = optionService.getAllOptions();
        model.addAttribute("options", listOptions);
        return "employee/options";
    }

    @GetMapping("/findclientbyphone")
    public String findClientByPhone(@RequestParam(value = "phone") String phone, Model model) {
        ClientDto clientDto = clientService.findByPhone(phone);
        if (clientDto == null) {
            model.addAttribute("message", "Client with phone " + phone + " does not exist");
            return "/employee/employeepage";
        }
        model.addAttribute("client", clientDto);
        return "/client/clientinfo";
    }

    @GetMapping("/findclientbyemail")
    public String findClientByEmail(@RequestParam(value = "email") String email, Model model) {
        ClientDto clientDto = clientService.findClientByUserEmail(email);
        if (clientDto == null) {
            model.addAttribute("message", "Client with email " + email + " does not exist");
            return "/employee/employeepage";
        }
        model.addAttribute("client", clientDto);
        return "/client/clientinfo";
    }

    @GetMapping("/newc")
    public String createNewClient(@RequestParam("email") String email, Model model) {
        ClientDto clientDto = clientService.findClientByUserEmail(email);
        if (clientDto == null) {
            model.addAttribute("message", "Client with email " + email + " does not exist");
            return "/employee/employeepage";
        }
        model.addAttribute("client", clientDto);
        return "/client/clientinfo";
    }

    @PostMapping("/saveclient")
    public String saveClient(@ModelAttribute("clientDTO") ClientDto clientDto) {
        clientService.saveClient(clientDto);
        return "employee/clients";
    }


    @GetMapping("/editclient") //works
    public String editClient(@RequestParam("clientId") int id, Model model) {
        model.addAttribute("client", clientService.findById(id));
        return "employee/clientdata";
    }

    @PostMapping("/editclient")
    public String editClient(@ModelAttribute("client") ClientDto clientDto, Model model) {
        clientService.updateClient(clientDto);
        model.addAttribute("client", clientService.findById(clientDto.getClientId()));
        return "client/clientinfo";
    }

    @GetMapping("/block")
    public String blockContractByCompany(@RequestParam("contractId") int id, Model model) {
        int clientId = contractService.blockContractByCompany(id);
        return getClientInfoPage(model, clientId);
    }


    @GetMapping("/unblock")
    public String unblockContract(@RequestParam("contractId") int id, Model model) {
        int clientId = contractService.unblockContractByCompany(id);
        return getClientInfoPage(model, clientId);
    }

    private String getClientInfoPage(Model model, int clientId) {
        ClientDto clientDto = clientService.findById(clientId);
        if (clientDto == null) {
            model.addAttribute("message", "Client with Id " + clientId + " does not exist");
            return "/employee/employeepage";
        }
        model.addAttribute("client", clientDto);
        return "/client/clientinfo";
    }

}
