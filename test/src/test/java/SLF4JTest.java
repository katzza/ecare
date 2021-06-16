import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class SLF4JTest {

 /* //  private static Logger logger = LoggerFactory.getLogger(SLF4JTest.class);
    public static void main(String[] args) {
        log.debug("Debug log message");
        log.info("Info log message");
        log.error("Error log message");
    }*/

    @Test
    public void logTest(){
        log.debug("Debug log message");
        log.info("Info log message");
        log.error("Error log message");
    }
}
