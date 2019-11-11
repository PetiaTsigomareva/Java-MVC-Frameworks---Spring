package softuni.residentevil.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.residentevil.domain.entities.Capital;
import softuni.residentevil.domain.models.service.CapitalServiceModel;
import softuni.residentevil.repositories.CapitalRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CapitalServiceImpl implements CapitalService {
    private final CapitalRepository capitalRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CapitalServiceImpl(CapitalRepository capitalRepository, ModelMapper modelMapper) {
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CapitalServiceModel> findAllCapitals() {
        List<Capital> capitals1Entities = this.capitalRepository.findAll();
        List<CapitalServiceModel> capitals = capitals1Entities.stream().map(ce -> this.modelMapper.map(ce, CapitalServiceModel.class)).collect(Collectors.toList());

        return capitals;
    }
}
