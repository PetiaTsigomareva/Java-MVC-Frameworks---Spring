package softuni.exodiaapp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.exodiaapp.domain.models.binding.DocumentBindingModel;
import softuni.exodiaapp.domain.models.service.DocumentServiceModel;
import softuni.exodiaapp.domain.models.view.DocumentDetailsViewModel;
import softuni.exodiaapp.domain.models.view.DocumentPrintViewModel;
import softuni.exodiaapp.service.DocumentService;

import javax.servlet.http.HttpSession;

@Controller
public class DocumentController {

    private final DocumentService documentService;
    private final ModelMapper modelMapper;

    @Autowired
    public DocumentController(DocumentService documentService, ModelMapper modelMapper) {
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/schedule")
    public ModelAndView schedule(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/login");
        } else {

            modelAndView.setViewName("schedule");
        }
        return modelAndView;
    }

    @PostMapping("/schedule")
    public ModelAndView scheduleConfirm(@ModelAttribute DocumentBindingModel model, ModelAndView modelAndView) {
        String documentId = this.documentService.scheduleDocument(this.modelMapper.map(model, DocumentServiceModel.class));
        if (documentId == null) {
            throw new IllegalArgumentException("The document can not be scheduled!!!");
        }
        modelAndView.setViewName("redirect:/details/" + documentId);

        return modelAndView;
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable("id") String id, ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/login");
        } else {

            DocumentServiceModel documentServiceModel = this.documentService.findDocumentById(id);
            if (documentServiceModel == null) {
                throw new IllegalArgumentException("Document not found!!!");
            }
            DocumentDetailsViewModel documentDetailsViewModel = this.modelMapper.map(documentServiceModel, DocumentDetailsViewModel.class);
            modelAndView.setViewName("details");
            modelAndView.addObject("model", documentDetailsViewModel);

        }
        return modelAndView;


    }

    @GetMapping("/print/{id}")
    public ModelAndView print(@PathVariable("id") String id, ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/login");
        } else {
            DocumentServiceModel documentServiceModel = this.documentService.findDocumentById(id);
            if (documentServiceModel == null) {
                throw new IllegalArgumentException("Document not found!!!");
            }
            DocumentPrintViewModel documentPrintViewModel = this.modelMapper.map(documentServiceModel, DocumentPrintViewModel.class);
            modelAndView.setViewName("print");
            modelAndView.addObject("model", documentPrintViewModel);
        }

        return modelAndView;
    }

    @PostMapping("/print/{id}")
    public ModelAndView printConfirm(@PathVariable("id") String id, ModelAndView modelAndView) {

        if (!this.documentService.printById(id)) {
            throw new IllegalArgumentException("Document can not be print!!!");
        }

        modelAndView.setViewName("redirect:/home");


        return modelAndView;
    }


}
