package main;

import main.model.CaseRepository;
import main.service.impl.CaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Case;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CaseController {

    @Autowired
    CaseServiceImpl caseService;

    @GetMapping("/cases/")
    public List<Case> list() {

        return caseService.getAll();

    }

    @PostMapping("/cases/")
    public int addCase(Case oneCase) {
        return caseService.addCase(oneCase);
    }

    @GetMapping("/cases/{id}")
    public ResponseEntity getCase(@PathVariable int id){

        Optional<Case> optionalCase = caseService.getById(id);

        if (!optionalCase.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(optionalCase.get(), HttpStatus.OK);
    }

    @DeleteMapping("/cases/{id}")
    public void removeCase(@PathVariable int id){
        caseService.delete(id);
    }

    @DeleteMapping("/cases/")
    public ResponseEntity deleteAllCases(){
        caseService.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/cases/{id}")
    public ResponseEntity changeCase(@PathVariable int id, String title, String description) {
        return caseService.editCase(id, title,description);
    }
}
