package com.ilia.schedule.repositories.models;

import com.ilia.schedule.repositories.enums.TimeSheetEntryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TimeSheet {
    @Id
    @GeneratedValue
    private Long id;

    private TimeSheetEntryType type;

    private LocalDateTime checkedDateTime;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    private Integer createdBy;
    private Integer updatedBy;

    public TimeSheet(LocalDateTime checkedDateTime) {

        var now = LocalDateTime.now();

        this.checkedDateTime = checkedDateTime;
        this.createdDateTime = now;
        this.updatedDateTime = now;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeSheetEntryType getType() {
        return type;
    }

    public void setType(TimeSheetEntryType type) {
        this.type = type;
    }

    public LocalDateTime getCheckedDateTime() {
        return checkedDateTime;
    }

    public void setCheckedDateTime(LocalDateTime checkedDateTime) {
        this.checkedDateTime = checkedDateTime;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }
}
