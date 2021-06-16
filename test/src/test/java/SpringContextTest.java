import com.ecare.config.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 ** https://stackoverflow.com/questions/23999043/failed-to-load-applicationcontext-in-unittests-no-xml-spring
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = {Config.class})

public class SpringContextTest {
    @Test
    public void whenSpringContextIsInstantiated_thenNoExceptions() {
    }
}

