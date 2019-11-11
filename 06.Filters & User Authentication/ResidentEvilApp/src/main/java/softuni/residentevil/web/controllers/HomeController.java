package softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import softuni.residentevil.cofigurations.Constants;
import softuni.residentevil.domain.models.service.VirusServiceModel;
import softuni.residentevil.domain.models.view.CapitalsViewModel;
import softuni.residentevil.domain.models.view.VirusesViewModel;
import softuni.residentevil.service.CapitalService;
import softuni.residentevil.service.VirusService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {
    private final VirusService virusService;
    private final CapitalService capitalService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(VirusService virusService, CapitalService capitalService, ModelMapper modelMapper) {
        this.virusService = virusService;
        this.capitalService = capitalService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(Constants.INDEX_ACTION)
    @PreAuthorize("isAnonymous()")
    public ModelAndView index() {
        return super.view("index");
    }

    @GetMapping(Constants.HOME_ACTION)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home() {
        return super.view("index");
    }

    @GetMapping(value = "/viruses", produces = "application/json")
    @ResponseBody
    public Object virusesData() {

        List<VirusesViewModel> virusesViewModel = this.virusService.allViruses().stream().map(virus -> this.modelMapper.map(virus, VirusesViewModel.class)).collect(Collectors.toUnmodifiableList());

        return virusesViewModel;
    }

    @GetMapping(value = "/capitals", produces = "application/json")
    @ResponseBody
    public Object capitalsData() {
        List<CapitalsViewModel> capitalsViewModels = this.capitalService.findAllCapitals().stream().map(capital -> this.modelMapper.map(capital, CapitalsViewModel.class)).collect(Collectors.toUnmodifiableList());
        return capitalsViewModels;
    }
}
