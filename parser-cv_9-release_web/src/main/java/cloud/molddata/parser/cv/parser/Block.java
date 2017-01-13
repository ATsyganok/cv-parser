package cloud.molddata.parser.cv.parser;

import java.util.*;

/**
 * Created by Андрей on 04.08.2016.
 */
public class Block {
    private List<String> skills=new ArrayList<>();
    private List<String> experience=new ArrayList<>();
    private List<String> education=new ArrayList<>();
    private List<String> languages=new ArrayList<>();
    private List<String> trainings=new ArrayList<>();
    private List<String> objective=new ArrayList<>();
    //------------------------------------------------
    private ArrayList<String> unrecognizedWords=new ArrayList<>();
    //------------------------------------------------
    private String textBlock;
    private int textBlockSize;

    public boolean isBlockContent(){
        int content=skills.size()+experience.size()+education.size()+languages.size()+trainings.size()+objective.size();
        return (0==content)?false:true;
    }

    public String getTypeBlock(){
        HashMap<String,Integer> map=new HashMap<>();
        map.put("skills",skills.size());
        map.put("experience",experience.size());
        map.put("education",education.size());
        map.put("languages",languages.size());
        map.put("trainings",trainings.size());
        map.put("objective",objective.size());
        Set<Map.Entry<String,Integer>> set=map.entrySet();
        int size=0;
        String type="none";
        for(Map.Entry<String,Integer> entry:set){
            if (entry.getValue()>size) {
                size = entry.getValue();
                type = entry.getKey();
            }
        }
        return type;
    }

    public void wipeOffList(String typeList){
        if("skills".equals(typeList))
            skills=new ArrayList<>();
        else if("experience".equals(typeList))
            experience=new ArrayList<>();
        else if("education".equals(typeList))
            education=new ArrayList<>();
        else if("languages".equals(typeList))
            languages=new ArrayList<>();
        else if("trainings".equals(typeList))
            trainings=new ArrayList<>();
        else if("objective".equals(typeList))
            objective=new ArrayList<>();
    }

    public int getAllPercent(String typeList){
        int percent=0;
        if("skills".equals(typeList))
            percent=getPercentSkills();
        else if("experience".equals(typeList))
            percent=getPercentExperience();
        else if("education".equals(typeList))
            percent=getPercentEducation();
        else if("languages".equals(typeList))
            percent=getPercentLanguages();
        else if("trainings".equals(typeList))
            percent=getPercentTrainings();
        else if("objective".equals(typeList))
            percent=getPercentObjective();
        return percent;
    }

    public int getPercentSkills(){
        return (getPointSkills()*100/textBlockSize);
    };
    public int getPercentExperience(){
        return (getPointExperience()*100/textBlockSize);
    }
    public int getPercentEducation(){return (getPointEducation()*100/textBlockSize);}
    public int getPercentLanguages(){return (getPointLanguages()*100/textBlockSize);}
    public int getPercentTrainings(){return (getPointTrainings()*100/textBlockSize);}
    public int getPercentObjective(){return (getPointObjective()*100/textBlockSize);}

    public String getTextBlock() {
        return textBlock;
    }
    public void setTextBlock(String textBlock) {
        this.textBlock = textBlock;
    }

    public int getTextBlockSize() {
        return textBlockSize;
    }
    public void setTextBlockSize(int textBlockSize) {
        this.textBlockSize = textBlockSize;
    }

    public void addLanguages(String word){
        languages.add(word);
    }
    public List<String> getWordsLanguages(){return languages;}
    public int getPointLanguages(){return languages.size();}

    public void addExperience(String word){
        experience.add(word);
    }
    public List<String> getWordsExperience(){return experience;}
    public int getPointExperience(){return experience.size();}

    public void addSkills(String word){
        skills.add(word);
    }
    public List<String> getWordsSkills(){return skills;}
    public int getPointSkills(){return skills.size();}

    public void addEducation(String word){
        education.add(word);
    }
    public List<String> getWordsEducation(){return education;}
    public int getPointEducation(){return education.size();}

    public void addTrainings(String word){
        trainings.add(word);
    }
    public int getPointObjective(){return objective.size();}

    public void addObjective(String word){
        objective.add(word);
    }
    public List<String> getWordsTrainings(){return trainings;}
    public int getPointTrainings(){return trainings.size();}

    public ArrayList<String> getUnrecognizedWords() {
        return unrecognizedWords;
    }
    public void addUnrecognizedWord(String word) {
        this.unrecognizedWords.add(word);
    }

    @Override
    public String toString() {
        return "Block{" +
                "\n skills=" + skills +
                "\n experience=" + experience +
                "\n education=" + education +
                "\n languages=" + languages +
                "\n trainings=" + trainings +
                "\n objective=" + objective +
                "\n unrecognizedWords=" + unrecognizedWords +
                "\n textBlockSize=" + textBlockSize +
                "\n block type=" + getTypeBlock()+
                "\n block percent=" +getAllPercent(getTypeBlock())+
                '}';
    }
}
