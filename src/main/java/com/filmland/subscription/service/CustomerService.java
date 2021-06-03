package com.filmland.subscription.service;

import com.filmland.subscription.exception.NotFoundException;
import com.filmland.subscription.model.Customer;
import com.filmland.subscription.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Overriding method from UserDetailsService to get customer email and password
     * @param username
     * @return
     * @throws NotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws NotFoundException {
        Optional<Customer> optionalCustomer = findCustomerByEmail(username);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return new User(customer.getEmail(), customer.getPassword(), new ArrayList<>());
        }
        throw new NotFoundException("Customer not found with username: " + username);
    }

    /**
     *
     * @param user
     * @return
     */
    public Optional<Customer> findCustomerByEmail(String user) {
        return Optional.ofNullable(customerRepository.findOneByEmail(user));
    }
}

