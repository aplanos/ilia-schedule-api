package com.ilia.schedule.services;

import com.ilia.schedule.api.models.TimeSheetCreateModel;
import com.ilia.schedule.repositories.models.TimeSheet;
import com.ilia.schedule.services.dto.TimeSheetDto;

public interface TimeSheetService {
    TimeSheetDto saveCheckedDateTime(TimeSheetDto timeSheetDto);
}
