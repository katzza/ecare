package com.ecare.controllers;

import com.ecare.dto.ContractDto;
import com.ecare.services.ClientService;
import com.ecare.services.ContractFacade;
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
        try {
            int contractId = contractFacade.save(contractDto);
           /* model.addAttribute("contractId", contractId);
            return "redirect:/contract/setmultioptions";*/
            model.addAttribute("message", "New contract was successfully added");
            model.addAttribute("client", clientService.findById(contractDto.getClientId()));
            return "client/clientinfo";
        } catch (Exception ex) {
            model.addAttribute("message", ex.getMessage());
            contractFacade.showAllTariffs(contractDto);
            return "client/newcontract";
        }
        //  model.addAttribute("tariffId", contractDto.getTariffId());

    }

/*    @GetMapping("/contract/setmultioptions")
    public String setOptionsToContract(@RequestParam("contractId") int contractId, Model model) {
        ContractDto contractDto = contractFacade.prepareNewContractToSetOptions(contractId);
        model.addAttribute("contract", contractDto);
        return "employee/addcontractoptions";
    }*/

   /* @PostMapping("/contract/setmultioptions")
    public String setMultioptions(@ModelAttribute("contract") ContractDto contractDto, Model model) {
        try {
            contractFacade.saveOptionsToContract(contractDto);
        } catch (Exception ex) {
            model.addAttribute("message", ex.getMessage());
            return "employee/addcontractoptions";
        }
        model.addAttribute("message", "New contract was successfully added");
        model.addAttribute("client", clientService.findById(contractDto.getClientId()));
        return "client/clientinfo";
    }*/


    @GetMapping("/contract/editcontract")
    public String editContract(@RequestParam("contractId") int id, Model model) {
        ContractDto contractDto = contractFacade.findById(id);
        if (contractFacade.isBlocked(contractDto)) {
            model.addAttribute("message", "Contract " + contractDto.getPhoneNumber().getPhoneNumber() + " is blocked - it cannot be changed");
            model.addAttribute("client", clientService.findById(contractDto.getClientId()));
            return "client/clientinfo";
        }
        contractFacade.showAllTariffs(contractDto);
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
