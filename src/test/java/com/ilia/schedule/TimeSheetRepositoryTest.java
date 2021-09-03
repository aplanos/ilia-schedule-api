package com.ilia.schedule;

import com.ilia.schedule.repositories.TimeSheetRepository;
import com.ilia.schedule.repositories.enums.TimeSheetEntryType;
import com.ilia.schedule.repositories.models.TimeSheet;
import com.ilia.schedule.utils.DateHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update"
})
public class TimeSheetRepositoryTest {

    @Autowired private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;
    @Autowired private TimeSheetRepository timeSheetRepository;

    @AfterEach
    void teardown() {
        timeSheetRepository.deleteAll();
    }

    @Test
    @DisplayName("Injected components are not null")
    void injectedComponentsAreNotNull(){
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(timeSheetRepository).isNotNull();
    }

    @Test
    @DisplayName("When save time sheet then finds by checkedTime")
    void when_save_time_sheet_then_finds_by_checkedTime() {

        var checkedTime = LocalDateTime.parse("2018-08-22T00:00");

        timeSheetRepository.save(new TimeSheet(checkedTime));
        var timeSheet = timeSheetRepository.findFirstByCheckedDateTime(checkedTime);

        assertThat(timeSheet.isPresent()).isTrue();
        assertThat(timeSheet.get().getCheckedDateTime()).isEqualTo(checkedTime);
    }

    @Test
    @Sql("test_lunch_rule.sql")
    @DisplayName("When search for last timesheet get a paused one")
    void when_search_for_last_timesheet_get_a_paused_one() {

        var checkedTime = LocalDateTime.parse("2018-08-22T00:00");

        var pausedTimeSheet = timeSheetRepository.findFirstByCheckedDateTimeBetweenOrderByCreatedDateTimeDesc(
                DateHelper.firstHourOfDay(checkedTime), DateHelper.lastHourOfDay(checkedTime)
        );

        assertThat(pausedTimeSheet.isPresent()).isTrue();
        assertThat(pausedTimeSheet.get().getType()).isEqualTo(TimeSheetEntryType.PAUSE);
    }

    @Test
    @DisplayName("When ask for count it return 4")
    void when_ask_for_count_it_return_four() {

        var now = LocalDateTime.now();

        timeSheetRepository.save(new TimeSheet(now));
        timeSheetRepository.save(new TimeSheet(now));
        timeSheetRepository.save(new TimeSheet(now));
        timeSheetRepository.save(new TimeSheet(now));

        assertThat(timeSheetRepository.countTimeSheetsBetweenDates(
                DateHelper.firstHourOfDay(now), DateHelper.lastHourOfDay(now)
        )).isEqualTo(4);
    }
}
