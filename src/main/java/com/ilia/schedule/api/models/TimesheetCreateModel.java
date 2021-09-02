package com.ilia.schedule.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "O momento da batida")
public class TimesheetCreateModel {

    @JsonProperty("dataHora")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="America/Sao_Paulo")
    @Schema(example = "2018-08-22T08:00:00", description = "Data e hora da batida")
    Date checkedDatetime;

}
