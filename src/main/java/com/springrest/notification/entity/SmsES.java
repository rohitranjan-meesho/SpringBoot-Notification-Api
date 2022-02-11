package com.springrest.notification.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springrest.notification.helper.Indices;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


import javax.persistence.Id;
import java.sql.Timestamp;

@Document(indexName = Indices.SMS_INDEX)

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsES {


    @Id
    private int id;

    @Field(type= FieldType.Text)
    private String phone_number;

    @Field(type= FieldType.Text)
    private String message;

    @Field(type= FieldType.Text)
    private String status;
    @Field(type= FieldType.Keyword)
    private int failure_code;

    @Field(type= FieldType.Text)
    private String failure_comments;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp created_at;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updated_at;

    @Field(type= FieldType.Text)
    private String request_UUID;
}
