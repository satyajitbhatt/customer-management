package customer.management.service;

import java.util.List;

import customer.management.response.BaseResponse;
import customer.management.vo.CustomerVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.Errors;

public interface CustomerService {

	//void save(CustomerVo customerVo) ;

	BaseResponse save(CustomerVo customerVo, Errors errors);


	//void update(Integer id, CustomerVo customerVo) ;
	BaseResponse update(Integer id, CustomerVo customerVo);

	//void delete(Integer id);
	BaseResponse delete(Integer id);

	CustomerVo get(Integer id);

	List<CustomerVo> getAll();

	Page<CustomerVo> getAllPagable(PageRequest pageable, String name);
}

