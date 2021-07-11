package main;

import main.model.CaseRepository;
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
    private CaseRepository caseRepository;

    @GetMapping("/cases/")
    public List<Case> list() {
        Iterable<Case> caseIterable = caseRepository.findAll();
        ArrayList<Case> caseArrayList = new ArrayList<>();
        for(Case oneCase : caseIterable){
            caseArrayList.add(oneCase);
        }
        return caseArrayList;
    }

    @PostMapping("/cases/")
    public int addCase(Case oneCase) {
        Case newCase = caseRepository.save(oneCase);
        return newCase.getId();
    }

    @GetMapping("/cases/{id}")
    public ResponseEntity getCase(@PathVariable int id){

        Optional<Case> optionalCase = caseRepository.findById(id);

        if (!optionalCase.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(optionalCase.get(), HttpStatus.OK);
    }

    @DeleteMapping("/cases/{id}")
    public void removeCase(@PathVariable int id){
        caseRepository.deleteById(id);
    }

    @DeleteMapping("/cases/")
    public ResponseEntity deleteAllCases(){
        caseRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/cases/{id}")
    public ResponseEntity changeCase(@PathVariable int id, String title, String description) {
        Optional<Case> optionalCase = caseRepository.findById(id);
        Case oneCase = optionalCase.get();
        oneCase.setTitle(title);
        oneCase.setDescription(description);
        if (optionalCase.isPresent()) {
            return new ResponseEntity(caseRepository.save(oneCase), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
