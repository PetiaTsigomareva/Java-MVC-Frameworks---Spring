package softuni.residentevil.service;

import softuni.residentevil.domain.models.service.CapitalServiceModel;
import softuni.residentevil.domain.models.service.VirusServiceModel;

import java.util.List;

public interface VirusService {
    String saveVirus(VirusServiceModel virusServiceModel);
    List<VirusServiceModel> allViruses();
    VirusServiceModel findById(String id);
    String editVirus(VirusServiceModel virusServiceModel);
    void deleteVirus(VirusServiceModel virusServiceModel);
    List<String> findCapitalsByVirusId(String id);


}
