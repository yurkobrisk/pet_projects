package by.korziuk.qr_code_web_app.controller;

import by.korziuk.qr_code_web_app.domain.QRCode;
import by.korziuk.qr_code_web_app.service.IQRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QRCodeController {

    @Autowired
    IQRCodeService qrCodeService;

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
        if (!inputText.isEmpty()){
            String imageBLOB;
            if (qrCodeService.existsByDescription(inputText)){
                imageBLOB = qrCodeService.findByDescription(inputText).getBinaryImage();
            } else {
                imageBLOB = qrCodeService.createImageQr(inputText);
                qrCodeService.saveQRCode(new QRCode(inputText, imageBLOB));
            }
            model.addAttribute("imageBLOB", imageBLOB);
            model.addAttribute("isTextPresent", "true");
        } else {
            model.addAttribute("isTextPresent", "false");
        }
        return "index.html";
    }

    @GetMapping("/error")
    public String error(){
        return "error.html";
    }
}
