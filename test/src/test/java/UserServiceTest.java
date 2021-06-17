import com.ecare.dao.*;
import com.ecare.domain.*;
import com.ecare.dto.UserDto;
import com.ecare.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@Slf4j
/*@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = {Config.class})*/
//@Transactional
public class UserServiceTest extends UserService {

    @Mock
    UserDAO userDAO;

    @Mock
    ClientDAO clientDAO;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private List<UserDto> dtoList = new ArrayList<>();
    private List<UserEntity> entityList = new ArrayList<>();

    private UserEntity userEntity = new UserEntity();
    private UserDto userDto = new UserDto();
    private ClientEntity client;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        userDto.setUserRole(UserRole.ROLE_CLIENT);
        userDto.setEmail("test@tt.ee");
        userDto.setPassword("321");

        userEntity.setRole(UserRole.ROLE_CLIENT);
        userEntity.setEmail("test@tt.ee");
        userEntity.setPassword("$2a$10$KqaFxPq6a34ZfTou33B.bOUGuNdZxt5pAdU7HMNbxVcp9jYcS8T3C");

        client = new ClientEntity(userEntity);
        dtoList.add(userDto);
        entityList.add(userEntity);
    }

    @Test
    public void saveUserTest() {
        when(modelMapper.map(userDto, UserEntity.class)).thenReturn(userEntity);
        userService.saveUser(userDto);

        verify(userDAO, times(1)).findByEmail(userDto.getEmail());
        verify(userDAO, times(1)).save(userEntity);
        verify(clientDAO, times(1)); //как подпихнуть сюда клиента?
    }

    @Test
    public void findByEmailTest() {
        when(userDAO.findByEmail(userDto.getEmail())).thenReturn(entityList);
        when(modelMapper.map(userEntity, UserDto.class)).thenReturn(userDto);

        UserDto foundByEmail = userService.findByEmail(userEntity.getEmail());

        verify(userDAO, times(1)).findByEmail(userDto.getEmail());
        assertEquals(foundByEmail, userDto);
    }
}
