package softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.residentevil.cofigurations.Constants;
import softuni.residentevil.domain.models.binding.UserRegisterBindingModel;
import softuni.residentevil.domain.models.service.UserServiceModel;
import softuni.residentevil.service.UserService;

@Controller
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(Constants.REGISTER_FORM_ACTION)
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(@ModelAttribute(name = "registerModel") UserRegisterBindingModel registerModel) {
        return super.view(Constants.REGISTER_PAGE);
    }

    @PostMapping(Constants.REGISTER_FORM_ACTION)
    public ModelAndView registerConfirm(@ModelAttribute(name = "registerModel") UserRegisterBindingModel registerModel, ModelAndView modelAndView) {

        if (!registerModel.getPassword().equalsIgnoreCase(registerModel.getConfirmPassword())) {
            return super.view(Constants.REGISTER_PAGE);
        }
        this.userService.registerUser(this.modelMapper.map(registerModel, UserServiceModel.class));

        return super.redirect(Constants.LOGIN_FORM_ACTION);
    }

    @GetMapping(Constants.LOGIN_FORM_ACTION)
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {
        return super.view(Constants.LOGIN_PAGE);
    }


}
