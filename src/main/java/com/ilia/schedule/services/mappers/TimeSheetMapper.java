package com.ilia.schedule.services.mappers;

import com.ilia.schedule.repositories.models.TimeSheet;
import com.ilia.schedule.services.dto.TimeSheetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TimeSheetMapper {

    TimeSheetMapper INSTANCE = Mappers.getMapper(TimeSheetMapper.class);

    @Mapping(target = "createdDateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedDateTime", expression = "java(java.time.LocalDateTime.now())")
    TimeSheet timeSheetDtoToTimeSheet(TimeSheetDto timeSheetCreateModel);
    TimeSheetDto timeSheetToTimeSheetDto(TimeSheet timeSheet);
}