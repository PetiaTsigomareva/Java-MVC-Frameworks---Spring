package softuni.residentevil.service;

import softuni.residentevil.domain.models.service.UserRoleServiceModel;
import softuni.residentevil.domain.models.service.UserServiceModel;

import java.util.Set;

public interface UserRoleService {

    void seedRoles();

    Set<UserRoleServiceModel> allRoles();

    UserRoleServiceModel findByAuthority(String authority);
}
