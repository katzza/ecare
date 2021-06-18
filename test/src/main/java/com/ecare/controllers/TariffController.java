package com.ecare.controllers;

import com.ecare.dto.TariffDto;
import com.ecare.services.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/tariff")
public class TariffController {

    @Autowired
    private TariffService tariffService;

    @GetMapping("/basetariff")
    public String baseTariff(@RequestParam("tariffId") int tariffId) {
        tariffService.makeBaseTariff(tariffId);
        return "redirect:/tariffs";
    }

    @GetMapping("/delete")
    public String deleteTariff(@RequestParam("tariffId") int tariffId) {
        tariffService.deleteTariff(tariffId);
        return "redirect:/tariffs";
    }

    @GetMapping("/newtariff")
    public String createNewTariff(Model model) {
        TariffDto tariffDto = new TariffDto();
        tariffService.showUniqueCallsOptions(tariffDto);
        tariffService.showUniqueInternetOptions(tariffDto);
        tariffService.showUniqueTravelOptions(tariffDto);
        tariffService.showFreeOptions(tariffDto);  //todo
        model.addAttribute("tariff", tariffDto);
        return "employee/newtariff";
    }

    @PostMapping("/newtariff")
    public String createNewTariff(@ModelAttribute("tariff") @Valid TariffDto tariffDto, Model model) {
        Optional<String> errors = tariffService.saveTariff(tariffDto);
        if (errors.isPresent()) {
            model.addAttribute("message", errors);
            return "employee/newtariff";
        }
        model.addAttribute("tariffId", tariffDto.getTariffId());
        //  model.addAttribute("tariff", tariffDto);  //dto goes to null
        return "redirect:/tariff/setmultioptions";  //take from context-menu! must be link to method, not jsp
    }


    @GetMapping("/updatetariff")
    public String updateTariff(@RequestParam("tariffId") int tariffId, Model model) {
        TariffDto tariff = tariffService.findById(tariffId); //todo to service to one method

        tariffService.showUniqueCallsOptions(tariff);
        tariffService.showUniqueTravelOptions(tariff);
        tariffService.showUniqueInternetOptions(tariff);
        tariffService.showUnselectedFreeOptions(tariff);
        model.addAttribute("tariff", tariff);
        return "employee/updatetariff";
    }

    @PostMapping("/updatetariff")
    public String updateTariff(@ModelAttribute("tariff") @Valid TariffDto tariffDto, Model model) {
        Optional<String> error = tariffService.updateTariff(tariffDto);
        if (error.isPresent()) {
            model.addAttribute("message", error.get());
            return "redirect:/tariffs";
        }
        model.addAttribute("tariffId", tariffDto.getTariffId());
        model.addAttribute("tariffName", tariffDto.getTariffName());
        return "redirect:/tariff/setmultioptions";
    }

    @GetMapping("/setmultioptions")
    public String setMultioptions(@RequestParam("tariffId") int tariffId, Model model) {
        //   public String setMultioptions(@ModelAttribute("tariff") TariffDto tariffDto, Model model, HttpSession session) {
        TariffDto tariff = tariffService.findById(tariffId);
        tariffService.showTariffAddedUniqueOptions(tariff);
        tariffService.showMultipleOptions(tariff);
        model.addAttribute("tariff", tariff);
        return "employee/setmultioptions";
    }

    @PostMapping("/setmultioptions")
    public String setMultioptions(@ModelAttribute("tariff") TariffDto tariffDto, Model model) {
        Optional<String> error = tariffService.setMultioptionsToTariff(tariffDto);
        if (error.isPresent()) {
            model.addAttribute("message", error.get());
            return "employee/setmultioptions";
        }
        model.addAttribute("message", "Tariff was successfully changed");
        return "redirect:/tariffs";
    }

    @RequestMapping("/deletefacultativeoption")
    public String deleteFacultativeOption(@RequestParam("tariffId") int tariffId, @RequestParam("optionId") int optionId, Model model) {
        tariffService.deleteOptionFromTariff(tariffId, optionId);
        model.addAttribute("tariffId", tariffId);
        model.addAttribute("message", "Option was deleted from tariff");
        return "redirect:/tariff/updatetariff";
    }

}
