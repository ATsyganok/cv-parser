package cloud.molddata.parser.cv.parser;

import cloud.molddata.parser.cv.model.CV;
import cloud.molddata.parser.cv.model.Contact;

import java.util.*;

public final class TextParser {
    private static final String fDOUBLE_QUOTE = "\"";
    private static final String fWHITESPACE_AND_QUOTES = " \t\r\n\"";

    // returned CV
    public static CV createCV(List<String> listText) {
        Set<Block> blockSet = new LinkedHashSet<Block>();
        ArrayList<Contact> contactList = new ArrayList<Contact>();
        for (String text : listText) {
            Block block = createBlock(text.trim());
            if (!Type.UNRECOGNIZE.equals(block.getTypeBlock())) {
                blockSet.add(block);
            } else {
                contactList.add(createContact(block.getTextBlock()));
            }
        }
        CV cv = new CV();
        contactList= Analyzer.fillCvDescript(blockSet, cv, contactList);
        Contact contactCV= Analyzer.createContactCV(contactList);
        cv.setDate(new Date());
        contactCV.setCv(cv);
        cv.setContact(contactCV);
        return cv;
    }

    static Contact createContact(String text){
        text=text.trim();
        Contact c = new Contact();
        c.setText(text);
        c.setFullName(findName(text));
        c.setPhone(findPhone(text));
        c.setEmail((findMail(text)));
        c.setLocation(WordsContent.isLocation(c.getPhone()));
        c.setWeightContact(getWeightContact(c));
        return c;
    }

    private static int getWeightContact(Contact c) {
        int weightContact = 0;
        if (c.getPhone() != null)
            weightContact += 4;
        if (c.getFullName() != null)
            weightContact += 2;
        if (c.getFullName() != null && c.getPhone() != null)
            weightContact += 10;
        return weightContact;
    }

    private static Block createBlock(String text) {
        String currentDelims = fWHITESPACE_AND_QUOTES;
        StringTokenizer parser = new StringTokenizer(text, currentDelims, true);
        String word = null;
        Block textBlock = new Block(text);
        int textBlockSize = 0;
        int i = 0;
        while (parser.hasMoreTokens()) {
            word = wordTrim(parser.nextToken(currentDelims)).toLowerCase();
            if (checkWord(word)) {
                ArrayList<Boolean> checkList = new ArrayList<>();
                checkList.add(wordAdd(Type.EXPERIENCE, word, textBlock));
                checkList.add(wordAdd(Type.SKILLS, word, textBlock));
                checkList.add(wordAdd(Type.EDUCATION, word, textBlock));
                checkList.add(wordAdd(Type.LANGUAGE, word, textBlock));
                checkList.add(wordAdd(Type.TRAININGS, word, textBlock));
                checkList.add(wordAdd(Type.OBJECTIVE, word, textBlock));
                if(!checkList.contains(true))
                    textBlock.addWord(Type.UNRECOGNIZE,word);
                textBlockSize += 1;
            }
        }
        textBlock.setTextBlockSize(textBlockSize);
        return textBlock;
    }

    private static boolean checkWord(String word){
        boolean quote=!word.equals(fDOUBLE_QUOTE);
        boolean containWord=!WordsContent.containWord(Type.EXCLUDE, word);
        boolean length=word.length() == 1;
        boolean equales=word.equals("") || word.contentEquals("-") || word.contentEquals(",") || word.contentEquals(" ");
        boolean digits=word.matches("\\d+");
        boolean wled=length||equales||digits;
        return (containWord && quote && !wled)?true:false;
    }

    private static String wordTrim(String token) {
        return token.replace('(', ' ').replace(')', ' ').replace(',', ' ').replace('"', ' ').replace('|', ' ').replace(':', ' ').replace('-', ' ').replace(';', ' ').replace('�', ' ').replace('•', ' ').replace('”', ' ').trim();
    }

    private static boolean wordAdd(Type type, String word, Block block) {
        return (WordsContent.containWord(type,word)?block.addWord(type,word):false);
    }

    private static String findName(String text) {
        return WordsContent.isNameParse(text);
    }

    private static String findPhone(String text) {
        return WordsContent.isPhoneParse(text);
    }

    private static String findMail(String text) {
        return WordsContent.isMailParse(text);
    }
}
