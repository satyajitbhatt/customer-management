package customer.management.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Address {

    @Id
    @GeneratedValue
    private Integer id ;

    private String street ;

    private Integer number ;

    private String neighbourhood ;

    private String city ;

    private String zipCode ;

    private String state ;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private Boolean isPrimaryAddress = false;

    @Version
    private Long version;
}
