package com.src.train.track.User.mapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.src.train.track.User.model.UserDTO;
import com.src.train.track.User.model.Usuario;
import com.src.train.track.general.utils.BaseMapper;

@Mapper//(uses = { FinalClientMapper.class, PublicationReservationMapper.class })
public interface UserMapper extends BaseMapper<Usuario, UserDTO> {

//    @Override
//    @Mapping(target = Reservation.Properties.FINAL_CLIENT, ignore = true)
//    @Mapping(target = Reservation.Properties.PUBLICATION_RESERVATIONS, ignore = true)
//    ReservationDto convertToDto(Reservation entity);

    @AfterMapping
    default void fillEntity(final UserDTO dto, @MappingTarget final Usuario entity) {
        // set parent
//        if (entity.getPublicationReservations() != null) {
//            for (final PublicationReservation publicationReservation : entity.getPublicationReservations()) {
//                publicationReservation.setReservation(entity);
//            }
//        }
    }

    @AfterMapping
    default void fillDto(final Usuario entity, @MappingTarget final UserDTO dto) {
//        // set lazy properties only if loaded
//        if (isLoaded(entity, Reservation.Properties.FINAL_CLIENT)) {
//            final FinalClientMapper mapper = getBean(FinalClientMapper.class);
//            dto.setFinalClient(mapper.convertToDto(entity.getFinalClient()));
//        }
//        if (isLoaded(entity, Reservation.Properties.PUBLICATION_RESERVATIONS)) {
//            final PublicationReservationMapper mapper = getBean(PublicationReservationMapper.class);
//            dto.setPublicationReservations(mapper.convertListToDto(entity.getPublicationReservations()));
//        }
    }
}
