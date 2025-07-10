package flik;
import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {
    @Test
    public void TestFlik() {
        int j=0;
        for (int i = 0; i <= 500; i++,j++) {
            assertTrue(Flik.isSameNumber(i,j));
        }
    }
}
