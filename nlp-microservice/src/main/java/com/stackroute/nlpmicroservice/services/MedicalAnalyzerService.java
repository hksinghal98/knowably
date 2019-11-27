package com.stackroute.nlpmicroservice.services;

import com.stackroute.nlpmicroservice.domain.Medical;
import com.stackroute.nlpmicroservice.model.MedicalDictionary;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MedicalAnalyzerService {
    private MedicalDictionary medicalDictionary;
    private Medical medical;

    @Autowired
    public MedicalAnalyzerService(MedicalDictionary medicalDictionary, Medical medical) {
        this.medicalDictionary = medicalDictionary;
        this.medical = medical;
    }

    void extractInfo(String concept, CoreMap sentence, LinkedHashMap<String, List<String>> mapOfEachWordProps, LinkedHashMap<String, String> entInSentence) {
        medical.setDisease(concept);
        HashMap<Integer, String> matched_points = new HashMap<>();
        ArrayList<Integer> indices = new ArrayList<>();
        medicalDictionary.medicalInitializer();
        int index;

        for (int i = 0; i < medicalDictionary.getSymptoms().size(); i++) {
            index = sentence.toString().toLowerCase().indexOf(medicalDictionary.getSymptoms().get(i).toLowerCase());
            if (index != -1) {
                matched_points.put(index, "symptom");
                indices.add(index);
            }
        }
        for (int i = 0; i < medicalDictionary.getMedicine().size(); i++) {
            index = sentence.toString().toLowerCase().indexOf(medicalDictionary.getMedicine().get(i).toLowerCase());
            if (index != -1) {
                matched_points.put(index, "medicine");
                indices.add(index);
            }
        }
        for (int i = 0; i < medicalDictionary.getCauses().size(); i++) {
            index = sentence.toString().toLowerCase().indexOf(medicalDictionary.getCauses().get(i).toLowerCase());
            if (index != -1) {
                matched_points.put(index, "causes");
                indices.add(index);
            }
        }
        for (int i = 0; i < medicalDictionary.getDeathCount().size(); i++) {
            index = sentence.toString().toLowerCase().indexOf(medicalDictionary.getDeathCount().get(i).toLowerCase());
            if (index != -1) {
                matched_points.put(index, "deathCount");
                indices.add(index);
            }
        }
        for (int i = 0; i < medicalDictionary.getTreatment().size(); i++) {
            index = sentence.toString().toLowerCase().indexOf(medicalDictionary.getTreatment().get(i).toLowerCase());
            if (index != -1) {
                matched_points.put(index, "treatment");
                indices.add(index);
            }
        }
        matched_points.put(sentence.toString().length(), "EOS");
        indices.add(sentence.toString().length());
        LinkedHashSet<Integer> hashSet1 = new LinkedHashSet<>(indices);
        indices = new ArrayList<>(hashSet1);
        Collections.sort(indices);

        System.out.println("**************************************outside first for loop");
        System.out.println("sentence=" + sentence);
        System.out.println("m1=" + mapOfEachWordProps);
        System.out.println("m2=" + entInSentence);
        System.out.println(matched_points.toString());
        System.out.println(indices.toString());
        System.out.println("concept=" + medical.getDisease());

        for (int i = 0; i < indices.size() - 1; i++) {
            if (matched_points.get(indices.get(i)).equalsIgnoreCase("symptom"))
            {
                String sub_str = sentence.toString().substring(indices.get(i), indices.get(i + 1));
                ArrayList<String> exc= new ArrayList<>(Arrays.asList("DT","VB", "VBZ", "VBP", "VBD", "VBN", "VBG"));
                System.out.println("*************** substring symptom= "+sub_str);
                String[] splits = sub_str.split(" ");
                System.out.println("*************** split symptom= "+Arrays.asList(splits));
                for (int j = 1; j < splits.length; j++)
                {
                    try {
                        System.out.println("split j= "+splits[j]+" get 0 "+mapOfEachWordProps.get(splits[j]).get(0));
                        if (mapOfEachWordProps.get(splits[j]).get(0).equals("NN"))
                        { medical.getSymptoms().add(splits[j]); }
                        else if(exc.contains(mapOfEachWordProps.get(splits[j]).get(0)))
                        { break; }
                    }catch (Exception ex)
                    { System.out.println("caught ex in symptoms "+ex); }
                }
            }
            if (matched_points.get(indices.get(i)).equalsIgnoreCase("medicine"))
            {
                String sub_str = sentence.toString().substring(indices.get(i), indices.get(i + 1));
                ArrayList<String> exc= new ArrayList<>(Arrays.asList("VBZ", "VBP", "VBD", "VBN", "VBG"));
                ArrayList<String> inc= new ArrayList<>(Arrays.asList("NN", "FW","JJ"));
                System.out.println("*************** substring medicine= "+sub_str);
                String[] splits = sub_str.split(" ");
                System.out.println("*************** splits medicine= "+Arrays.asList(splits));
                for (int j = 1; j < splits.length; j++)
                {
                    try {
                        System.out.println("split j= "+splits[j]+" get 0 "+mapOfEachWordProps.get(splits[j]).get(0));
                        if (inc.contains(mapOfEachWordProps.get(splits[j]).get(0)))
                        { medical.getMedicine().add(splits[j]); }
                        else if(exc.contains(mapOfEachWordProps.get(splits[j]).get(0)))
                        { break; }
                    }catch (Exception ex)
                    { System.out.println("caught ex in medicine "+ex); }
                }
            }
            if (matched_points.get(indices.get(i)).equalsIgnoreCase("causes"))
            {
                String sub_str = sentence.toString().substring(indices.get(i), indices.get(i + 1));
                ArrayList<String> exc= new ArrayList<>(Arrays.asList("VBZ", "VBP", "VBD", "VBN", "VBG"));
                ArrayList<String> inc= new ArrayList<>(Arrays.asList("NN", "FW"));
                System.out.println("*************** substring causes= "+sub_str);
                String[] splits = sub_str.split(" ");
                System.out.println("*************** splits causes= "+Arrays.asList(splits));
                for (int j = 1; j < splits.length; j++)
                {
                    try {
                        System.out.println("split j= "+splits[j]+" get 0 "+mapOfEachWordProps.get(splits[j]).get(0));
                        if (inc.contains(mapOfEachWordProps.get(splits[j]).get(0)))
                        { medical.getCauses().add(splits[j]); }
                        else if(exc.contains(mapOfEachWordProps.get(splits[j]).get(0)))
                        { break; }
                    }catch (Exception ex)
                    { System.out.println("caught ex in causes "+ex); }
                }
            }

            if (matched_points.get(indices.get(i)).equalsIgnoreCase("deathCount"))
            {
                String sub_str = sentence.toString().substring(indices.get(i), indices.get(i + 1));
                System.out.println("*************** substring deathCount= "+sub_str);
                List<String> l=new ArrayList<>(entInSentence.keySet());
                for (String s : l) {
                    if (entInSentence.get(s).equalsIgnoreCase("NUMBER")) {
                        medical.getDeathCount().add(s); }
                }
            }
            if (matched_points.get(indices.get(i)).equalsIgnoreCase("treatment"))
            {
                String sub_str = sentence.toString().substring(indices.get(i), indices.get(i + 1));
                ArrayList<String> exc= new ArrayList<>(Arrays.asList("VBZ", "VBP", "VBD", "VBN", "VBG","IN"));
                ArrayList<String> inc= new ArrayList<>(Arrays.asList("NN", "FW","JJ"));
                System.out.println("*************** substring treatment= "+sub_str);
                String[] splits = sub_str.split(" ");
                System.out.println("*************** splits treatment= "+Arrays.asList(splits));
                for (int j = 1; j < splits.length; j++)
                {
                    try {
                        System.out.println("split j= "+splits[j]+" get 0 "+mapOfEachWordProps.get(splits[j]).get(0));
                        if (inc.contains(mapOfEachWordProps.get(splits[j]).get(0)))
                        { medical.getTreatment().add(splits[j]); }
                        else if(exc.contains(mapOfEachWordProps.get(splits[j]).get(0)))
                        { break; }
                    }catch (Exception ex)
                    { System.out.println("caught ex in treatment "+ex); }
                }
            }
        }
        System.out.println("\n// after each sentence===== "+medical.toString());
        matched_points.clear();
        indices.clear();
        index = 0;

    }
}
