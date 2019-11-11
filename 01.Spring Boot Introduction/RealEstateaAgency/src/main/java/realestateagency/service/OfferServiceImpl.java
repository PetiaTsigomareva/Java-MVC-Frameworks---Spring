package realestateagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import realestateagency.domain.entities.Offer;
import realestateagency.domain.models.binding.OfferFindBindingModel;
import realestateagency.domain.models.service.OfferServiceModel;
import realestateagency.repository.OfferRepository;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, Validator validator, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerOffer(OfferServiceModel offerServiceModel) {

        if (this.validator.validate(offerServiceModel).size() != 0) {
            throw new IllegalArgumentException("The offer can not be registered!");
        }

        this.offerRepository.saveAndFlush(this.modelMapper.map(offerServiceModel, Offer.class));
    }

    @Override
    public List<OfferServiceModel> findAllOffers() {

        return this.offerRepository.findAll().stream().map(o -> this.modelMapper.map(o, OfferServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public void findOffer(OfferFindBindingModel offerFindBindingModel) {
        if (this.validator.validate(offerFindBindingModel).size() != 0) {
            throw new IllegalArgumentException("Can not find searched offer!");
        }

        List<Offer> offers = this.offerRepository.findAll();
        boolean isFindOffer = false;
        for (Offer offer : offers) {
            boolean isValidedApartmentType = offer.getApartmentType().equalsIgnoreCase(offerFindBindingModel.getFamilyApartmentType());
            boolean isValidedBudget = offerFindBindingModel.getFamilyBudget().compareTo(offer.getApartmentRent().add(offer.getAgencyCommission().divide(new BigDecimal(100)).multiply(offer.getApartmentRent()))) >= 0;
            if (isValidedApartmentType && isValidedBudget) {
                isFindOffer = true;
                this.offerRepository.delete(offer);
                return;
            }
        }
        if (!isFindOffer) {
            throw new IllegalArgumentException("The searched offer does not exist!");

        }


    }
}
