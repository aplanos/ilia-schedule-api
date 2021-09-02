package com.ilia.schedule.services;

import com.ilia.schedule.api.models.TimeSheetCreateModel;
import com.ilia.schedule.repositories.models.TimeSheet;

public interface TimeSheetService {
    TimeSheet saveCheckedDateTime(TimeSheetCreateModel timeSheetCreateModel);
}
