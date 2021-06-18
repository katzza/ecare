import com.ecare.dao.*;
import com.ecare.domain.*;
import com.ecare.dto.UserDto;
import com.ecare.services.ClientService;
import com.ecare.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

/*@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = {Config.class})*/
//@Transactional
public class UserServiceTest {

    @Mock
    UserDAO userDAO;

    @Mock
    ClientService clientService;

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

        publicUserDto();

        publicUserEntity();

        client = new ClientEntity(userEntity);
        dtoList.add(userDto);
        entityList.add(userEntity);
    }

    private void publicUserEntity() {
        userEntity.setRole(UserRole.ROLE_CLIENT);
        userEntity.setEmail("test@tt.ee");
        userEntity.setPassword("$2a$10$KqaFxPq6a34ZfTou33B.bOUGuNdZxt5pAdU7HMNbxVcp9jYcS8T3C");
    }

    private void publicUserDto() {
        userDto.setUserRole(UserRole.ROLE_CLIENT);
        userDto.setEmail("test@tt.ee");
        userDto.setPassword("321");
    }

    @Test
    public void saveUserTest() {
        when(modelMapper.map(userDto, UserEntity.class)).thenReturn(userEntity);
        userService.saveUser(userDto);

        verify(userDAO, times(1)).findByEmail(userDto.getEmail());
        verify(userDAO, times(1)).save(userEntity);
        verify(clientService, times(1)).createClient(userEntity);
    }

    @Test
    public void findByEmailTest() {
        when(userDAO.findByEmail(userDto.getEmail())).thenReturn(entityList);
        when(modelMapper.map(userEntity, UserDto.class)).thenReturn(userDto);

        UserDto foundByEmail = userService.findByEmail(userEntity.getEmail());

        verify(userDAO, times(1)).findByEmail(userDto.getEmail());
        assertEquals(foundByEmail, userDto);
    }

    @Test
    public void findByEmailTestNegativ() {
        when(userDAO.findByEmail(userDto.getEmail())).thenReturn(Collections.emptyList());
        assertThrows(UsernameNotFoundException.class, () -> userService.findByEmail(userDto.getEmail()));

    }

    @Test(expected = UsernameNotFoundException.class) //4Junit only
    public void findByEmailTestNegativNeg() {
        when(userDAO.findByEmail(userDto.getEmail())).thenReturn(Collections.emptyList());
        userService.findByEmail(userDto.getEmail());

    }
}
