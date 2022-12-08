package customer.management.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import customer.management.constants.CustomerConstants;
import customer.management.entity.Address;
import customer.management.response.BaseResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import customer.management.dao.CustomerDao;
import customer.management.entity.Customer;
import customer.management.service.CustomerService;
import customer.management.vo.CustomerVo;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private RestTemplate restTemplate;

    @Autowired
    CustomerDao customerDao;

    BaseResponse response = new BaseResponse();

    @Override
    @Transactional
    public BaseResponse save(CustomerVo customerVo, Errors errors) {
        String msg = "";
        Gson gson = new Gson();
        if (errors.hasErrors()) {
            for (ObjectError error1 : errors.getAllErrors()) {
                msg += ((FieldError) error1).getField() + " " + error1.getDefaultMessage() + ", ";
            }
            response.setResultMessage(msg);
        } else if (customerVo.getAddress() != null && customerVo.getAddress().size() > 5) {
            response.setResultMessage("Address should not more then five.");
        } else if (!isValidAddress(customerVo.getAddress())) {
            response.setResultMessage("Only One address should be primary address.");
        } else {
            Customer user = new Customer();
            customerVo.setCreatedDate(LocalDateTime.now());
            BeanUtils.copyProperties(customerVo, user);
            List<Address> newAddress = new ArrayList<>();
            /*final List<Address> addresses = customerVo.getAddress();
            for (Address address1 : addresses) {
                Object forEntity = restTemplate.getForEntity("https://viacep.com.br/ws/%22+address1.getState()+%22/%22+address1.getCity()+%22/%22+address1.getStreet()+%22/json/", Object.class).getBody();
                JsonArray jsonArray = gson.fromJson(gson.toJson(forEntity), JsonArray.class);
                if (!jsonArray.isJsonNull()){
                    JsonObject asJsonObject = (JsonObject) jsonArray.get(1);
                    JsonElement jsonElement = asJsonObject.get("cep");
                    address1.setZipCode(jsonElement.getAsString());
                    newAddress.add(address1);
                }
            }*/
            customerVo.setAddress(newAddress);
            customerDao.save(user);
            response.setResultMessage("Data is saved");
        }
        return response;

    }

    @Override
    @Transactional
    public BaseResponse update(Integer customerId, CustomerVo customerVo) {
        Optional<Customer> customerOptional = customerDao.findById(customerId);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            if (customerVo.getFullName() != null) {
                customer.setFullName(customerVo.getFullName());
            }

            if (customerVo.getContactNo() != null) {
                customer.setContactNo(customerVo.getContactNo());
            }

            if (customerVo.getCpfCnpj() != null) {
                customer.setCpfCnpj(customerVo.getCpfCnpj());
            }

            if (customerVo.getEmail() != null) {
                customer.setEmail(customerVo.getEmail());
            }

            if (customerVo.getGender() != null) {
                customer.setGender(customerVo.getGender());
            }

            if (customerVo.getType() != null) {
                customer.setType(customerVo.getType());
            }
            customerVo.setUpdatedDate(LocalDateTime.now());
            customerDao.save(customer);
            response.setResultMessage(CustomerConstants.DATA_UPDATED_SUCCESSFULLY);
            return response;
        } else {
			throw new RuntimeException("Error while update customer");
        }
	}

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value="id", key="#customer.id") })
    public BaseResponse delete(Integer id) {
        if (customerDao.existsById(id)) {
            customerDao.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
        response.setResultMessage(CustomerConstants.DATA_DELETED_SUCCESSFULLY);
        return response;
    }

    @Override
    @Transactional
    public CustomerVo get(Integer id) {
        Optional<Customer> customerOptional = customerDao.findById(id);
        CustomerVo customerVo = null;
        if (customerOptional.isPresent()) {
            customerVo = new CustomerVo();
            BeanUtils.copyProperties(customerOptional.get(), customerVo);
        } else {
            throw new EntityNotFoundException();
        }

        return customerVo;
    }

    @Override
    @Transactional
    public List<CustomerVo> getAll() {
        List<Customer> customerList = customerDao.findAll();
        List<CustomerVo> customerVoList = new ArrayList<>();
        if (customerList != null && !customerList.isEmpty()) {
            for (Customer customer : customerList) {
                CustomerVo customerVo = new CustomerVo();
                BeanUtils.copyProperties(customer, customerVo);
                customerVoList.add(customerVo);
            }
        }
        return customerVoList;
    }

    @Override
    public Page<CustomerVo> getAllPagable(PageRequest pageable, String name) {
        Page<Customer> customerList = customerDao.findAll(pageable);
        List<CustomerVo> customerVoList = new ArrayList<>();
        if (customerList != null && !customerList.isEmpty()) {
            for (Customer customer : customerList) {
                CustomerVo customerVo = new CustomerVo();
                BeanUtils.copyProperties(customer, customerVo);
                customerVoList.add(customerVo);
            }
        }
        Page<CustomerVo> pages = new PageImpl<>(customerVoList, pageable, customerVoList.size());
        return pages;
    }

    private Boolean isValidAddress(List<Address> addressList) {

        int count = 0;
        for (Address address : addressList) {
            if (addressList.size() == 1) {
                address.setIsPrimaryAddress(true);
                return true;
            }
            if (address.getIsPrimaryAddress() != null && address.getIsPrimaryAddress()) {
                count++;
            }

        }
        if (count == 0 && !addressList.isEmpty()) {
            addressList.get(0).setIsPrimaryAddress(true);
            return true;
        }
        if (count > 1 || count == 0) {
            return false;
        }
        return true;
    }
}

