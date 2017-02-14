package cloud.molddata.parser.cv.parser;

import cloud.molddata.parser.cv.model.CV;
import cloud.molddata.parser.cv.model.Contact;
import cloud.molddata.parser.cv.model.UploadedFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The user enters text into a search box. This class is used
 * to parse that text into specific search terms (or tokens).
 * It eliminates common words, and allows for the quoting of text, using
 * double quotes.
 * JDK 7+.
 */
public final class FileParcer {
    private static String path=Paths.pfolderUploades;
    private static int MaxSize=40;
    private static int MinSize=3;
    private static CV cv;
    private static List<Contact> contactList=new ArrayList<>();
    private static List<CV> cvList=new ArrayList<>();

    public static List<Contact> getContactList() {
        List<Contact> contactListTemp = new ArrayList<>();
        contactListTemp = contactList;
        contactList = new ArrayList<>();
        return contactListTemp;
    }

    public static List<CV> getcvList() {
        List<CV> cvListTemp = new ArrayList<>();
        cvListTemp = cvList;
        cvList = new ArrayList<>();
        return cvListTemp;
    }

    public static CV getCv() {
        return cv;
    }

    public static void setCv(CV cv) {
        FileParcer.cv = cv;
    }

    public static void CVparser(List<UploadedFile> activeFilesInSession){
        for(UploadedFile file:activeFilesInSession){
            String[] strings=null;
            if(fyleTypeIsPDF(file.getName())){
            strings=pdfParse(file.getName());
            }else{
                strings=docParse(file.getName());
            }
            if (strings==null)
                return;
            if(strings.length<MaxSize&&strings.length>MinSize) {
                CV cv = TextParser.createCV(Arrays.asList(strings));
                if (isCV(cv)) {
                    cvList.add(cv);
                }
            }
        }
    }

    private static Boolean isCV(CV cv){
        int sizeCV=sizeStr(cv.getSkills())+sizeStr(cv.getExp())+sizeStr(cv.getEdu())+sizeStr(cv.getLang());
        int sizeCo=sizeStr(cv.getContact().getFullName())+sizeStr(cv.getContact().getEmail())+sizeStr(cv.getContact().getPhone());
        int resSize=sizeCV+sizeCo;
        return (resSize>0)?true:false;
    }

    private static int sizeStr(String string){
        return (string!=null?string.length():0);
    }

    private static boolean fyleTypeIsPDF(String fName){
        String[] parts=fName.split("\\.");
        return ("pdf".equals(parts[parts.length-1])?true:false);
    }
    private static String[] pdfParse(String fName){
        String[] strings=null;
        try {
            PDDocument document = null;
            File file = new File(path+fName);

            document = PDDocument.load(file);
            document.getClass();
            if (!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper Tstripper = new PDFTextStripper();

                String st = Tstripper.getText(document);
                String regularParag="(?m)(?=^\\s{"+delimAnalyzer(st.split("\n"))+"})";
                strings = st.trim().split(regularParag);
            }
        } catch (Exception e) {
            System.out.println("FILE is not exist!");
        }
        return strings;
    }

    private static String[] docParse(String fName){
        String[] strings=null;
        try
        {
            File file = new File(path+fName);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            HWPFDocument document = new HWPFDocument(fis);
            WordExtractor extractor = new WordExtractor(document);
            String regularParag="(?m)(?=^\\s{"+delimAnalyzer(extractor.getParagraphText())+"})";
            strings=extractor.getText().split(regularParag);
        }
        catch (Exception exep)
        {
            System.out.println("FILE is not exist!");
        }
        return strings;
    }



    public static String parseStatus(UploadedFile activeFileInSession){
            String fName=activeFileInSession.getName();
            String[] strings=null;
            if(fyleTypeIsPDF(fName)){
            strings=pdfParse(fName);
            }else{
                strings=docParse(fName);
            }
        if (strings==null)
            return " - not parsed (FILE NOT LOADED)";
            if(strings.length<MaxSize&&strings.length>MinSize) {
                return " - parsed successful";
            }
            return " - not parsed";
        }

    private static int delimAnalyzer(String[] strings ){
        int[] space=spaceFiller(strings);
        int spaceType=spaceTyper(space);
        int result=resultTyper(spaceType);
        return result;
    }

    private static int[] spaceFiller(String[] strings){
        int[] space=new int[4];
        int counter=0;
        for(int i=0;i<=strings.length-1;++i){
            if(strings[i].length()==1||strings[i].length()==2){
                ++counter;
            }else if(counter>0&&strings[i].length()>2){
                switch (counter){
                    case 1: space[0]++;
                        break;
                    case 2: space[1]++;
                        break;
                    case 3: space[2]++;
                        break;
                    case 4: space[3]++;
                        break;
                }
                counter=0;
            }
        }
        return space;
    }

    public static int spaceTyper(int[] space){
        int spaceCounter=Integer.MAX_VALUE;
        int spaceType=0;
        for(int i=0;i<=space.length-1;++i){
            if(space[i]>0&&space[i]<=spaceCounter){
                spaceCounter=space[i];
                spaceType=i;
            }
        }
        return spaceType;
    }

    public static int resultTyper(int spaceType){
        return (++spaceType==1?1:2*(spaceType-1));
    }
}
