package main.service.impl;

import main.model.Case;
import main.model.CaseRepository;
import main.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    private CaseRepository caseRepository;

    @Override
    public int addCase(Case oneCase) {
        Case savedCase = caseRepository.save(oneCase);
        return savedCase.getId();
    }

    @Override
    public void delete(long id) {
        caseRepository.deleteById((int) id);
    }

    @Override
    public Optional<Case> getById(long id) {
        Optional<Case> optionalCase = caseRepository.findById((int) id);
        return optionalCase;
    }

    @Override
    public ResponseEntity editCase(int id, String title, String description) {
        Optional<Case> optionalCase = caseRepository.findById(id);
        Case oneCase = optionalCase.get();
        oneCase.setTitle(title);
        oneCase.setDescription(description);
        if (optionalCase.isPresent()) {
            return new ResponseEntity(caseRepository.save(oneCase), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @Override
    public List<Case> getAll() {
        Iterable<Case> caseIterable = caseRepository.findAll();
        ArrayList<Case> caseArrayList = new ArrayList<>();
        for(Case oneCase : caseIterable){
            caseArrayList.add(oneCase);
        }
        return caseArrayList;
    }

    @Override
    public void deleteAll() {
        caseRepository.deleteAll();
    }
}
