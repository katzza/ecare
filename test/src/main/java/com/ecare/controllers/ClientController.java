package com.ecare.controllers;

import com.ecare.dto.TariffDto;
import com.ecare.services.ContractService;
import com.ecare.services.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClientController {


    @Autowired
    private TariffService tariffService;

    @Autowired
    private ContractService contractService;

    @RequestMapping("tariffs")
    public String getAllTariffs(Model model) {
        List<TariffDto> listTariffs = tariffService.getAllTariffs();
        for (TariffDto tariff :
                listTariffs) {
            tariffService.showTariffAddedMultiFreeOptions(tariff);
        }
        model.addAttribute("tariffs", listTariffs);
        return "client/tariffs";
    }


    @GetMapping("/client/block")
    public String blockContract(@RequestParam("contractId") int id) {
        contractService.blockContractByClient(id);
        return "redirect:/client/homepage";
    }

    @GetMapping("/client/unblock")
    public String unblockContract(@RequestParam("contractId") int id) {
        contractService.unblockContractByClient(id);
        return "redirect:/client/homepage";
    }


}
