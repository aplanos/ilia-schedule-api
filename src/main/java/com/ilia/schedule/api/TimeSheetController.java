package com.ilia.schedule.api;

import com.ilia.schedule.api.models.ApiResponseModel;
import com.ilia.schedule.api.models.TimeSheetCreateModel;
import com.ilia.schedule.services.TimeSheetService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Bate Ponto")
@RequestMapping("/api/batidas")
public class TimeSheetController {

    final TimeSheetService timeSheetService;

    public TimeSheetController(TimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

    @PostMapping(value = {"/v1"}, produces = MediaType.APPLICATION_JSON_VALUE)
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

        timeSheetService.saveCheckedDateTime(timesheetCreateModel);

        return ResponseEntity
                .status(201)
                .body(new ApiResponseModel(timesheetCreateModel.getCheckedDatetime().toString()));
    }
}
