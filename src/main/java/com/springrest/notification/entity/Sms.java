package com.springrest.notification.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(
        name="sms_table",
        uniqueConstraints = @UniqueConstraint(
                name="id_unique",
                columnNames="id"
        )
)
public class Sms {
    //@Pattern(regexp="(^$|[0-9]{10})")
    @Id
//    @SequenceGenerator(
//            name="id_sequence",
//            sequenceName = "id_sequence",
//            allocationSize=1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator="id_sequence"
//    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Pattern(regexp="^[+]?[0-9]{0,2}?[0-9]{10}$",message="Invalid Phone Number!")
    private String phone_number;
    private String message;
    private String status;

    //@Column(columnDefinition = "int")
    private int failure_code;

    private String failure_comments;


    @CreationTimestamp
    @Column( columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp created_at;

    @CreationTimestamp
    @Column(  columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updated_at;

//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Type(type="uuid-char")
//    @Column(columnDefinition = "VARCHAR(255)", insertable = false, updatable = false, nullable = false)
//    private String request_id;
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "request_UUID", unique=true,updatable = false, nullable = false)
    private String request_UUID;

    @PrePersist
    public void autofill() {
        this.request_UUID=UUID.randomUUID().toString();
    }
//    public Sms(String number, String message) {
//        this.number = number;
//        this.message = message;
//    }
//
//    public String getNumber() {
//        return number;
//    }
//
//    public String getMessage() {
//        return message;
//    }



}

