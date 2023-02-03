package com.example.hotel.repository;


import com.example.hotel.models.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface EventRequestRepository extends JpaRepository<EventRequest, Long> {
}
