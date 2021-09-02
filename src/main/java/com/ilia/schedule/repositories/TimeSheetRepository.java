package com.ilia.schedule.repositories;

import com.ilia.schedule.repositories.models.TimeSheet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TimeSheetRepository extends CrudRepository<TimeSheet, Long> {

    @Query("select count(ts) from TimeSheet ts where ts.checkedDateTime >= :startDatetime and ts.checkedDateTime <= :endDatetime")
    Integer countTimeSheetsBetweenDates(
            @Param("startDatetime") LocalDateTime startDatetime,
            @Param("endDatetime") LocalDateTime endDatetime
    );

    TimeSheet findFirstByCheckedDateTimeBetweenOrderByCreatedDateTimeDesc(LocalDateTime startDatetime, LocalDateTime endDatetime);
}
