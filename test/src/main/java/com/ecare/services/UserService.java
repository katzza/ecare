package com.ecare.services;

import com.ecare.dao.ClientDAO;
import com.ecare.dao.UserDAO;
import com.ecare.domain.UserEntity;
import com.ecare.domain.UserRole;
import com.ecare.dto.UserDto;
import com.ecare.error.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    ClientDAO clientDAO; //todo clientService

    @Autowired
    ClientService clientService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @SuppressWarnings("unchecked")
    @Transactional
    public void saveUser(UserDto user) {
        if (userDAO.findByEmail(user.getEmail()).size() > 0) {
            log.info("Account-exist test NOK: There is an account with that email address");
            throw new UserAlreadyExistException(String.format("There is an account with that email address: %s", user.getEmail()));
        } else {
            log.info("Account-exist test is OK");
            UserEntity userEntity = convertToEntity(user);
            userEntity.setRole(UserRole.ROLE_CLIENT);
            userDAO.save(userEntity);
            clientService.createClient(userEntity);
        }
    }


    @Transactional
    public UserDto findByEmail(String email) {
        List<UserEntity> userEntity = userDAO.findByEmail(email);
        if (userEntity.size() == 0) {
            throw new UsernameNotFoundException("User not found by name: " + email);
        } else {
            return convertToDto(userEntity.get(0));
        }

    }

    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) { //login after registration
        try {
            request.login(username, password);
        } catch (ServletException e) {
            log.info("Error while login {}", e.getMessage());
        }
    }

    public UserDto convertToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    public UserEntity convertToEntity(UserDto userDto) {
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userEntity;
    }
}
