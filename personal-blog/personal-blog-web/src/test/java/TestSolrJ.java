import org.junit.Test;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created by MOKE on 2019/2/19.
 */

public class TestSolrJ extends AbstractJUnit4SpringContextTests {

    @Test
    public void test(){
        TestSolrJ add = new TestSolrJ();
        System.out.print(add.invertedSequence("shabidongxi",2,5));

    }




    public String invertedSequence(String s,int b,int e){
        int i = 0;
        char[] temp = s.toCharArray();
        char[] result = new char[e-b+1];
        while(i<result.length){
            result[i] = temp[e--];
            i++;
        }
        return new String(result);
    }
}
