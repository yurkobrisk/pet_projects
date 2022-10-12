package by.korziuk.qr_code_web_app.controller;

import by.korziuk.qr_code_web_app.service.QRCodeService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class QRCodeController {

    @Autowired
    QRCodeService qrCodeService;

    @GetMapping("/")
    public String getText(Model model){
        model.addAttribute("inputText", "");
        model.addAttribute("isTextPresent", "false");
        return "index.html";
    }

    @PostMapping("/")
    public String setQRCode(
            Model model,
            @ModelAttribute(name = "inputText") String inputText){
        try {
            if (!inputText.isEmpty()){
                String imageBLOB = qrCodeService.createImageQR(inputText);
                model.addAttribute("imageBLOB", imageBLOB);
                model.addAttribute("isTextPresent", "true");
            } else {
                model.addAttribute("isTextPresent", "false");
            }
        } catch (WriterException | IOException e) {
            throw new RuntimeException("The image writer has error");
        }
        return "index.html";
    }

    @GetMapping("/error")
    public String error(){
        return "error.html";
    }
}
