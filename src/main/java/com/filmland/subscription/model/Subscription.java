package com.filmland.subscription.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Subscription {

    private static final long serialVersionUID = 5567221391479816950L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private String name;
    private int remainingContent;
    private Double price;
    private String owner;
    private LocalDateTime startDate;
}
