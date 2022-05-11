package com.fp.knp.Models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "registration_code", "email"}))
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Registration code is required")
    @Column(name = "registration_code")
    private String registrationCode;

    @Column(name = "full_name")
    private String fullName;

    @Email(message = "Email is not valid")
    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    public Customer() {
    }

    public Customer(String regCode, String fullName, String email, String telephone) {
        this.registrationCode = regCode;
        this.fullName = fullName;
        this.email = email;
        this.telephone = telephone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String code) {
        this.registrationCode = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String phone) {
        this.telephone = telephone;
    }
}
