package com.ecare.controllers;

import com.ecare.dto.OptionDto;
import com.ecare.services.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/option")   //todo pagination
public class OptionController {

    @Autowired
    private OptionService optionService;

    @GetMapping("/newoption")
    public String createNewOption(Model model) {
        OptionDto optionDto = new OptionDto();
        optionService.showBaseOptions(optionDto);
        // optionService.showAdditionalOptions(optionDto);
        model.addAttribute("option", optionDto);
        return "employee/newoption";
    }

    @PostMapping("/newoption")
    public String createNewOption(@ModelAttribute("option") @Valid OptionDto optionDto, Model model) {
        Optional<String> error = optionService.saveOption(optionDto);
        if (error.isPresent()) {
            model.addAttribute("message", error.get());
            return "employee/newoption";
        }
        return "redirect:/employee/options";
    }

    @GetMapping("/updateoption")
    public String updateOption(@RequestParam("optionId") int optionId, Model model) {
        OptionDto optionDto = optionService.findById(optionId);
        optionService.showBaseOptions(optionDto);
         model.addAttribute("option", optionDto);
        return "employee/updateoption";
    }

    @PostMapping("/updateoption")
    public String updateOption(@ModelAttribute("option") @Valid OptionDto option, Model model) {
        Optional<String> error = optionService.updateOption(option);
        if (error.isPresent()) {
            model.addAttribute("message", error.get());
            return "employee/updateoption";
        }
      /*  model.addAttribute("message", "Option was successfully changed");*/
        return "redirect:/employee/options";
    }

}
