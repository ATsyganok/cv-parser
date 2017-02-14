import cloud.molddata.parser.cv.parser.CCSeacrher;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Андрей on 14.02.2017.
 */
public class CCSearcherTest {
    @Test
    public void testFindFileCountryCode(){
        CCSeacrher CCSeacrherTest = new CCSeacrher();

        String result0 = CCSeacrherTest.findFileZipCode("+38050");
        String result1 = CCSeacrherTest.findFileZipCode("+38044");

        assertEquals("/opt/tomcat/temp/location/phone-codes.json", result0);
        assertEquals("/opt/tomcat/temp/location/phone-codes.json", result1);

    }
}
