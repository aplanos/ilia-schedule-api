package com.ilia.schedule.services;

import com.ilia.schedule.repositories.models.TimeSheet;
import com.ilia.schedule.services.dto.TimeSheetDto;
import com.ilia.schedule.services.exceptions.CheckedTimeExistException;
import com.ilia.schedule.services.exceptions.CheckedTimeInvalidException;

public interface TimeSheetService {
    /**
     * Returns a TimeSheetDto object when is successfully created in repository.
     * The checkedDateTime field cannot be null.
     *
     * @param timeSheetDto a TimeSheetDto instance object
     * @return a new TimeSheetDto instance object
     * @throws CheckedTimeInvalidException when required checkedDateTime is null
     * @throws IllegalArgumentException when some rule is broken, example: more than four entries by day
     * @throws CheckedTimeExistException when the provide checkedDateTime already is store in database
     * @see TimeSheetDto
     * @see TimeSheet
     */
    TimeSheetDto saveCheckedDateTime(TimeSheetDto timeSheetDto);
}
