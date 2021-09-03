package com.ilia.schedule;

import com.ilia.schedule.api.models.TimeSheetCreateModel;
import com.ilia.schedule.repositories.TimeSheetRepository;
import com.ilia.schedule.repositories.enums.TimeSheetEntryType;
import com.ilia.schedule.repositories.models.TimeSheet;
import com.ilia.schedule.services.TimeSheetServiceImpl;
import com.ilia.schedule.services.dto.TimeSheetDto;
import com.ilia.schedule.services.exceptions.CheckedTimeExistException;
import com.ilia.schedule.services.exceptions.CheckedTimeInvalidException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

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

    private final TimeSheetDto startCheckPoint = new TimeSheetDto(LocalDateTime.parse("2021-09-02T08:00:00"));
    private final TimeSheetDto pauseCheckPoint = new TimeSheetDto(LocalDateTime.parse("2021-09-02T12:01:00"));
    private final TimeSheetDto resumeCheckPoint = new TimeSheetDto(LocalDateTime.parse("2021-09-02T13:00:00"));
    private final TimeSheetDto endCheckPoint = new TimeSheetDto(LocalDateTime.parse("2021-09-02T17:00:00"));

    private final TimeSheetDto saturdayCheckpoint = new TimeSheetDto(LocalDateTime.parse("2021-09-04T08:00:00"));
    private final TimeSheetDto sundayCheckpoint = new TimeSheetDto(LocalDateTime.parse("2021-09-05T08:00:00"));

    @Test
    @DisplayName("When save timesheet checked date cannot be null")
    public void when_save_timesheet_checked_date_cant_be_null() {
        Exception exception = assertThrows(CheckedTimeInvalidException.class, () ->
                timeSheetService.saveCheckedDateTime(new TimeSheetDto())
        );

        assertNotNull(exception);
    }

    @Test
    @DisplayName("Save only four timesheet by day")
    public void save_only_four_timesheet_by_day() {

        given(timeSheetRepository.countTimeSheetsBetweenDates(any(LocalDateTime.class), any(LocalDateTime.class)))
                .willAnswer(invocation -> 4);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                timeSheetService.saveCheckedDateTime(startCheckPoint)
        );

        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should have one hour of lunch")
    public void should_have_one_hour_of_lunch() {

        given(timeSheetRepository.findFirstByCheckedDateTimeBetweenOrderByCreatedDateTimeDesc(any(LocalDateTime.class), any(LocalDateTime.class)))
                .willAnswer(invocation -> {
                    var timesheet = new TimeSheet();
                    timesheet.setCheckedDateTime(pauseCheckPoint.getCheckedDateTime());
                    timesheet.setType(TimeSheetEntryType.PAUSE);

                    return Optional.of(timesheet);
                });

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                timeSheetService.saveCheckedDateTime(resumeCheckPoint)
        );

        assertNotNull(exception);
    }

    @Test
    @DisplayName("Cannot check time in weekends")
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
    @DisplayName("Cannot repeat same checked time")
    public void cannot_repeat_same_checked_time() {

        given(timeSheetRepository.findFirstByCheckedDateTime(any(LocalDateTime.class)))
                .willAnswer(invocation -> Optional.of(new TimeSheet()));

        Exception repeatedException = assertThrows(CheckedTimeExistException.class, () ->
                timeSheetService.saveCheckedDateTime(startCheckPoint)
        );

        assertNotNull(repeatedException);
    }

    @Test
    @DisplayName("When save timesheet it should return timesheet")
    public void when_save_timesheet_it_should_return_timesheet() {

        given(timeSheetRepository.save(any(TimeSheet.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        TimeSheetDto created = timeSheetService.saveCheckedDateTime(startCheckPoint);

        assertThat(created.getCheckedDateTime()).isEqualTo(startCheckPoint.getCheckedDateTime());
    }

    @Test
    @DisplayName("Cannot check time minor than the previous one in day")
    public void cannot_check_time_minor_than_the_previous_one_in_day() {

        given(timeSheetRepository.findFirstByCheckedDateTimeBetweenOrderByCreatedDateTimeDesc(
                any(LocalDateTime.class), any(LocalDateTime.class)))
                .willAnswer(invocation -> {
                    var timesheet = new TimeSheet();
                    timesheet.setCheckedDateTime(resumeCheckPoint.getCheckedDateTime());
                    timesheet.setType(TimeSheetEntryType.RESUME);

                    return Optional.of(timesheet);
                });

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                timeSheetService.saveCheckedDateTime(startCheckPoint)
        );

        assertNotNull(exception);
    }

    @AfterEach
    void teardown() {
        timeSheetRepository.deleteAll();
    }
}
