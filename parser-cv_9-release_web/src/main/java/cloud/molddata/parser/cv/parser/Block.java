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
    private LinkedHashSet<String> unrecognizedWords=new LinkedHashSet<>();

    private String textBlock;
    private int textBlockSize;

    public Block(String text){
        this.textBlock=text;
    }

    public Type getTypeBlock(){
        HashMap<Type,Integer> map=new HashMap<>();
        map.put(Type.SKILLS,skills.size());
        map.put(Type.EXPERIENCE,experience.size());
        map.put(Type.EDUCATION,education.size());
        map.put(Type.LANGUAGE,languages.size());
        map.put(Type.TRAININGS,trainings.size());
        map.put(Type.OBJECTIVE,objective.size());
        int size=0;
        Type type=Type.UNRECOGNIZE;
        for(Map.Entry<Type,Integer> entry:map.entrySet()){
            if (entry.getValue()>size) {
                size = entry.getValue();
                type = entry.getKey();
            }
        }
        return type;
    }

    public void wipeOffList(Type type){
        switch (type){
            case SKILLS: skills=new ArrayList<>();
                break;
            case EXPERIENCE: experience=new ArrayList<>();
                break;
            case EDUCATION: education=new ArrayList<>();
                break;
            case LANGUAGE: languages=new ArrayList<>();
                break;
            case TRAININGS: trainings=new ArrayList<>();
                break;
            case OBJECTIVE: objective=new ArrayList<>();
                break;
        }
    }

    public int getPercent(Type type){
        return getPoint(type)*100/textBlockSize;
    }

    public int getPoint(Type type){
        int point=0;
        switch (type){
            case EDUCATION: point=education.size();
                break;
            case TRAININGS: point=trainings.size();
                break;
            case EXPERIENCE: point=experience.size();
                break;
            case LANGUAGE: point=languages.size();
                break;
            case OBJECTIVE: point=objective.size();
                break;
            case SKILLS: point=skills.size();
                break;
        }
        return point;
    }


    public String getTextBlock() {
        return textBlock;
    }

    public int getTextBlockSize() {
        return textBlockSize;
    }
    public void setTextBlockSize(int textBlockSize) {
        this.textBlockSize = textBlockSize;
    }

    public boolean addWord(Type type,String word){
        switch (type){
            case EDUCATION: return education.add(word);
            case TRAININGS: return trainings.add(word);
            case EXPERIENCE: return experience.add(word);
            case LANGUAGE: return languages.add(word);
            case OBJECTIVE: return objective.add(word);
            case SKILLS: return skills.add(word);
            case UNRECOGNIZE: return unrecognizedWords.add(word);
            default:return false;
        }
    }


    public LinkedHashSet<String> getUnrecognizedWords() {
        return unrecognizedWords;
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
                "\n block percent=" +getPercent(getTypeBlock())+
                '}';
    }
}
