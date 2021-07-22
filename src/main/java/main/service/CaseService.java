package main.service;

import main.model.Case;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CaseService {

    int addCase(Case oneCase);
    void delete(long id);
    Optional<Case> getById(long id);
    ResponseEntity editCase(int id, String title, String description);
    List<Case> getAll();
    void deleteAll();
}
