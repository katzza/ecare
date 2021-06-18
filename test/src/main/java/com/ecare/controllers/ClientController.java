package com.ecare.controllers;

import com.ecare.dto.TariffDto;
import com.ecare.services.ContractFacade;
import com.ecare.services.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@Controller
public class ClientController {

    private final TariffService tariffService;

    private final ContractFacade contractFacade;

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
        contractFacade.blockContractByClient(id);
        return "redirect:/client/homepage";
    }

    @GetMapping("/client/unblock")
    public String unblockContract(@RequestParam("contractId") int id) {
        contractFacade.unblockContractByClient(id);
        return "redirect:/client/homepage";
    }


}
