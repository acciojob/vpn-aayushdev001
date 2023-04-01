package com.driver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    int id;

    String username;

    String password;

    String originalIp;

    String maskedIp;

    boolean connected;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Connection> connectionList = new ArrayList<>();

    @ManyToMany
    @JoinColumn
    List<ServiceProvider> serviceProviderList = new ArrayList<>();

    @OneToOne
    @JoinColumn
    Country country;

    public boolean getConnected()
    {
        return connected;
    }
}
