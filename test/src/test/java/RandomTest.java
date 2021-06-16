import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class RandomTest {


    @Test
    public void logTest() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        int min = 999999;
        int max = 10000000;
        int diff = max - min;
        for (int i = 0; i < 10; i++) {
            int y = random.nextInt(diff + 1) + min;
            int z = random.nextInt(9000001) + 999999;
            log.info("" + y);
            log.info("" + z);
        }
    }
}
