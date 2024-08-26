package com.ElectronicStore.ElectronicStore.Model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import  java.sql.Timestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="otp")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otpid",nullable = false)
    private long otpId;

    @Column(name ="otp",nullable = false,unique = true)
    private String otp;

    @Column(name="User_email",nullable = false)
    private String email;

    @Column(name = "otpGeneratedTime",nullable = false)
    @CreationTimestamp
    private Timestamp otpGeneratedTime;

}
