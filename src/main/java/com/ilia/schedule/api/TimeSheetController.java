package com.ilia.schedule.api;

import com.ilia.schedule.api.mappers.ApiModelMapper;
import com.ilia.schedule.api.resources.ApiResponseModel;
import com.ilia.schedule.api.resources.TimeSheetCreateModel;
import com.ilia.schedule.services.exceptions.CheckedTimeExistException;
import com.ilia.schedule.services.TimeSheetService;
import com.ilia.schedule.services.exceptions.CheckedTimeInvalidException;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Bate Ponto")
@RequestMapping("/api/v1/batidas")
public class TimeSheetController {

    final TimeSheetService timeSheetService;

    public TimeSheetController(TimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Bater ponto",
            notes = "Registrar um horário da jornada diária de trabalho")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Horário registrado com sucesso"),
            @ApiResponse(code = 400, message = "Solicitação inválida", response = ApiResponseModel.class),
            @ApiResponse(code = 403, message = "Solicitação denegada", response = ApiResponseModel.class),
            @ApiResponse(code = 409, message = "Inconsistência", response = ApiResponseModel.class),
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponseModel> insert(
            @RequestBody TimeSheetCreateModel timesheetCreateModel) {

        try {
            var timeSheetDto = ApiModelMapper.INSTANCE
                    .timeSheetCreateModelToTimeSheetDto(timesheetCreateModel);

            timeSheetService.saveCheckedDateTime(timeSheetDto);

            return ResponseEntity.status(201).build();

        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponseModel(ex.getLocalizedMessage()));
        } catch (CheckedTimeInvalidException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseModel(ex.getLocalizedMessage()));
        } catch (CheckedTimeExistException ex) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ApiResponseModel(ex.getLocalizedMessage()));
        }
    }
}
