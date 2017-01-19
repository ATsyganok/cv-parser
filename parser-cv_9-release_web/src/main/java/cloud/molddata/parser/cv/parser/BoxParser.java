package cloud.molddata.parser.cv.parser;

import cloud.molddata.parser.cv.model.CV;
import cloud.molddata.parser.cv.model.Contact;

import java.util.*;

public final class BoxParser {

    private static final String fDOUBLE_QUOTE = "\"";
    //the parser flips between these two sets of delimiters
    private static final String fWHITESPACE_AND_QUOTES = " \t\r\n\""; // \"
    private static final String fQUOTES_ONLY ="\"";
    //* @param aSearchText is non-null, but may have no content,
     //* and represents what the user has input in a search box.


    // must return CV
    public static CV parseResume(List<String> listText) {
        CV cv=new CV();
        Contact contact=new Contact();
        Set<Block> blockSet = new LinkedHashSet<Block>();
        ArrayList<String> listText2=new ArrayList<>();
        for(String text:listText) {
            if (text == null) {
                listText.remove(text);
                continue;
            }
            Block block=parseTextAndrey(text.trim());
            if(!"none".equals(block.getTypeBlock())){
                blockSet.add(block);
            }else{
               listText2.add(block.getTextBlock());
            }
        }
           ArrayList<Block> returnetList=Analyzer.AnalyzeAndrey(blockSet, cv);
           for(Block block:returnetList){
               listText2.add(block.getTextBlock());
           }

        ArrayList<Contact> contactList=new ArrayList<Contact>();
            for(String text:listText2) {
                parseTextVova(text.trim(), contactList);
            }
            Analyzer.AnalyzeVova(contactList,contact);
        cv.setDate(new Date());
        contact.setCv(cv);
        cv.setContact(contact);
        return cv;
    }

    private static void parseTextVova(String text,ArrayList<Contact> contactList){
        Contact c=new Contact();
        c.setText(text);
        c.setFullName(findName(text));
        c.setPhone(findPhone(text));
        c.setEmail((findMail(text)));
        c.setLocation(WordsContent.is_Location(c.getPhone()));
        c.setWeightContact(getWeightContact(c));
        contactList.add(c);
    }

    private static int getWeightContact(Contact c){
        int weightContact = 0;
        if (c.getPhone() != null)
            weightContact+=4;
        if (c.getFullName() != null)
            weightContact+=2;
        if (c.getFullName() !=null && c.getPhone() !=null)
            weightContact+=10;
        return weightContact;
    }

    private static Block parseTextAndrey(String text){
        boolean returnTokens = true;
        String currentDelims = fWHITESPACE_AND_QUOTES;
        StringTokenizer parser = new StringTokenizer(
                text, currentDelims, returnTokens
        );

        String token = null;
        Block textBlock=new Block();
        textBlock.setTextBlock(text);
        int textBlockSize=0;
        int i=0;
        while (parser.hasMoreTokens()) {
            token = parser.nextToken(currentDelims);
            token = tokenTrim(token).toLowerCase();
            if (!isDoubleQuote(token)&&!WordsContent.isExcludeWord(token)&&!checkP(token)){
                ArrayList<Boolean> checker=new ArrayList<>();
                checker.add(wordFindExperience(token, textBlock));
                checker.add(wordFindSkills(token, textBlock));
                checker.add(wordFindEducation(token, textBlock));
                checker.add(wordFindLanguages(token, textBlock));
                checker.add(wordFindTrainings(token, textBlock));
                checker.add(wordFindObjective(token, textBlock));
                if(!checkerBox(checker)){
                    textBlock.addUnrecognizedWord(token);
                }
                textBlockSize+=1;
            }
        }
        textBlock.setTextBlockSize(textBlockSize);
        return textBlock;
    }

    private static String tokenTrim(String token){
        return token.replace('(', ' ').replace(')', ' ').replace(',', ' ').replace('"',' ').replace('|',' ').replace(':',' ').replace('-',' ').replace(';',' ').replace('�',' ').replace('•',' ').replace('”',' ').trim();
    }

    private static boolean wordFindExperience(String aToken,Block block){
        Boolean status=false;
        if(WordsContent.is_WORDS_EXPERIENCE(aToken)) {
            block.addExperience(aToken);
            status=true;
        }
        return status;
    }

    private static boolean wordFindLanguages(String aToken,Block block){
        Boolean status=false;
        if(WordsContent.is_WORDS_LANGUAGE(aToken)){
            block.addLanguages(aToken);
            status=true;
        }
        return status;
    }

    private static boolean wordFindSkills(String aToken,Block block){
        Boolean status=false;
        if(WordsContent.is_WORDS_SKILLS(aToken)){
            block.addSkills(aToken);
            status=true;
        }
        return status;
    }

    private static boolean wordFindEducation(String aToken,Block block){
        Boolean status=false;
        if(WordsContent.is_WORDS_EDUCATION(aToken)){
            block.addEducation(aToken);
            status=true;
        }
        return status;
    }

    private static boolean wordFindTrainings(String aToken,Block block){
        Boolean status=false;
        if(WordsContent.is_WORDS_TRAININGS(aToken)){
            block.addTrainings(aToken);
            status=true;
        }
        return status;
    }
    private static boolean wordFindObjective(String aToken,Block block){
        Boolean status=false;
        if(WordsContent.is_WORDS_OBJECTIVE(aToken)){
            block.addObjective(aToken);
            status=true;
        }
        return status;
    }

    private static String findName(String text){
        return WordsContent.is_NAME_PARSE(text);
    }

    private static String findPhone(String text){
        return WordsContent.is_PHONE_PARSE(text);
    }

    private static String findMail(String text){
        return WordsContent.is_MAIL_PARSE(text);
    }

    private static boolean textHasContent(String aText){
        return (aText != null) && (!aText.trim().equals("")&&!aText.matches(" "));
    }

    private static boolean isDoubleQuote(String aToken){
        return aToken.equals(fDOUBLE_QUOTE);
    }

    private static String flipDelimiters(String aCurrentDelims){
        String result = null;
        if (aCurrentDelims.equals(fWHITESPACE_AND_QUOTES)){
            result = fQUOTES_ONLY;
        }
        else {
            result = fWHITESPACE_AND_QUOTES;
        }
        return result;
    }

    private static Boolean checkerBox(ArrayList<Boolean> checker){
        for(Boolean status:checker){
            if(status==true)
                return true;
        }
        return false;
    }

    private static Boolean checkP(String token){
        if(token.equals("")||token.contentEquals("-")||token.contentEquals(",")||token.contentEquals(" ")||token.length()==1||token.matches("\\d+")){
            return true;
        }
        return false;
    }
}
