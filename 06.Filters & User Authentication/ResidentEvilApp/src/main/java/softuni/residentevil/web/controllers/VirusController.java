package softuni.residentevil.web.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import softuni.residentevil.domain.models.binding.VirusBindingModel;
import softuni.residentevil.domain.models.binding.VirusEditBindingModel;
import softuni.residentevil.domain.models.service.VirusServiceModel;
import softuni.residentevil.domain.models.view.CapitalNamesViewModel;
import softuni.residentevil.domain.models.view.VirusDeleteViewModel;
import softuni.residentevil.service.CapitalService;
import softuni.residentevil.service.VirusService;

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
	@PreAuthorize("isAuthenticated()")
	public ModelAndView add(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") VirusBindingModel virusBindingModel) {
		modelAndView.addObject("capitalNames", getCapitalNames());
		modelAndView.addObject("bindingModel", virusBindingModel);

		return super.view("add-virus", modelAndView);
	}

	@PostMapping("/add")
	public ModelAndView addConfirm(ModelAndView modelAndView, @Valid @ModelAttribute(name = "bindingModel") VirusBindingModel virusBindingModel, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("capitalNames", getCapitalNames());
			modelAndView.addObject("bindingModel", virusBindingModel);
			return super.view("add-virus", modelAndView);
		}

		VirusServiceModel virusServiceModel = this.modelMapper.map(virusBindingModel, VirusServiceModel.class);
		this.virusService.saveVirus(virusServiceModel);

		return super.redirect("/");
	}

	@GetMapping("/show")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView show(ModelAndView modelAndView) {
		List<VirusServiceModel> viruses = this.virusService.allViruses();

		modelAndView.addObject("viruses", viruses);

		return super.view("show", modelAndView);

	}

	// TODO edit form to select virus capital from select view
	@GetMapping("/edit/{id}")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView edit(ModelAndView modelAndView, @PathVariable(name = "id") String id, @ModelAttribute(name = "bindingModel") VirusEditBindingModel virusEditBindingModel) {
		modelAndView.addObject("capitalNames", getCapitalNames());
		modelAndView.addObject("virusCapitals", this.virusService.findCapitalsByVirusId(id));
		modelAndView.addObject("bindingModel", this.modelMapper.map(this.virusService.findById(id), VirusEditBindingModel.class));
		return super.view("edit", modelAndView);
	}

	// TODO edit form to select virus capital from select view
	@PutMapping("/edit/{id}")
	public ModelAndView editConfirm(ModelAndView modelAndView, @PathVariable(name = "id") String id,
	    @Valid @ModelAttribute(name = "bindingModel") VirusEditBindingModel virusEditBindingModel, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("id", id);
			modelAndView.addObject("capitalNames", getCapitalNames());
			modelAndView.addObject("virusCapitals", this.virusService.findCapitalsByVirusId(id));
			modelAndView.addObject("bindingModel", virusEditBindingModel);
			return super.view("edit", modelAndView);
		}

		VirusServiceModel virusServiceModel = this.modelMapper.map(virusEditBindingModel, VirusServiceModel.class);
		virusServiceModel.setId(id);
		this.virusService.editVirus(virusServiceModel);

		return super.redirect("/");
	}

	@GetMapping("/delete/{id}")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView delete(ModelAndView modelAndView, @PathVariable(name = "id") String id, @ModelAttribute(name = "bindingModel") VirusDeleteViewModel virusDeleteViewModel) {

		modelAndView.addObject("capitalNames", getCapitalNames());
		modelAndView.addObject("virusCapitals", this.virusService.findCapitalsByVirusId(id));
		modelAndView.addObject("id", id);
		modelAndView.addObject("bindingModel", this.modelMapper.map(this.virusService.findById(id), VirusDeleteViewModel.class));
		return super.view("delete-virus", modelAndView);
	}

	@DeleteMapping("/delete/{id}")
	public ModelAndView deleteConfirm(ModelAndView modelAndView, @PathVariable(name = "id") String id,
	    @ModelAttribute(name = "bindingModel") VirusDeleteViewModel virusDeleteViewModel) {
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
