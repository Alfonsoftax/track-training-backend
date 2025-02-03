package com.track.training.app.customer.core.implementation.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.track.training.app.customer.core.domain.Address;
import com.track.training.app.customer.core.domain.Customer;
import com.track.training.app.customer.core.domain.PaymentMethod;

@Mapper(uses = { BaseMapper.class })
public interface EventsMapper {

    EventsMapper INSTANCE = Mappers.getMapper(EventsMapper.class);

//    com.track.training.app.customer.core.outbound.events.dtos.Address asAddress(Address address);

//    com.track.training.app.customer.core.outbound.events.dtos.PaymentMethod asPaymentMethod(
//            PaymentMethod paymentMethod);

//    com.track.training.app.customer.core.outbound.events.dtos.CustomerEvent asCustomerEvent(
//            Customer customer);

//    com.track.training.app.customer.core.outbound.events.dtos.CustomerEvent asCustomerEvent(Long id);

}
