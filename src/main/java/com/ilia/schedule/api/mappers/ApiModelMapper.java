package com.ilia.schedule.api.mappers;

import com.ilia.schedule.api.models.TimeSheetCreateModel;
import com.ilia.schedule.api.models.ApiResponseModel;
import com.ilia.schedule.services.dto.TimeSheetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApiModelMapper {

    ApiModelMapper INSTANCE = Mappers.getMapper(ApiModelMapper.class);

    @Mapping(source = "checkedDateTime", target = "message")
    ApiResponseModel timeSheetDtoToApiResponseModel(TimeSheetDto timeSheet);
    TimeSheetDto timeSheetCreateModelToTimeSheetDto(TimeSheetCreateModel timeSheetCreateModel);
}