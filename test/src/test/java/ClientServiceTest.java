import com.ecare.dao.ClientDAO;
import com.ecare.domain.ClientEntity;
import com.ecare.domain.UserEntity;
import com.ecare.dto.ClientDto;
import com.ecare.dto.UserDto;
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

}
