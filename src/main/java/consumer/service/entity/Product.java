package consumer.service.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

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

}