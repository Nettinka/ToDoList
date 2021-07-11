package main;

import main.model.Case;
import java.util.ArrayList;
import java.util.HashMap;


public class Storage {

    private static int currentId = 1;
    private static HashMap<Integer, Case> cases = new HashMap<>();

    public static ArrayList<Case> getAllCase() {

        ArrayList<Case> casesList = new ArrayList<>();
        casesList.addAll(cases.values());
        return casesList;
    }

    public static int addCase(Case oneCase){

        int id = currentId++;
        oneCase.setId(id);
        cases.put(id, oneCase);
        return id;
    }

    public static Case getCase(int id){

        if(cases.containsKey(id)) {
            return cases.get(id);
        }
       return null;
    }

    public static void deleteCaseById(int id){
        if(cases.containsKey(id)) {
            cases.remove(id);
        }
    }

    public static void deleteAllCases(){
        cases.clear();
    }

    public static Case refreshCase(int id, String description, String title){
        if(cases.containsKey(id)) {
            if(description == null && title == null){
                return cases.get(id);
            }
            if(description == null && title != null){
                cases.get(id).setDescription(title);
                return cases.get(id);
            }
            if(description != null && title == null) {
                cases.get(id).setDescription(description);
                return cases.get(id);
            }
            else {
                cases.get(id).setDescription(description);
                cases.get(id).setDescription(title);
                return cases.get(id);
            }
        }
        return null;
    }

}
