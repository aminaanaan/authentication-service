package com.example.hotel.service;

import com.example.hotel.dto.*;
import com.example.hotel.models.*;
import com.example.hotel.repository.*;
import com.example.hotel.rest.*;
import com.example.hotel.validation.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

import javax.validation.*;
import java.util.*;


@Service
public class UserService {

    RequestValidation requestValidation;

    @Autowired
    UserRepository korisnikRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String create(final UserRequest userRequest) throws ConstraintViolationException{
        UserEntity userEntity =new UserEntity();
        BeanUtils.copyProperties(userRequest, userEntity);
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userEntity.setProvider(AuthProvider.local);
        korisnikRepository.save(userEntity);
        return "User successfully created";
    }
    public List<User> findAll(){
        List<UserEntity> korisnici=korisnikRepository.findAll();
        List<User> responses=new ArrayList<User>();
        for(UserEntity userEntity :korisnici) {
            User user = new User();

            BeanUtils.copyProperties(userEntity, user);
            responses.add(user);
        }
        return responses;
    }
    public User findById(Long id){
        requestValidation.validateId(id);
        UserEntity userEntity =korisnikRepository.findById(id).get();
        User user =new User();
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }
public String updateKorisnik( User userRequest){
        requestValidation.validateId(userRequest.getId());
        UserEntity userEntity =korisnikRepository.findById(userRequest.getId()).get();
        BeanUtils.copyProperties(userRequest, userEntity);
        korisnikRepository.save(userEntity);
        return "Updated successfully";
}
public String deleteById(Long id){
        requestValidation.validateId(id);
        korisnikRepository.deleteById(id);
        return  "User is deleted successfully";
}


    public List<User> findAllGuests() {
        List<UserEntity> korisnici = korisnikRepository.findByRole("USER");

        List<User> responses = new ArrayList<User>();
        if (korisnici != null) {
            for (UserEntity userEntity : korisnici) {
                User user = new User();
                BeanUtils.copyProperties(userEntity, user);
                responses.add(user);
            }

        }
        return responses;
    }
}
