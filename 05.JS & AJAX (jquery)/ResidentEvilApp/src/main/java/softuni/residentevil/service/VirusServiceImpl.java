package softuni.residentevil.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.residentevil.domain.entities.Capital;
import softuni.residentevil.domain.entities.Virus;
import softuni.residentevil.domain.models.service.CapitalServiceModel;
import softuni.residentevil.domain.models.service.VirusServiceModel;
import softuni.residentevil.repositories.CapitalRepository;
import softuni.residentevil.repositories.VirusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VirusServiceImpl implements VirusService {
    private final VirusRepository virusRepository;
    private final ModelMapper modelMapper;
    private final CapitalRepository capitalRepository;


    @Autowired
    public VirusServiceImpl(VirusRepository virusRepository, ModelMapper modelMapper, CapitalRepository capitalRepository) {
        this.virusRepository = virusRepository;
        this.modelMapper = modelMapper;
        this.capitalRepository = capitalRepository;
    }

    @Override
    public String saveVirus(VirusServiceModel virusServiceModel) {
        String result;
        List<Capital> virusCapitals = getCapitals(virusServiceModel.getCapitals());

        Virus virus = this.modelMapper.map(virusServiceModel, Virus.class);
        virus.setCapitals(virusCapitals);
        try {
            virus = this.virusRepository.saveAndFlush(virus);
            result = virus.getId();

        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            result = null;
        }

        return result;
    }

    @Override
    public List<VirusServiceModel> allViruses() {
        List<VirusServiceModel> result = this.virusRepository.findAll().stream().map(v -> this.modelMapper.map(v, VirusServiceModel.class)).collect(Collectors.toList());
        return result;
    }

    @Override
    public VirusServiceModel findById(String id) {
        VirusServiceModel result;
        try {
            Virus virus = this.virusRepository.findById(id).orElse(null);
            result = this.modelMapper.map(virus, VirusServiceModel.class);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            result = null;
        }
        return result;
    }

    //TODO
    @Override
    public String editVirus(VirusServiceModel virusServiceModel) {
        String result;
        try {

            List<Capital> virusCapitals = getCapitals(virusServiceModel.getCapitals());
            Virus editVirus = this.virusRepository.findById(virusServiceModel.getId()).orElse(null);
            editVirus = this.modelMapper.map(virusServiceModel, Virus.class);
            editVirus.setCapitals(virusCapitals);
            editVirus = this.virusRepository.saveAndFlush(editVirus);
            result = editVirus.getId();

        } catch (NullPointerException ex) {
            ex.printStackTrace();
            result = null;
        }
        return result;
    }

    @Override
    public void deleteVirus(VirusServiceModel virusServiceModel) {
        Virus virus = this.modelMapper.map(virusServiceModel, Virus.class);
        this.virusRepository.delete(virus);
    }



    private List<Capital> getCapitals(List<String> ids) {
        List<Capital> capitals = new ArrayList<>();

        for (String id : ids) {
            Capital capital = this.capitalRepository.findById(id).orElse(null);
            capitals.add(capital);
        }

        return capitals;


    }

    @Override
    public List<String> findCapitalsByVirusId(String id) {
        List<String> result;
        try {
            Virus virus = this.virusRepository.findById(id).orElse(null);
            result = virus.getCapitals().stream().map(c -> this.modelMapper.map(c.getName(), String.class)).collect(Collectors.toList());
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            result = null;
        }

        return result;
    }


}
