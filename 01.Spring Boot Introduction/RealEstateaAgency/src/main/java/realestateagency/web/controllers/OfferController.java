package realestateagency.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import realestateagency.domain.models.binding.OfferFindBindingModel;
import realestateagency.domain.models.binding.OfferRegisterBindingModel;
import realestateagency.domain.models.service.OfferServiceModel;
import realestateagency.service.OfferService;

import java.util.List;

@Controller
public class OfferController {
    private final OfferService offerService;
    private final ModelMapper modelMapper;

    @Autowired
    public OfferController(OfferService offerService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/reg")
    public String getRegisterForm() {
        return "register.html";
    }

    @PostMapping("/reg")
    public String registerConfirm(@ModelAttribute(name = "model") OfferRegisterBindingModel model) {
        String result;
        try {
            this.offerService.registerOffer(this.modelMapper.map(model, OfferServiceModel.class));
            result = "redirect:/";
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            result = "redirect:/reg";
        }
        return result;
    }

    @GetMapping("/find")
    public String getFindForm() {
        return "find.html";
    }

    @PostMapping("/find")
    public String findConfirm(@ModelAttribute(name = "model") OfferFindBindingModel model) {

        String result;
        try {
            this.offerService.findOffer(model);
            result = "redirect:/";
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            result = "redirect:/find";

        }
        return result;
    }


}
