package com.example.lr15.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;


    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "views")
    private Integer views;
}
