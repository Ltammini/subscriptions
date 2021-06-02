package com.filmland.subscription.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    private static final long serialVersionUID = 5560221391479816950L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    @OneToMany(mappedBy ="customerId", fetch = FetchType.EAGER)
    private List<Subscription> subscriptions;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return this.email + this.firstName + this.lastName;
    }
}
