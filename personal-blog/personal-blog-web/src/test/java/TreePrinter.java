import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * Created by MOKE on 2019/2/21.
 */
public class TreePrinter {

    public static void main(String[] args) {
        String password = new Md5PasswordEncoder().encodePassword("123456", "1916986844@qq.com");
        System.out.println(password);
    }
}
