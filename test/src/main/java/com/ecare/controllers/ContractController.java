package com.ecare.controllers;

import com.ecare.dto.ClientDto;
import com.ecare.dto.ContractDto;
import com.ecare.services.ClientService;
import com.ecare.services.ContractFacade;
import com.ecare.services.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ContractController {

    @Autowired
    ContractFacade contractFacade;
    @Autowired
    private ClientService clientService;

    @GetMapping("/contract/newcontract")
    public String createContract(@RequestParam("clientId") int clientId, Model model) {
        ContractDto contractDto = contractFacade.prepareNewContract(clientId);
        model.addAttribute("contract", contractDto);
        return "client/newcontract";
    }

    @PostMapping("/contract/newcontract")
    public String createContract(@ModelAttribute("contract") ContractDto contractDto, Model model) {
        Optional<String> error = contractFacade.save(contractDto);
        if (error.isPresent()) {
            model.addAttribute("message", error.get());
            contractFacade.showTariffandOptions(contractDto);
            return "client/newcontract";
        }
        model.addAttribute("message", "New contract was successfully added");
        model.addAttribute("client", clientService.findById(contractDto.getClientId()));
        return "client/clientinfo";
    }

    @GetMapping("/contract/editcontract")
    public String editContract(@RequestParam("contractId") int id, Model model) {
        ContractDto contractDto = contractFacade.findById(id);
        if (contractFacade.isBlocked(contractDto)) {
            model.addAttribute("message", "Contract " + contractDto.getPhoneNumber().getPhoneNumber() + " is blocked - it cannot be changed");
            model.addAttribute("client", clientService.findById(contractDto.getClientId()));
            return "client/clientinfo";
        }
        contractFacade.showTariffandOptions(contractDto);
        model.addAttribute("contract", contractDto);
        model.addAttribute("client", clientService.findByPhone(contractDto.getPhoneNumber().getPhoneNumber()));
        return "client/updatecontract";
    }

    @PostMapping("/contract/editcontract")
    public String editContract(@ModelAttribute("contract") ContractDto contractDto, Model model) {
        contractFacade.updateContract(contractDto);
        model.addAttribute("message", "Contract " + contractDto.getPhoneNumber().getPhoneNumber() + " was successfully changed");
        model.addAttribute("client", clientService.findByPhone(contractDto.getPhoneNumber().getPhoneNumber()));
        return "client/clientinfo";
    }
}
