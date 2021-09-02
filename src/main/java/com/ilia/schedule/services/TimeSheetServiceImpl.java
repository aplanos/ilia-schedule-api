package com.ilia.schedule.services;

import com.ilia.schedule.api.models.TimeSheetCreateModel;
import com.ilia.schedule.repositories.TimeSheetRepository;
import com.ilia.schedule.repositories.enums.TimeSheetEntryType;
import com.ilia.schedule.repositories.models.TimeSheet;
import com.ilia.schedule.utils.DateHelper;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TimeSheetServiceImpl implements TimeSheetService {

    TimeSheetRepository timeSheetRepository;

    public TimeSheetServiceImpl(TimeSheetRepository timeSheetRepository) {
        this.timeSheetRepository = timeSheetRepository;
    }

    @Override
    public TimeSheet saveCheckedDateTime(TimeSheetCreateModel timeSheetCreateModel) {

        var checkedDateTime = timeSheetCreateModel.getCheckedDatetime();

        if (checkedDateTime == null) {
            throw new IllegalArgumentException("[CheckedDateTime] in [TimeSheetCreateModel] cannot be null.");
        }

        if (List.of(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY).contains(checkedDateTime.getDayOfWeek())) {
            throw new IllegalArgumentException("[TimeSheetCreateModel] cannot check weekend days.");
        }

        var dayTimeSheetCount = countTimeSheetEntriesInDay(checkedDateTime);

        if (dayTimeSheetCount == 4) {
            throw new IllegalArgumentException("[TimeSheetCreateModel] cannot accept more than 4 entries by day.");
        }

        var lastCheckedTime = timeSheetRepository.findFirstByCheckedDateTimeBetweenOrderByCreatedDateTimeDesc(
                DateHelper.firstHourOfDay(checkedDateTime), DateHelper.lastHourOfDay(checkedDateTime)
        );

        if (lastCheckedTime != null && lastCheckedTime.getCheckedDateTime() != null) {

            long minutesInterval = Duration
                    .between(lastCheckedTime.getCheckedDateTime(), checkedDateTime)
                    .toMinutes();

            if (minutesInterval < 0) {
                throw new IllegalArgumentException("[TimeSheetCreateModel] cannot accept time before last one inserted.");
            }

            if (TimeSheetEntryType.PAUSE.equals(lastCheckedTime.getType()) && minutesInterval < 60) {
                throw new IllegalArgumentException("[TimeSheetCreateModel] After PAUSE you have one hour of lunch.");
            }
        }

        var instance = new TimeSheet(checkedDateTime);
        instance.setType(TimeSheetEntryType.fromValue(dayTimeSheetCount));

        timeSheetRepository.save(instance);

        return instance;
    }

    private Integer countTimeSheetEntriesInDay(LocalDateTime checkedDateTime) {
        return timeSheetRepository.countTimeSheetsBetweenDates(
                DateHelper.firstHourOfDay(checkedDateTime), DateHelper.lastHourOfDay(checkedDateTime)
        );
    }

}
