import cloud.molddata.parser.cv.parser.SeacrherRegByPh;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by admin on 11.01.2017.
 */
public class SearcherRegByPhTest {
    @Test
    public void testFindFileCountryCode(){
        SeacrherRegByPh seacrherRegByPhTest = new SeacrherRegByPh();

        String result0 = seacrherRegByPhTest.findFileCountryCode("+38050");
        String result1 = seacrherRegByPhTest.findFileCountryCode("+38044");

        assertEquals("/opt/tomcat/temp/location/phone-codes.json", result0);
        assertEquals("/opt/tomcat/temp/location/phone-codes.json", result1);

    }

}
