package consumer.service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    private Long id;

    @NotEmpty(message = "Product name cannot be empty")
    private String name;

    private double price;
    private int quantity;
    private String description;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedBy;

    @PrePersist
    protected void onCreate() {
        this.createdBy = new Date();
        this.updatedBy = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedBy = new Date();
    }

}