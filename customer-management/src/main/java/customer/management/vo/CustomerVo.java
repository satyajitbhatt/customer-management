package customer.management.vo;

import customer.management.entity.Address;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CustomerVo {

	//private Integer id ;

	private String fullName ;

	@NotBlank(message = "Gender is Mandatory")
	private String gender ;

	@Pattern(regexp = "^\\d{10}$", message = "Mobile number is invalid.")
	private String contactNo ;

	@Pattern(regexp = "([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})",message = "Invalid cpf or Cnpj")
	private String cpfCnpj ;

	@NotBlank(message = "Type is Mandatory")
	private String type ;

	@Email(message = "Email is invalid")
	private String email ;

	private List<Address> address;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	public CustomerVo() {

	}
}