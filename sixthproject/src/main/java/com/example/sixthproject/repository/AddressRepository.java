package com.example.sixthproject.repository;

import com.example.sixthproject.model.Address;
import com.example.sixthproject.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(UserModel user);
}




