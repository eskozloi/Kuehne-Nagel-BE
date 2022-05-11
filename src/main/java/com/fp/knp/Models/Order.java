package com.fp.knp.Models;

import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

//import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Entity
@Table(name = "orders", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "Customer id is required")
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "submission_date")
    private Date submissionDate;

    @PrePersist
    protected void onCreate() {
        submissionDate = Date.valueOf(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
    }

    public Order() {
    }

    public Order(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }
}
