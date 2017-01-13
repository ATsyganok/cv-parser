package cloud.molddata.parser.cv.parser;

import cloud.molddata.parser.cv.model.CV;
import cloud.molddata.parser.cv.model.Contact;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Analyzer {
    private static String skills_block;
    private static String experience_block;
    private static String education_block;
    private static String languages_block;
    //------------------------------------

    public static ArrayList<Block> AnalyzeAndrey(Set<Block> blocksSet, CV cv) {
        System.out.println("BLOCK SIZE="+blocksSet.size());
        ArrayList<Block> listUnknownBlock=new ArrayList<>();
        while (blocksSet.size() > 0) {
            System.out.println(blocksSet.size());
            for (Block candidateBlock : SetUnknownBlockFind(blocksSet))
                listUnknownBlock.add(candidateBlock);

            if (blocksSet.size() > 0) {
                System.out.println("before maxBLOCK with size=" + blocksSet.size());
                Block maxBlock = findMaxBlock(blocksSet);
                System.out.println("after maxBLOCK");
                maxBlock = findMaxBlock(sameTypeBlockSetCreate(maxBlock, blocksSet));

                setTextToCV(maxBlock, cv);
                // wipe of type list
                wipeOffBlockSet(maxBlock.getTypeBlock(), blocksSet);
                // remove this Block from blockSet
                System.out.println("a3");
                //removeBlock(maxBlock, blocksSet);
                blocksSet.remove(maxBlock);
                System.out.println("a4");
            }
        }
        return listUnknownBlock;
    }

    private static Set<Block> sameTypeBlockSetCreate(Block maxBlock,Set<Block> blocksSet){
        Set<Block> sameTypeBlockSet=new LinkedHashSet<Block>();
        String blockType=maxBlock.getTypeBlock();
        for(Block block:blocksSet){
            if(blockType.equals(block.getTypeBlock())) {
                sameTypeBlockSet.add(block);
            }
        }
        return sameTypeBlockSet;
    }

    private static Set<Block> SetUnknownBlockFind(Set<Block> blocksSet){
        Set<Block> setNoneBlocks=new LinkedHashSet<>();
        for(Block block:blocksSet){
            if("none".equals(block.getTypeBlock())) {
                setNoneBlocks.add(block);
            }
        }
        for(Block block:setNoneBlocks)
            blocksSet.remove(block);
        return setNoneBlocks;
    }

    private static void wipeOffBlockSet(String typeList, Set<Block> blockSet) {
        for (Block block : blockSet) {
            block.wipeOffList(typeList);
        }
    }

    private static void removeBlock(Block block, Set<Block> blockSet) {
        if (blockSet.contains(block)) {
            blockSet.remove(block);
        }
    }

    private static void setTextToCV(Block block, CV cv) {
        if ("skills".equals(block.getTypeBlock()))
            cv.setSkills(block.getTextBlock());
        else if ("experience".equals(block.getTypeBlock()))
            cv.setExp(block.getTextBlock());
        else if ("education".equals(block.getTypeBlock()))
            cv.setEdu(block.getTextBlock());
        else if ("languages".equals(block.getTypeBlock()))
            cv.setLang(block.getTextBlock());
        else if ("trainings".equals(block.getTypeBlock()))
            cv.setTrainings(block.getTextBlock());
        else if ("objective".equals(block.getTypeBlock()))
            cv.setObjective(block.getTextBlock());

        addNewWords(block);
    }

       private static void addNewWords(Block block){
            if("skills".equals(block.getTypeBlock()))
                WordsContent.add_WORD_SKILLS(block.getUnrecognizedWords());
            else if("experience".equals(block.getTypeBlock()))
                WordsContent.add_WORD_EXPERIENCE(block.getUnrecognizedWords());
            else if("education".equals(block.getTypeBlock()))
                WordsContent.add_WORD_EDUCATION(block.getUnrecognizedWords());
            else if("languages".equals(block.getTypeBlock()))
                WordsContent.add_WORD_LANGUAGE(block.getUnrecognizedWords());
            else if("trainings".equals(block.getTypeBlock()))
                WordsContent.add_WORD_TRAININGS(block.getUnrecognizedWords());
            else if("objective".equals(block.getTypeBlock()))
                WordsContent.add_WORD_OBJECTIVE(block.getUnrecognizedWords());
    }

    private static Block findMaxBlock(Set<Block> blockSet) {
        String type = "none";
        int maxPercent = 0;
        int maxPoint = 0;
        int textSize = 0;
        Block maxBlock = null;
        for (Block block : blockSet) {
            type = block.getTypeBlock();
            if ((type.equals("skills") && block.getPointSkills() > maxPoint)||(type.equals("skills") && block.getPercentSkills() > maxPercent && block.getTextBlockSize() > textSize)) {
                maxPoint = block.getPointSkills();
                maxPercent = block.getPercentSkills();
                textSize = block.getTextBlockSize();
                //---------------
                maxBlock = block;
                //---------------
            } else if ((type.equals("experience") && block.getPointExperience() > maxPoint )||(type.equals("experience") && block.getPercentExperience() > maxPercent && block.getTextBlockSize() > textSize)) {
                maxPoint = block.getPointExperience();
                maxPercent = block.getPercentExperience();
                textSize = block.getTextBlockSize();
                //---------------
                maxBlock = block;
                //---------------
            } else if ((type.equals("education") && block.getPointEducation() > maxPoint)||(type.equals("education") && block.getPercentEducation() > maxPercent && block.getTextBlockSize() > textSize)) {
                maxPoint = block.getPointEducation();
                maxPercent = block.getPercentEducation();
                textSize = block.getTextBlockSize();
                //---------------
                maxBlock = block;
                //---------------
            } else if ((type.equals("languages") && block.getPointLanguages() > maxPoint)||(type.equals("languages") && block.getPercentLanguages() > maxPercent && block.getTextBlockSize() > textSize)) {
                maxPoint = block.getPointLanguages();
                maxPercent = block.getPercentLanguages();
                textSize = block.getTextBlockSize();
                //---------------
                maxBlock = block;
                //---------------
            } else if ((type.equals("trainings") && block.getPointTrainings() > maxPoint)||(type.equals("trainings") && block.getPercentTrainings() > maxPercent && block.getTextBlockSize() > textSize)) {
                maxPoint = block.getPointTrainings();
                maxPercent = block.getPercentTrainings();
                textSize = block.getTextBlockSize();
                //---------------
                maxBlock = block;
                //---------------
            }else if ((type.equals("objective") && block.getPointSkills() > maxPoint)||(type.equals("objective") &&  block.getPercentObjective() > maxPercent && block.getTextBlockSize() > textSize)) {
                maxPoint = block.getPointObjective();
                maxPercent = block.getPercentObjective();
                textSize = block.getTextBlockSize();
                //---------------
                maxBlock = block;
                //---------------
            }
        }
        return maxBlock;
    }

    public static void AnalyzeVova(ArrayList<Contact> contactList, Contact contact) {
        int tempWeight = 0;
        for (Contact contacts : contactList) {
            if (contacts.getWeightContact() > tempWeight) {
                if (contacts.getFullName() != "") {
                    contact.setFullName(contacts.getFullName());
                }
                if (contacts.getEmail() != null)
                    contact.setEmail(contacts.getEmail());
                if (contacts.getPhone() != null) {
                    contact.setPhone(contacts.getPhone());
                    contact.setLocation(contacts.getLocation());
                }
                tempWeight = contacts.getWeightContact();
            }
        }
    }
}
