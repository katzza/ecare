import com.ecare.dao.ClientDAO;
import com.ecare.domain.ClientEntity;
import com.ecare.domain.ContractEntity;
import com.ecare.domain.UserEntity;
import com.ecare.dto.ClientDto;
import com.ecare.dto.UserDto;
import com.ecare.error.UserNotFoundException;
import com.ecare.services.ClientService;
import com.ecare.services.ContractFacade;
import com.ecare.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    @Mock
    ClientDAO clientDAO;
    @Mock
    ContractFacade contractFacade;
    @Mock
    UserService userService;

    private ClientEntity clientEntity = new ClientEntity();
    private ClientDto clientDto = new ClientDto();
    private List<ClientEntity> entityList = new ArrayList<>();

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClientService clientService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveClientTest() {
        when(modelMapper.map(clientDto, ClientEntity.class)).thenReturn(clientEntity);
        clientService.saveClient(clientDto);
        verify(clientDAO, times(1)).save(clientEntity);
    }

    @Test
    public void updateClientTest() {
        when(modelMapper.map(clientDto, ClientEntity.class)).thenReturn(clientEntity);
        clientService.saveClient(clientDto);
        verify(clientDAO, times(1)).save(clientEntity);
    }


    @Test
    public void findClientByUserEmailPositive() {
        setEmailToClient();
        when(clientDAO.findClientByUserEmail(clientDto.getUser().getEmail())).thenReturn(entityList);
        when(modelMapper.map(clientEntity, ClientDto.class)).thenReturn(clientDto);

        ClientDto foundByEmail = clientService.findClientByUserEmail(clientDto.getUser().getEmail());
        verify(clientDAO, times(1)).findClientByUserEmail((clientDto.getUser().getEmail()));
        assertEquals(foundByEmail, clientDto);
    }

    @Test
    public void findClientByUserEmailNegative() {
        setEmailToClient();
        when(clientDAO.findClientByUserEmail(clientDto.getUser().getEmail())).thenReturn(Collections.emptyList());
        assertThrows(UserNotFoundException.class, () -> clientService.findClientByUserEmail(clientDto.getUser().getEmail()));
    }

    private void setEmailToClient() {
        clientDto.setUser(new UserDto());
        clientDto.getUser().setEmail("test@tt.tt");
        clientEntity.setUser(new UserEntity());
        clientEntity.getUser().setEmail("test@tt.tt");
        entityList.add(clientEntity);
    }

}
