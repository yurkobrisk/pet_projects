package by.korziuk.web_pass_generator.controller;

import by.korziuk.web_pass_generator.service.IGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GeneratorController {

    @Autowired
    IGeneratorService generator;

    @GetMapping("/")
    public String getSizePassword(Model model) {
        model.addAttribute("sizePassword", "20");
        model.addAttribute("init", "0");
        return "index.html";
    }

    @PostMapping("/")
    public String setPassword(
            Model model,
            @ModelAttribute(name = "sizePassword") String size,
            @ModelAttribute(name = "init") String init) {

        model.addAttribute("init", "1");
        if (generator.isNumber(size)) {
            int parsedSize = Integer.parseInt(size);
            if (parsedSize >= 8 && parsedSize <= 20) {
                model.addAttribute("password", generator.generatePassword(parsedSize));
                model.addAttribute("error", "false");
            } else {
                model.addAttribute("error", "true");
            }
        } else {
            model.addAttribute("error", "true");
        }
        return "index.html";
    }
}
