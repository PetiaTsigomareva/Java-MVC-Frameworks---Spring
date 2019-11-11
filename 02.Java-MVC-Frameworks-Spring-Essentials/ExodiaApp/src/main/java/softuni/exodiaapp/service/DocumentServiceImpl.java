package softuni.exodiaapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exodiaapp.domain.entities.Document;
import softuni.exodiaapp.domain.models.service.DocumentServiceModel;
import softuni.exodiaapp.repositories.DocumentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository, ModelMapper modelMapper) {
        this.documentRepository = documentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String scheduleDocument(DocumentServiceModel documentServiceModel) {
        Document document = this.modelMapper.map(documentServiceModel, Document.class);
        String result;

        try {
            document = this.documentRepository.saveAndFlush(document);
            result = document.getId();


        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            result = null;
        }
        return result;
    }

    @Override
    public DocumentServiceModel findDocumentById(String id) {
        Document document = this.documentRepository.findById(id).orElse(null);
        DocumentServiceModel result;

        if (document == null) {
            result = null;
        } else {
            result = this.modelMapper.map(document, DocumentServiceModel.class);
        }
        return result;
    }

    @Override
    public List<DocumentServiceModel> findAllDocumnts() {
        return this.documentRepository.findAll().stream().map(d -> this.modelMapper.map(d, DocumentServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public boolean printById(String id) {
        boolean result;
        try {
            this.documentRepository.deleteById(id);
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }
        return result;
    }


}
