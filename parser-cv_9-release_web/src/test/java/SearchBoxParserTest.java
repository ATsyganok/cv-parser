import cloud.molddata.parser.cv.parser.BoxParser;
import cloud.molddata.parser.cv.parser.SearchBoxParser;
import cloud.molddata.parser.cv.model.CV;
import cloud.molddata.parser.cv.model.Contact;
import cloud.molddata.parser.cv.model.UploadedFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * The user enters text into a search box. This class is used
 * to parse that text into specific search terms (or tokens).
 * It eliminates common words, and allows for the quoting of text, using
 * double quotes.
 * JDK 7+.
 */
public final class SearchBoxParserTest {

    /*private static List<Contact> contactList=new ArrayList<>();

    public static List<Contact> getContactList() {
        List<Contact> contactListTemp = new ArrayList<>();
        contactListTemp = contactList;
        contactList = new ArrayList<>();
        return contactListTemp;
    }
    private static List<CV> cvList=new ArrayList<>();


    public static List<CV> getcvList() {
        List<CV> cvListTemp = new ArrayList<>();
        cvListTemp = cvList;
        cvList = new ArrayList<>();
        return cvListTemp;
    }

    static CV cv;

    public static CV getCv() {
        return cv;
    }

    public static void setCv(CV cv) {
        SearchBoxParserTest.cv = cv;
    }

    public static void CVparser(List<UploadedFile> activeFilesInSession){
        for(UploadedFile file:activeFilesInSession){
            System.out.println("1");
            String[] strings=null;
            if(fyleTypeIsPDF(file.getName())){
            System.out.println("2");
            strings=pdfParse(file.getName());
                for (int i=0; i<strings.length;i++)
                    System.out.println("STR["+i+"]="+strings[i]);
            }else{
                strings=docParse(file.getName());
            }
            if(strings.length<40&&strings.length>3) {
                System.out.println("3");
                System.out.println();
                CV cv = BoxParser.parseResume(Arrays.asList(strings));
                System.out.println("4");
                if (isCV(cv)) {
                    cvList.add(cv);
                }
            }
            System.out.println("5");
        }
    }

    private static Boolean isCV(CV cv){
        int sizeCV=sizeStr(cv.getSkills())+sizeStr(cv.getExp())+sizeStr(cv.getEdu())+sizeStr(cv.getLang());
        int sizeCo=sizeStr(cv.getContact().getFullName())+sizeStr(cv.getContact().getEmail())+sizeStr(cv.getContact().getPhone());
        int resSize=sizeCV+sizeCo;
        if(resSize>0)
            return true;
        return false;
    }

    private static int sizeStr(String string){
        if(string!=null)
            return string.length();
        return 0;
    }

    private static boolean fyleTypeIsPDF(String fName){
        String[] parts=fName.split("\\.");
        if("pdf".equals(parts[parts.length-1])){
            return true;
        }else {
            return false;
        }
    }
    private static String[] pdfParse(String fName){
        String[] strings=null;
        //ClassLoader classLoader = new SearchBoxParser().getClass().getClassLoader();
        try {
            PDDocument document = null;
            File file = new File("C:/uploaded-files/"+fName);
            //File file = new File("/opt/tomcat/temp/uploaded/"+fName);
            //File file = ResourceUtils.getFile(classLoader.getResource("resources/uploaded/" + fName).getFile());

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
            e.printStackTrace();
        }
        return strings;
    }

    private static String[] docParse(String fName){
        String[] strings=null;
        //ClassLoader classLoader = new SearchBoxParser().getClass().getClassLoader();
        try
        {
            File file = new File("C:/uploaded-files/"+fName);
            //File file = new File("/opt/tomcat/temp/uploaded/"+fName);
            //File file = ResourceUtils.getFile(classLoader.getResource("resources/uploaded/"+fName).getFile());
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            HWPFDocument document = new HWPFDocument(fis);
            WordExtractor extractor = new WordExtractor(document);
            String regularParag="(?m)(?=^\\s{"+delimAnalyzer(extractor.getParagraphText())+"})";
            strings=extractor.getText().split(regularParag);
        }
        catch (Exception exep)
        {
            System.out.println("FILE is not exist!");
            //exep.printStackTrace();
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
                System.out.println("doc leng="+strings.length);
                System.out.println("text="+strings[0].toString());
            }
            if(strings.length<40&&strings.length>0) {
                return " - parsed successful";
            }
            return " - not parsed";
        }

    @Test
    public void testDelimAnalyzer(String[] strings ){
        System.out.println("delimAnalyzer(strings)="+strings.length);
        int[] space=spaceFiller(strings);
        int spaceType=spaceTyper(space);
        int result=resultTyper(spaceType);
        return result;
    }*/


    @Test
    public void testSpaceTyper(){
        SearchBoxParser searchBoxParser = new SearchBoxParser();
        int result = searchBoxParser.spaceTyper(new int[]{10,5,3});
        assertEquals(2,result);

    }

    @Test
    public void testResultTyper(){
        SearchBoxParser searchBoxParser = new SearchBoxParser();

        int result = searchBoxParser.resultTyper(3);
        assertEquals(6, result);
    }
}
