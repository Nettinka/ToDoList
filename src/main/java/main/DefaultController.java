package main;

import main.model.Case;

import main.service.impl.CaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;


@Controller
public class DefaultController {

    @Autowired
    private CaseServiceImpl caseService;


    @RequestMapping("/")
    public String index(Model model){

        ArrayList<Case> cases = new ArrayList<>();
        cases.addAll(caseService.getAll());

        model.addAttribute("cases", cases);
        model.addAttribute("casesCount", cases.size());


        return "index";
    }
}
