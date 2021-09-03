package com.ilia.schedule.services;

import com.ilia.schedule.services.dto.TimeSheetDto;

public interface TimeSheetService {
    TimeSheetDto saveCheckedDateTime(TimeSheetDto timeSheetDto);
}
