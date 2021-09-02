package com.ilia.schedule.services;

import com.ilia.schedule.repositories.TimeSheetRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TimeSheetServiceImpl implements TimeSheetService {

    TimeSheetRepository timeSheetRepository;

    public TimeSheetServiceImpl(TimeSheetRepository timeSheetRepository) {
        this.timeSheetRepository = timeSheetRepository;
    }

    @Override
    public void saveCheckedDateTime(Date checkedDateTime) {
    }
}
