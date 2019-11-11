package softuni.exodiaapp.service;

import softuni.exodiaapp.domain.entities.Document;
import softuni.exodiaapp.domain.models.service.DocumentServiceModel;

import java.util.List;

public interface DocumentService {

    String scheduleDocument(DocumentServiceModel documentServiceModel);

    DocumentServiceModel findDocumentById(String id);

    List<DocumentServiceModel> findAllDocumnts();

    boolean printById(String id);
}
