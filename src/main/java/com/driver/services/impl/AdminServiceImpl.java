package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Override
    public Admin register(String username, String password)
    {
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        adminRepository1.save(admin);
        return admin;
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName)
    {
        Admin admin = adminRepository1.findById(adminId).get();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setName(providerName);
        serviceProvider.setAdmin(admin);

        admin.getServiceProviders().add(serviceProvider);

        adminRepository1.save(admin);
        return admin;
    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception
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

        ServiceProvider serviceProvider = serviceProviderRepository1.findById(serviceProviderId).get();

        country.setServiceProvider(serviceProvider);

        serviceProvider.getCountryList().add(country);

        serviceProviderRepository1.save(serviceProvider);
        return serviceProvider;
    }
}
