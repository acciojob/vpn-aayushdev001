package com.driver.services.impl;

import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.model.User;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public User register(String username, String password, String countryName) throws Exception
    {
        Country country = new Country();
        if(countryName.toUpperCase().equals(CountryName.AUS.name()))
            country.setCountryName(CountryName.AUS);
        else if(countryName.toUpperCase().equals(CountryName.CHI.name()))
            country.setCountryName(CountryName.CHI);
        else if(countryName.toUpperCase().equals(CountryName.IND.name()))
            country.setCountryName(CountryName.IND);
        else if(countryName.toUpperCase().equals(CountryName.JPN.name()))
            country.setCountryName(CountryName.JPN);
        else if(countryName.toUpperCase().equals(CountryName.USA.name()))
            country.setCountryName(CountryName.USA);
        else throw new Exception("Country not found");

        List<Country> list = countryRepository3.findAll();
        for(Country c : list)
        {
            if(c.getCode().equals(country.getCode())) country = c;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCountry(country);
        user.setOriginalIp(country.getCode()+"."+user.getId());

        country.setUser(user);

        countryRepository3.save(country);

        return user;
    }

    @Override
    public User subscribe(Integer userId, Integer serviceProviderId)
    {
        ServiceProvider serviceProvider = serviceProviderRepository3.findById(serviceProviderId).get();
        User user = userRepository3.findById(userId).get();

        user.getServiceProviderList().add(serviceProvider);

        serviceProvider.getUsers().add(user);

        serviceProviderRepository3.save(serviceProvider);

        return user;
    }
}
