package com.shammocodes.BhaiCycle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique=true)
    private String username;
//    @JsonIgnore
    private String password;
    private String name;
    private String department;
    private String hall;
    private int room;
    private String cycle;
    private int active;
    private String verificationcode;
    private String messengerid;
    private String cycle_status;
    private String req;
    private int contribution;
    private String phone;
    private long endinstance;
    private long h;
    private long m;
    private long s;
}
