import com.ecare.config.Config;
import com.ecare.dao.UserDAO;
import com.ecare.domain.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = {Config.class})
@Transactional
public class UserDAOUnitTest {

    @Autowired
    UserDAO userDAO = new UserDAO();

    /**
     * without database, only in session
     */

    @Test
    public void userDAOCreatePositivAndDelete() {
        String login = UUID.randomUUID().toString().substring(13) + "@test.de";
        String password = UUID.randomUUID().toString().substring(16);
        List<UserEntity> userToBeNotFound = userDAO.findByEmail(login);
        Assertions.assertEquals(userToBeNotFound.size(), 0);
        log.info("userToBeNotFound-check is OK");
        UserEntity testUser = new UserEntity(login, password);
        userDAO.save(testUser);
        log.info("userEntity saved :" + login + password);
        Assertions.assertEquals(userDAO.findByEmail(login).size(), 1);
        userDAO.delete(testUser);
        Assertions.assertEquals(userDAO.findByEmail(login).size(), 0);
    }

}
