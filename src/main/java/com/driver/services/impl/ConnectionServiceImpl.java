package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ConnectionRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    UserRepository userRepository2;
    @Autowired
    ServiceProviderRepository serviceProviderRepository2;
    @Autowired
    ConnectionRepository connectionRepository2;

    @Override
    public User connect(int userId, String countryName) throws Exception
    {
        User user = userRepository2.findById(userId).get();

        if(user.getConnected()) throw new Exception("Already connected");
        else if(user.getCountry().equals(countryName)) return user;

        List<ServiceProvider> list = user.getServiceProviderList();
        if(list == null) throw new Exception("Unable to connect");
        for(ServiceProvider sp : list)
        {
            if(sp.getCountryList().contains(user.getCountry()))
            {
                user.setMaskedIp(user.getCountry().getCode()+"."+sp.getId()+"."+user.getId());
                user.setConnected(true);

                Connection  connection = new Connection(sp, user);
                user.getConnectionList().add(connection);
                sp.getConnectionList().add(connection);
                sp.getUsers().add(user);

                serviceProviderRepository2.save(sp);
                return user;
            }
        }
        throw new Exception("Unable to connect");
    }
    @Override
    public User disconnect(int userId) throws Exception
    {
        User user = userRepository2.findById(userId).get();
        if(!user.getConnected()) throw new Exception("Already disconnect");

        user.setConnected(false);
        user.setMaskedIp(null);

        userRepository2.save(user);

        return user;
    }
    @Override
    public User communicate(int senderId, int receiverId) throws Exception
    {
        User sender = userRepository2.findById(senderId).get();
        User reciever = userRepository2.findById(senderId).get();

        if(reciever.getConnected())
        {
            if(reciever.getMaskedIp().substring(0,3).equals(sender.getCountry().getCode()))
            {
                return sender;
            }
            else
            {
                throw new Exception("Cannot establish communication");
            }
        }
        else
        {
            if(reciever.getCountry().getCode().equals(sender.getCountry().getCode()))
            {
                return sender;
            }
            else
            {
                return connect(senderId, reciever.getCountry().getCountryName().name());
            }
        }
    }
}
