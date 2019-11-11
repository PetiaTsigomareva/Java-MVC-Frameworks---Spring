package softuni.residentevil.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import softuni.residentevil.domain.models.service.UserServiceModel;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserServiceModel userServiceModel);
}
