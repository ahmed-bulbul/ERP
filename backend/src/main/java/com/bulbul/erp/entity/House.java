package com.bulbul.erp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String area;
    private int baths;
    private int beds;
    private int garages;
    private double price;
    private String latitude;
    private String longitude;
    private String status="pending";
    private LocalDate bookingDate;
    private boolean isBooked = false;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    @JsonIgnore
    private User bookedBy;
    @NotBlank(message = "address must not be empty")
    private String address;
    @NotBlank(message = "contact number must not be empty")
    private String contactNo;
    private String bookingStatus;

    private LocalDate checkIn;
    private LocalDate checkOut;

    private String review;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    private User user;

}
