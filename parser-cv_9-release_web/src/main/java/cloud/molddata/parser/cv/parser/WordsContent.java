package cloud.molddata.parser.cv.parser;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordsContent {
    private static final String filePath=Paths.pFolderDictionary;
    private static final Set<String> fCOMMON_WORDS_EXCLUDE = new LinkedHashSet<>();
    private static final Set<String> fCOMMON_WORDS_SKILLS = new LinkedHashSet<>();
    private static final Set<String> fCOMMON_WORDS_EDUCATION = new LinkedHashSet<>();
    private static final Set<String> fCOMMON_WORDS_LANGUAGE = new LinkedHashSet<>();
    private static final Set<String> fCOMMON_WORDS_EXPERIENCE = new LinkedHashSet<>();
    private static final Set<Pattern> fCOMMON_PHONE_PARSE = new LinkedHashSet<>();
    private static final Set<String> fCOMMON_WORDS_TRAININGS = new LinkedHashSet<>();
    private static final Set<String> fCOMMON_WORDS_OBJECTIVE = new LinkedHashSet<>();

    static {
        readFromFiles(Type.EXCLUDE, fCOMMON_WORDS_EXCLUDE);
        readFromFiles(Type.SKILLS, fCOMMON_WORDS_SKILLS);
        readFromFiles(Type.EDUCATION, fCOMMON_WORDS_EDUCATION);
        readFromFiles(Type.LANGUAGE, fCOMMON_WORDS_LANGUAGE);
        readFromFiles(Type.EXPERIENCE, fCOMMON_WORDS_EXPERIENCE);
        readFromFiles(Type.TRAININGS, fCOMMON_WORDS_TRAININGS);
        readFromFiles(Type.OBJECTIVE, fCOMMON_WORDS_OBJECTIVE);
    }

    public static void addWords(Type type,LinkedHashSet<String> words) {
        addWordsToSET(type, words);
        writeToFileJSON(type, words);
    }

    public static boolean containWord(Type type,String word){
        Set<String> regularSet=getSet(type);
        for (String expression : regularSet) {
            if (word.matches(expression)) {
                return true;
            }
        }
        return false;
    }

    //-----------------------------------------------------------------------
    public static String isNameParse(String text) {

        Pattern pName0 = Pattern.compile("([A-Z][a-z]+\\s[A-Z]\\.((|\\s)[A-Z]\\.|\\s))"); //Kanagava V.B.
        Pattern pName1 = Pattern.compile("([A-Z][a-z]+\\s){2,}"); // Ivan Petrovich and Ivan Petro Borisich
        Pattern pName2 = Pattern.compile("([A-Z][a-z]+(|\\s)){2,}"); // Ivan Petrovich and Ivan Petro Borisich
        Pattern pName3 = Pattern.compile("([A-Z][a-z]+-[A-Z][a-z]+\\s([A-Z][a-z]+))"); //Ikhe-Abdu Adalahi

        Matcher mName0 = pName0.matcher(text);
        Matcher mName1 = pName1.matcher(text);
        Matcher mName2 = pName2.matcher(text);
        Matcher mName3 = pName3.matcher(text);

        String resName = "";

        if (mName3.find())
            resName = mName3.group();
        if (mName2.find())
            resName = mName2.group();
        if (mName1.find())
            resName = mName1.group();
        if (mName0.find())
            resName = mName0.group();

        return resName;
    }
    //-----------------------------------------------------------------------
    static {
        fCOMMON_PHONE_PARSE.add(Pattern.compile("(?:\\+|[0-9] ?){6,14}[0-9]")); //all without braces
        fCOMMON_PHONE_PARSE.add(Pattern.compile("((.?|\\+)\\d{2}[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}")); //all with braces
    }

    public static String isPhoneParse(String text) {
        String resNumber = "";
        for (Pattern pattern : fCOMMON_PHONE_PARSE) {
            Matcher phone = pattern.matcher(text);
            if (phone.find() && resNumber.length() < phone.group().length())
                resNumber = phone.group();
        }
        return (resNumber.equals("")) ? null : resNumber;
    }

    //------------------------------------------------------------------------------
    private static final Pattern Mail = Pattern.compile("[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})");

    public static String isMailParse(String text) {
        Matcher mMail = Mail.matcher(text);
        return(mMail.find()?mMail.group():null);
    }

    public static String isLocation(String phone) {
        if (phone != null && isPhoneParse(phone) != null) {
            CCSeacrher CCSeacrher = new CCSeacrher();
            return CCSeacrher.searcherCountryCode(phone)[1];
        } else
            return "UNKNOWN LOCATION";
    }

    private static void addWordsToSET(Type type,LinkedHashSet<String> words) {
        Set<String> regularSet=getSet(type);
        for(String word:words) {
            regularSet.add(makeRegular(word));
        }
    }

    private static Set<String> getSet(Type type){
        switch (type){
            case EDUCATION: return fCOMMON_WORDS_EDUCATION;
            case TRAININGS: return fCOMMON_WORDS_TRAININGS;
            case EXPERIENCE: return fCOMMON_WORDS_EXPERIENCE;
            case LANGUAGE: return fCOMMON_WORDS_LANGUAGE;
            case OBJECTIVE: return fCOMMON_WORDS_OBJECTIVE;
            case SKILLS: return fCOMMON_WORDS_SKILLS;
            case EXCLUDE: return fCOMMON_WORDS_EXCLUDE;
            default: return new LinkedHashSet<String>();
        }
    }

    private static String makeRegular(String word) {
        StringBuilder sb = new StringBuilder();
        char[] array = word.toCharArray();
        //------------------------------------
        sb.append(".*\\b");
        for (int i = 0; i < array.length; ++i) {
            sb.append("[");
            sb.append(Character.toUpperCase(array[i]));
            sb.append(Character.toLowerCase(array[i]));
            sb.append("]");
        }
        sb.append("(.?|[Ss])\\b.*");
        return sb.toString();
    }

    private static void writeToFileJSON(Type typeF,LinkedHashSet<String> words) {
        String fname=typeF.toString();
        String path =filePath+fname+"_learn.txt";
        Gson gson = new GsonBuilder().create();
        File file = null;
        BufferedWriter bw = null;
        try {
            file=new File(path);
            bw = new BufferedWriter(new FileWriter(file, true));
            for(String word:words) {
                bw.append(gson.toJson(new JSONWC(fname,makeRegular(word))));
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void readFromFiles(Type typeF,Set<String> set) {
        String fName=typeF.toString();
        Gson gson = new GsonBuilder().create();
        List<String> paths=new ArrayList<>();
        paths.add(filePath+fName+".txt");
        paths.add(filePath+fName+"_learn.txt");
        File file = null;
        BufferedReader br = null;
        try {
            for(String path:paths) {
                br = new BufferedReader(new FileReader(new File(path)));
                String json = br.readLine();
                while (json != null) {
                    set.add(gson.fromJson(json, JSONWC.class).word);
                    json=br.readLine();
                }
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

