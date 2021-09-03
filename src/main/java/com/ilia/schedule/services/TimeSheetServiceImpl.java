package com.ilia.schedule.services;

import com.ilia.schedule.repositories.TimeSheetRepository;
import com.ilia.schedule.repositories.enums.TimeSheetEntryType;
import com.ilia.schedule.services.dto.TimeSheetDto;
import com.ilia.schedule.services.exceptions.CheckedTimeExistException;
import com.ilia.schedule.services.exceptions.CheckedTimeInvalidException;
import com.ilia.schedule.services.mappers.TimeSheetMapper;
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
    public TimeSheetDto saveCheckedDateTime(TimeSheetDto timeSheetDto) {

        var timesheet = TimeSheetMapper.INSTANCE.timeSheetDtoToTimeSheet(timeSheetDto);

        var checkedDateTime = timesheet.getCheckedDateTime();

        if (checkedDateTime == null) {
            throw new CheckedTimeInvalidException("Campo obrigatório não informado [dataHora].");
        }

        if (List.of(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY).contains(checkedDateTime.getDayOfWeek())) {
            throw new IllegalArgumentException("Sábado e domingo não são permitidos como dia de trabalho.");
        }

        var alreadyProcessed = timeSheetRepository
                .findFirstByCheckedDateTime(checkedDateTime).isPresent();

        if (alreadyProcessed) {
            throw new CheckedTimeExistException();
        }

        var dayTimeSheetCount = countTimeSheetEntriesInDay(checkedDateTime);

        if (dayTimeSheetCount == 4) {
            throw new IllegalArgumentException("Apenas 4 horários podem ser registrados por dia.");
        }

        var lastTimeSheet = timeSheetRepository.findFirstByCheckedDateTimeBetweenOrderByCreatedDateTimeDesc(
                DateHelper.firstHourOfDay(checkedDateTime), DateHelper.lastHourOfDay(checkedDateTime)
        );

        lastTimeSheet.ifPresent(timeSheet -> {

            long minutesInterval = Duration
                    .between(timeSheet.getCheckedDateTime(), checkedDateTime)
                    .toMinutes();

            if (minutesInterval < 0) {
                throw new IllegalArgumentException("Não pode aceitar uma data e hora anterior à última inserida.");
            }

            if (TimeSheetEntryType.PAUSE.equals(timeSheet.getType()) && minutesInterval < 60) {
                throw new IllegalArgumentException("Deve haver no mínimo 1 hora de almoço.");
            }

        });

        timesheet.setType(TimeSheetEntryType.fromValue(dayTimeSheetCount));
        timeSheetRepository.save(timesheet);

        return TimeSheetMapper.INSTANCE.timeSheetToTimeSheetDto(timesheet);
    }

    private Integer countTimeSheetEntriesInDay(LocalDateTime checkedDateTime) {
        return timeSheetRepository.countTimeSheetsBetweenDates(
                DateHelper.firstHourOfDay(checkedDateTime), DateHelper.lastHourOfDay(checkedDateTime)
        );
    }

}
