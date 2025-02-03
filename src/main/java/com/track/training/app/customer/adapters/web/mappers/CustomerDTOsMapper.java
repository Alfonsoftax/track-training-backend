package com.track.training.app.customer.adapters.web.mappers;

import com.track.training.app.customer.adapters.web.model.CustomerDTO;
import com.track.training.app.customer.adapters.web.model.CustomerPaginatedDTO;
import com.track.training.app.customer.adapters.web.model.CustomerSearchCriteriaDTO;
import com.track.training.app.customer.core.domain.Customer;
import com.track.training.app.customer.core.inbound.dtos.CustomerSearchCriteria;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(uses = BaseMapper.class)
public interface CustomerDTOsMapper {

    CustomerDTOsMapper INSTANCE = Mappers.getMapper(CustomerDTOsMapper.class);

    // request mappings
    CustomerSearchCriteria asCustomerSearchCriteria(CustomerSearchCriteriaDTO dto);

    Customer asCustomer(CustomerDTO dto);

    // response mappings

    List<CustomerDTO> asCustomerDTOList(List<Customer> entityList);

    CustomerPaginatedDTO asCustomerPaginatedDTO(Page<Customer> page);

    default Page<CustomerDTO> asCustomerDTOPage(Page<Customer> page) {
        return page.map(this::asCustomerDTO);
    }

    CustomerDTO asCustomerDTO(Customer entity);

}
