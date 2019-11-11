package softuni.exodiaapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exodiaapp.domain.entities.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {
}
