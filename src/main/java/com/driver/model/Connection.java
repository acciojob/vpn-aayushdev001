package com.driver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Connection
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @JoinColumn
    @ManyToOne
    ServiceProvider serviceProvider;

    @JoinColumn
    @ManyToOne
    User user;

    public Connection(ServiceProvider serviceProvider, User user)
    {
        this.serviceProvider = serviceProvider;
        this.user = user;
    }
}
