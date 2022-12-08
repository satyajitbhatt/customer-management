package customer.management.controller;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import customer.management.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import customer.management.service.CustomerService;
import customer.management.vo.CustomerVo;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@PostMapping
	public ResponseEntity<BaseResponse> save(@RequestBody CustomerVo customerVo, Errors errors) {
		BaseResponse response = customerService.save(customerVo, errors);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}


	@GetMapping("/{id}")
	public CustomerVo get( @PathVariable Integer id) {
		return customerService.get(id);
	}


	@GetMapping(value = "/all", headers = {"Accept-version=v1"})
	public List<CustomerVo> getAll() {
		return customerService.getAll();
	}

	@GetMapping(value = "/all", headers = {"Accept-version=v2"})
	public Page<CustomerVo> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
								   @RequestParam(name = "size", defaultValue = "10") int size,
								   @RequestParam(name = "sort", defaultValue = "asc") String sort,
								   @RequestParam(name = "field", defaultValue = "id") String field,
								   @RequestParam(name = "name", required = false) String name) {
		PageRequest pageable = PageRequest.of(page, size, Sort.by(sort, field));
		return customerService.getAllPagable(pageable,name);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BaseResponse> update(@PathVariable Integer id, @RequestBody CustomerVo customerVo) {
		BaseResponse response = customerService.update(id, customerVo);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<BaseResponse> delete( @PathVariable Integer id) {
		BaseResponse response = customerService.delete(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Cacheable(value="cacheSampleData", key="#id")
	@GetMapping("/test/{id}")
	public CustomerVo testEndpoint(@PathVariable Integer id) {
		return customerService.get(id);
	}
	
	@ExceptionHandler(EntityExistsException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public String handleEntityExistsException(EntityExistsException e) {
	    return e.getMessage();
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleEntityNotFoundException(EntityNotFoundException e) {
	    return e.getMessage();
	}
}
