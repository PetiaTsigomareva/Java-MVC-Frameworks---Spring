package softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.residentevil.domain.models.binding.VirusBindingModel;
import softuni.residentevil.domain.models.binding.VirusEditBindingModel;
import softuni.residentevil.domain.models.service.CapitalServiceModel;
import softuni.residentevil.domain.models.service.VirusServiceModel;
import softuni.residentevil.domain.models.view.CapitalNamesViewModel;
import softuni.residentevil.domain.models.view.VirusDeleteViewModel;
import softuni.residentevil.service.CapitalService;
import softuni.residentevil.service.VirusService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/viruses")
public class VirusController extends BaseController {
    private final CapitalService capitalService;
    private final ModelMapper modelMapper;
    private final VirusService virusService;

    @Autowired
    public VirusController(CapitalService capitalService, ModelMapper modelMapper, VirusService virusService) {
        this.capitalService = capitalService;
        this.modelMapper = modelMapper;
        this.virusService = virusService;
    }

    @GetMapping("/add")
    public ModelAndView add(ModelAndView modelAndView, @ModelAttribute(name = "virusBindingModel") VirusBindingModel virusBindingModel) {
        modelAndView.addObject("capitalNames", getCapitalNames());
        modelAndView.addObject("virusBindingModel", virusBindingModel);

        return super.view("add-virus", modelAndView);
    }



    @PostMapping("/add")
    public ModelAndView addConfirm(ModelAndView modelAndView, @Valid @ModelAttribute(name = "virusBindingModel") VirusBindingModel virusBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("capitalNames", getCapitalNames());
            modelAndView.addObject("virusBindingModel", virusBindingModel);
            return super.view("add-virus", modelAndView);
        }

        VirusServiceModel virusServiceModel = this.modelMapper.map(virusBindingModel, VirusServiceModel.class);
        this.virusService.saveVirus(virusServiceModel);

        return super.redirect("/");
    }


    @GetMapping("/show")
    public ModelAndView show(ModelAndView modelAndView) {
        List<VirusServiceModel> viruses = this.virusService.allViruses();

        modelAndView.addObject("viruses", viruses);

        return super.view("show-viruses", modelAndView);

    }
//TODO edit form to select virus capital from select view
    @GetMapping("/edit/{id}")
    public ModelAndView edit(ModelAndView modelAndView, @PathVariable(name = "id") String id, @ModelAttribute(name = "virusEditBindingModel") VirusEditBindingModel virusEditBindingModel) {

        modelAndView.addObject("capitalNames", getCapitalNames());
        modelAndView.addObject("virusCapitals", this.virusService.findCapitalsByVirusId(id));
        modelAndView.addObject("virusEditBindingModel", this.modelMapper.map(this.virusService.findById(id), VirusEditBindingModel.class));
        return super.view("edit", modelAndView);
    }
    //TODO edit form to select virus capital from select view
    @PostMapping("/edit/{id}")
    public ModelAndView editConfirm(ModelAndView modelAndView, @PathVariable(name = "id") String id, @Valid @ModelAttribute(name = "virusEditBindingModel") VirusEditBindingModel virusEditBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("id", id);
            modelAndView.addObject("capitalNames", getCapitalNames());
            modelAndView.addObject("virusCapitals", this.virusService.findCapitalsByVirusId(id));
            modelAndView.addObject("virusEditBindingModel", virusEditBindingModel);
            return super.view("edit", modelAndView);
        }

        VirusServiceModel virusServiceModel = this.modelMapper.map(virusEditBindingModel, VirusServiceModel.class);
        virusServiceModel.setId(id);
        this.virusService.editVirus(virusServiceModel);

        return super.redirect("/");
    }
    @GetMapping("/delete/{id}")
    public ModelAndView delete(ModelAndView modelAndView, @PathVariable(name = "id") String id, @ModelAttribute(name = "virusDeleteViewModel") VirusDeleteViewModel virusDeleteViewModel) {

        modelAndView.addObject("capitalNames", getCapitalNames());
        modelAndView.addObject("virusCapitals", this.virusService.findCapitalsByVirusId(id));
        modelAndView.addObject("id", id);
        modelAndView.addObject("virusDeleteViewModel", this.modelMapper.map(this.virusService.findById(id), VirusDeleteViewModel.class));
        return super.view("delete-virus", modelAndView);
    }


    @PostMapping("/delete/{id}")
    public ModelAndView deleteConfirm(ModelAndView modelAndView, @PathVariable(name = "id") String id, @ModelAttribute(name = "virusDeleteViewModel") VirusDeleteViewModel virusDeleteViewModel) {
        VirusServiceModel virusServiceModel = this.modelMapper.map(virusDeleteViewModel, VirusServiceModel.class);
        virusServiceModel.setId(id);
        this.virusService.deleteVirus(virusServiceModel);

        return super.redirect("/");
    }

    private List<CapitalNamesViewModel> getCapitalNames() {
        List<CapitalNamesViewModel> result = this.capitalService.findAllCapitals().stream().map(c -> this.modelMapper.map(c, CapitalNamesViewModel.class)).collect(Collectors.toList());

        return result;
    }




}
