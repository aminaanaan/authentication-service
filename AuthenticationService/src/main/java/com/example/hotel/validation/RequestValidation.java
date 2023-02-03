package com.example.hotel.validation;

import com.example.hotel.dto.*;
import com.example.hotel.exception.*;
import com.example.hotel.repository.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

@AllArgsConstructor
@Component
public class RequestValidation {

    UserRepository korisnikRepository;
    public void validateId(Long id) {
        if (!korisnikRepository.existsById(id)) {
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }
    }
    public void validateEmployee(Long id){
        if(!korisnikRepository.findById(id).get().getRole().equals("USER")){
            throw new BaseException("Unauthorized access", HttpStatus.FORBIDDEN);
        }
    }
}
