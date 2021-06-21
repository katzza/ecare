package com.ecare.controllers;

import com.ecare.services.HotTariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HotTariffController {

    @Autowired
    HotTariffService hotTariffService;

    @GetMapping("/hotTariffs")
    public String sendMessage() {
        hotTariffService.sendMessage();
        return "home";
    }
}
