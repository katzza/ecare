import com.ecare.dao.ClientDAO;
import com.ecare.dao.ContractDAO;
import com.ecare.dao.NumberDAO;
import com.ecare.dao.TariffDAO;
import com.ecare.domain.*;
import com.ecare.dto.ContractDto;
import com.ecare.dto.NumberDto;
import com.ecare.services.ClientService;
import com.ecare.services.ContractFacade;
import lombok.extern.slf4j.Slf4j;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

@Slf4j
/*@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = {Config.class})
@Transactional*/
public class ContractServiceTest {

    @Mock
    ContractDAO contractDAO;
    @Mock
    ClientService clientService;
    @Mock
    ClientDAO clientDAO;
    @Mock
    TariffDAO tariffDAO;
    @Mock
    NumberDAO numberDAO;
    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    private ContractFacade contractFacade;
    private List<NumberDto> numberList = new ArrayList<>();
    private List<ContractDto> dtoList = new ArrayList<>();
    private List<ContractEntity> entityList = new ArrayList<>();

  /*  @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(contractService.findById(1)).thenReturn(ContractDto.builder().contractId(1).build());

        numberList.add(NumberDto.builder().id(1).phoneNumber("1234567").build());
        dtoList.add(ContractDto.builder().contractId(1)
                .clientEmail("blocked@web.de").blockedByUser(true).phoneNumber(numberList.get(0))
                .blockedByCompany(true).build());
        entityList.add(ContractEntity.builder().contractId(1)
                .blockedByUser(true).phoneNumber("1234567")
                .blockedByCompany(true).build());
    }

    @Test
    public void contractCreated() {
        ContractDto contractDto = dtoList.get(0);
        ContractEntity contractEntity = entityList.get(0);
        when(modelMapper.map(contractDto, ContractEntity.class)).thenReturn(contractEntity);
        contractService.save(contractDto);
        verify(contractDAO, times(1)).save(contractEntity);
    }*/

}
