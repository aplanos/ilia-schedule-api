package com.ilia.schedule.services.dto;

import com.ilia.schedule.repositories.enums.TimeSheetEntryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class TimeSheetDto {

    Long id;
    TimeSheetEntryType type;

    LocalDateTime checkedDateTime;
    LocalDateTime createdDateTime;
    LocalDateTime updatedDateTime;

    Integer createdBy;
    Integer updatedBy;

    public TimeSheetDto(LocalDateTime checkedDateTime) {
        this.checkedDateTime = checkedDateTime;
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
