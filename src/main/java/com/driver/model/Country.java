package com.driver.model;// Note: Do not write @Enumerated annotation above CountryName in this model.

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    int id;

    CountryName countryName;

    String code;

    @OneToOne(mappedBy = "country", cascade = CascadeType.ALL)
    User user;

    @ManyToOne
    ServiceProvider serviceProvider;
}