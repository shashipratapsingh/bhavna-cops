package product.service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {
    private Long id;
    private int code;
    private String message;

    public void ErrorResponse(Long id, int code, String message) {
       this.id=id;
        this.code = code;
        this.message = message;
    }

}

