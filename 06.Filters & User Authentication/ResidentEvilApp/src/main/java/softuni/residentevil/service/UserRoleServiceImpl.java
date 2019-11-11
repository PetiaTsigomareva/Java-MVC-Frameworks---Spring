package softuni.residentevil.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.residentevil.domain.entities.UserRole;
import softuni.residentevil.domain.enums.Roles;
import softuni.residentevil.domain.models.service.UserRoleServiceModel;
import softuni.residentevil.domain.models.service.UserServiceModel;
import softuni.residentevil.repositories.UserRoleRepository;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedRoles() {
        if (this.userRoleRepository.count() == 0) {
            for (Roles r : Roles.values()) {
                this.userRoleRepository.save(new UserRole(r.name()));
            }
        }

    }

    @Override
    public Set<UserRoleServiceModel> allRoles() {
        return this.userRoleRepository.findAll().stream().map(r -> this.modelMapper.map(r, UserRoleServiceModel.class)).collect(Collectors.toSet());
    }

    @Override
    public UserRoleServiceModel findByAuthority(String authority) {
        UserRoleServiceModel result = this.modelMapper.map(this.userRoleRepository.findByAuthority(authority), UserRoleServiceModel.class);
        return result;
    }


}
