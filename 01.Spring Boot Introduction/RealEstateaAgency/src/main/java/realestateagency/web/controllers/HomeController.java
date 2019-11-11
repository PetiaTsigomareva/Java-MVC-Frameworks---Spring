package realestateagency.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import realestateagency.conficurations.Constants;
import realestateagency.domain.models.view.OfferViewModel;
import realestateagency.service.OfferService;
import realestateagency.util.HtmlReader;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final OfferService offerService;
    private final HtmlReader reader;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(OfferService offerService, HtmlReader reader, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.reader = reader;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/")
    @ResponseBody
    public String index() throws IOException {

        return this.prepareView();

    }

    private String prepareView() throws IOException {
        StringBuilder offertHtmlContent = new StringBuilder();
        List<OfferViewModel> offers = this.offerService.findAllOffers().stream().map(o -> this.modelMapper.map(o, OfferViewModel.class)).collect(Collectors.toList());
        if (offers.size() == 0) {
            offertHtmlContent.append("<div class=\"apartment\" style=\"border:red solid 1px\">").append("There are not any offers!").append(" </div>");

        } else {
            for (OfferViewModel offer : offers) {
                offertHtmlContent.append("<div class=\"apartment\">");
                offertHtmlContent.append("<p>Rent: " + offer.getApartmentRent() + "</p>");
                offertHtmlContent.append("<p>Type: " + offer.getApartmentType() + "</p>");
                offertHtmlContent.append("<p>Commission: " + offer.getAgencyCommission() + "</p>");
                offertHtmlContent.append("</div>").append(System.lineSeparator());
            }

        }

        return this.reader.readHtmlFile(Constants.INDEX_HTML_PATH_FILE).replace("{{offers}}", offertHtmlContent.toString().trim());
    }

}
