package com.izertis.architecture.examples.customer.core.implementation.mappers;

import com.izertis.architecture.examples.customer.core.domain.Address;
import com.izertis.architecture.examples.customer.core.domain.Customer;
import com.izertis.architecture.examples.customer.core.domain.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { BaseMapper.class })
public interface EventsMapper {

    EventsMapper INSTANCE = Mappers.getMapper(EventsMapper.class);

//    com.izertis.architecture.examples.customer.core.outbound.events.dtos.Address asAddress(Address address);

//    com.izertis.architecture.examples.customer.core.outbound.events.dtos.PaymentMethod asPaymentMethod(
//            PaymentMethod paymentMethod);

//    com.izertis.architecture.examples.customer.core.outbound.events.dtos.CustomerEvent asCustomerEvent(
//            Customer customer);

//    com.izertis.architecture.examples.customer.core.outbound.events.dtos.CustomerEvent asCustomerEvent(Long id);

}
