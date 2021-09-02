package com.ilia.schedule.repositories;

import com.ilia.schedule.repositories.models.TimeSheet;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TimeSheetRepository extends CrudRepository<TimeSheet, Long> {

    List<TimeSheet> findTimeSheetsByCheckedDateTimeAfterAndCheckedDateTimeBefore(
            Date startDatetime,
            Date endDatetime
    );
}
