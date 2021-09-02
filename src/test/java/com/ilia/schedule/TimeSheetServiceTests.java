package com.ilia.schedule;

import com.ilia.schedule.api.models.TimeSheetCreateModel;
import com.ilia.schedule.repositories.TimeSheetRepository;
import com.ilia.schedule.repositories.enums.TimeSheetEntryType;
import com.ilia.schedule.repositories.models.TimeSheet;
import com.ilia.schedule.services.TimeSheetServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class TimeSheetServiceTests {

    @Mock
    private TimeSheetRepository timeSheetRepository;

    @InjectMocks
    private TimeSheetServiceImpl timeSheetService;

    private final String checkedTime = "2021-09-02T08:00:00";

    private final TimeSheetCreateModel startCheckPoint = new TimeSheetCreateModel(LocalDateTime.parse("2021-09-02T08:00:00"));
    private final TimeSheetCreateModel pauseCheckPoint = new TimeSheetCreateModel(LocalDateTime.parse("2021-09-02T12:01:00"));
    private final TimeSheetCreateModel resumeCheckPoint = new TimeSheetCreateModel(LocalDateTime.parse("2021-09-02T13:00:00"));
    private final TimeSheetCreateModel endCheckPoint = new TimeSheetCreateModel(LocalDateTime.parse("2021-09-02T17:00:00"));

    private final TimeSheetCreateModel saturdayCheckpoint = new TimeSheetCreateModel(LocalDateTime.parse("2021-09-04T08:00:00"));
    private final TimeSheetCreateModel sundayCheckpoint = new TimeSheetCreateModel(LocalDateTime.parse("2021-09-05T08:00:00"));

    @Test
    public void when_save_timesheet_checked_date_cant_be_null() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                timeSheetService.saveCheckedDateTime(new TimeSheetCreateModel())
        );

        assertNotNull(exception);
    }

    @Test
    public void save_only_four_timesheet_by_day() {

        given(timeSheetRepository.countTimeSheetsBetweenDates(any(LocalDateTime.class), any(LocalDateTime.class)))
                .willAnswer(invocation -> 4);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                timeSheetService.saveCheckedDateTime(startCheckPoint)
        );

        assertNotNull(exception);
    }

    @Test
    public void should_have_one_hour_of_lunch() {

        given(timeSheetRepository.findFirstByCheckedDateTimeBetweenOrderByCreatedDateTimeDesc(any(LocalDateTime.class), any(LocalDateTime.class)))
                .willAnswer(invocation -> {
                    var timesheet = new TimeSheet();
                    timesheet.setCheckedDateTime(pauseCheckPoint.getCheckedDatetime());
                    timesheet.setType(TimeSheetEntryType.PAUSE);

                    return timesheet;
                });

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                timeSheetService.saveCheckedDateTime(resumeCheckPoint)
        );

        assertNotNull(exception);
    }

    @Test
    public void cannot_check_time_in_weekends() {

        Exception saturdayException = assertThrows(IllegalArgumentException.class, () ->
                timeSheetService.saveCheckedDateTime(saturdayCheckpoint)
        );

        Exception sundayException = assertThrows(IllegalArgumentException.class, () ->
                timeSheetService.saveCheckedDateTime(sundayCheckpoint)
        );

        assertNotNull(saturdayException);
        assertNotNull(sundayException);
    }

    @Test
    public void when_save_timesheet_it_should_return_timesheet() {

        given(timeSheetRepository.save(any(TimeSheet.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        TimeSheet created = timeSheetService.saveCheckedDateTime(startCheckPoint);

        assertThat(created.getCheckedDateTime()).isEqualTo(startCheckPoint.getCheckedDatetime());
    }

    @Test
    public void cannot_check_time_minor_than_the_previous_one_in_day() {

        given(timeSheetRepository.findFirstByCheckedDateTimeBetweenOrderByCreatedDateTimeDesc(
                any(LocalDateTime.class), any(LocalDateTime.class)))
                .willAnswer(invocation -> {
                    var timesheet = new TimeSheet();
                    timesheet.setCheckedDateTime(resumeCheckPoint.getCheckedDatetime());
                    timesheet.setType(TimeSheetEntryType.RESUME);

                    return timesheet;
                });

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                timeSheetService.saveCheckedDateTime(startCheckPoint)
        );

        assertNotNull(exception);
    }
}
