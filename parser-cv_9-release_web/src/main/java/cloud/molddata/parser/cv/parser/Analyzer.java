package cloud.molddata.parser.cv.parser;

import cloud.molddata.parser.cv.model.CV;
import cloud.molddata.parser.cv.model.Contact;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Analyzer {

    public static ArrayList<Contact> fillCvDescript(Set<Block> blocksSet, CV cv,ArrayList<Contact> contactList) {
        while (blocksSet.size() > 0) {
            for (Block candidateBlock : createSetUnrecBlock(blocksSet)) {
                contactList.add(TextParser.createContact(candidateBlock.getTextBlock()));
                blocksSet.remove(candidateBlock);
            }
            if (blocksSet.size() > 0) {
                Block maxBlock = findMaxBlock(blocksSet);
                maxBlock = findMaxBlock(createSameTypeBlockSet(maxBlock, blocksSet));
                setTextToCV(maxBlock, cv);
                addUnrecWordsSetFile(maxBlock);
                // wipe of type list
                wipeOffBlockSet(maxBlock.getTypeBlock(), blocksSet);
                //removeBlock(maxBlock, blocksSet);
                blocksSet.remove(maxBlock);
            }
        }
        return contactList;
    }

    private static Set<Block> createSameTypeBlockSet(Block maxBlock,Set<Block> blocksSet){
        Set<Block> sameTypeBlockSet=new LinkedHashSet<Block>();
        for(Block block:blocksSet){
            if(maxBlock.getTypeBlock().equals(block.getTypeBlock())) {
                sameTypeBlockSet.add(block);
            }
        }
        return sameTypeBlockSet;
    }

    private static Set<Block> createSetUnrecBlock(Set<Block> blocksSet){
        Set<Block> setUnrecBlocks=new LinkedHashSet<>();
        for(Block block:blocksSet){
            if(Type.UNRECOGNIZE.equals(block.getTypeBlock())) {
                setUnrecBlocks.add(block);
            }
        }
        return setUnrecBlocks;
    }

    private static void wipeOffBlockSet(Type type, Set<Block> blockSet) {
        for (Block block : blockSet)
            block.wipeOffList(type);
    }

    private static void setTextToCV(Block block, CV cv) {
        switch (block.getTypeBlock()){
            case SKILLS:cv.setSkills(block.getTextBlock());
                break;
            case EXPERIENCE: cv.setExp(block.getTextBlock());
                break;
            case EDUCATION: cv.setEdu(block.getTextBlock());
                break;
            case LANGUAGE: cv.setLang(block.getTextBlock());
                break;
            case TRAININGS: cv.setTrainings(block.getTextBlock());
                break;
            case OBJECTIVE: cv.setObjective(block.getTextBlock());
                break;
        }
    }

    private static void addUnrecWordsSetFile(Block block){
        WordsContent.addWords(block.getTypeBlock(),block.getUnrecognizedWords());
    }

    private static Block findMaxBlock(Set<Block> blockSet) {
        Type type = Type.UNRECOGNIZE;
        int maxPercent = 0;
        int maxPoint = 0;
        int textSize = 0;
        Block maxBlock = null;
        for (Block block : blockSet) {
            type = block.getTypeBlock();
            if ((block.getPoint(type) > maxPoint)||(block.getPercent(type) > maxPercent && block.getTextBlockSize() > textSize)) {
                maxPoint = block.getPoint(type);
                maxPercent = block.getPercent(type);
                textSize = block.getTextBlockSize();
                maxBlock = block;
            }
        }
        return maxBlock;
    }

    public static Contact createContactCV(ArrayList<Contact> contactList) {
        Contact cvContact=new Contact();
        int tempWeight = 0;
        for (Contact contacts : contactList) {
            if (contacts.getWeightContact() > tempWeight) {
                if (contacts.getFullName() != "")
                    cvContact.setFullName(contacts.getFullName());
                if (contacts.getEmail() != null)
                    cvContact.setEmail(contacts.getEmail());
                if (contacts.getPhone() != null) {
                    cvContact.setPhone(contacts.getPhone());
                    cvContact.setLocation(contacts.getLocation());
                }
                tempWeight = contacts.getWeightContact();
            }
        }
        return cvContact;
    }
}
