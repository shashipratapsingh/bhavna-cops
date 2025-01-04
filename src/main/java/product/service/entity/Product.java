package product.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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