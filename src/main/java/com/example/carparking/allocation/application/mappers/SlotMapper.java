package com.example.carparking.allocation.application.mappers;

import com.example.carparking.allocation.application.dto.SlotCreatedResponse;
import com.example.carparking.allocation.domain.Slot;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SlotMapper {

    SlotMapper INSTANCE = Mappers.getMapper(SlotMapper.class);

    SlotCreatedResponse toDto(Slot slotCode);
}
