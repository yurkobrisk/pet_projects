package by.korziuk.qr_code_web_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QRCodeController {

    @GetMapping("/")
    public String getText(Model model){
        System.out.println("method getText");
        model.addAttribute("inputText", "This is the default attribute.");
        return "index.html";
    }

    @PostMapping("/")
    public String setQRCode(@ModelAttribute(name = "inputText") String inputText){
        System.out.println("method setQRCode");
        return "index.html";
    }
}
