package com.ecare.services;

import com.ecare.dao.NumberDAO;
import com.ecare.domain.NumberEntity;
import com.ecare.dto.ContractDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class NumberService {

    @Autowired
    NumberDAO numberDAO;
    @Autowired
    ClientService clientService;

    private final int FREE_NUMBER_SIZE = 3;
    private final int START_RANDOM = 999999;
    private final int FINISH_RANDOM = 9000001;

    /**
     * this method generate new random phone-numbers for contract and save new numbers after unique-check
     */
    public void generateFreeNumbers() {
        if (numberDAO.getFreeNumbers().size() < FREE_NUMBER_SIZE) {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            for (int i = 0; i < FREE_NUMBER_SIZE; i++) {
                int phoneNumber = random.nextInt(FINISH_RANDOM) + START_RANDOM;
                NumberEntity newNumber = new NumberEntity(Integer.toString(phoneNumber));
                if (numberDAO.findByPhone(newNumber.getPhoneNumber()).isEmpty()) {
                    numberDAO.save(newNumber);
                }
            }
        }
    }

    /**
     * this method adds free phone-numbers to contractDto
     * @param contractDto - contract to which numbers were put
     */
    public void putFreeNumbersToContractDto(ContractDto contractDto) {
        Map<String, Integer> mapNumbers = contractDto.getNumbers();
        numberDAO.getFreeNumbers().forEach(array -> mapNumbers.put((String) array[1], (Integer) array[0]));
    }

    /**
     * this method turns to true boolean isBooked is booked in NumberEntity
     * @param numberId -which NumberEntity will be booked
     * @return changed NumberEntity
     */
    public NumberEntity bookNumber(int numberId) {
        NumberEntity phoneNumber = (NumberEntity) numberDAO.findById(numberId);
        phoneNumber.setBooked(true);
        numberDAO.update(phoneNumber);
        return phoneNumber;
    }
}
