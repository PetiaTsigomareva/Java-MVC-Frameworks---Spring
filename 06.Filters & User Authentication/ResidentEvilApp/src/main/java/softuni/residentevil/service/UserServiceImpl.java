package softuni.residentevil.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.residentevil.domain.entities.User;
import softuni.residentevil.domain.enums.Roles;
import softuni.residentevil.domain.models.service.UserRoleServiceModel;
import softuni.residentevil.domain.models.service.UserServiceModel;
import softuni.residentevil.repositories.UserRepository;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        UserServiceModel result;

        this.userRoleService.seedRoles();
        if (this.userRepository.count() == 0) {

            userServiceModel.setAuthorities(this.userRoleService.allRoles());

        } else {
            userServiceModel.setAuthorities(new HashSet<>());
            UserRoleServiceModel userRoleServiceModel = this.userRoleService.findByAuthority(Roles.ROLE_USER.toString());
            userServiceModel.getAuthorities().add(userRoleServiceModel);
        }

        User user = this.modelMapper.map(userServiceModel, User.class);

        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        user = this.userRepository.save(user);
        result = this.modelMapper.map(user, UserServiceModel.class);
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username is not found!"));
    }
}
