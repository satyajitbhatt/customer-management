package customer.management.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Customer {

	@Id
	@GeneratedValue
	private Integer id;

	private String fullName;

	private String gender;

	private String contactNo;

	private String cpfCnpj;

	private String type;

	private String email;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	@Version
	private Long version;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	private List<Address> address;
}