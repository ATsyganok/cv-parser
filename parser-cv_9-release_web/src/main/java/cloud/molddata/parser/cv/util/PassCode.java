package cloud.molddata.parser.cv.util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Andrey on 20.03.2017.
 */
public class PassCode {
     static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        String pas = "admin";
        System.out.printf("res=" + bCryptPasswordEncoder.encode(pas));

    }
}
