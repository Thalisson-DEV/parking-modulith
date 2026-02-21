package com.example.carparking.allocation.doc;

import com.example.carparking.allocation.application.dto.SlotCreateRequest;
import com.example.carparking.allocation.application.dto.SlotCreatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

@Tag(name = "Allocation", description = "Operations pertaining to slot management")
public interface SlotCreationControllerDocs {

    @Operation(summary = "Create a new parking slot")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Slot created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = SlotCreatedResponse.class
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "Response Created Slot",
                                            value = """
                                                            {
                                                              "id": 1,
                                                              "slotCode": "A1"
                                                            }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ProblemDetail.class
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "Response Bad Request Exception",
                                            value = """
                                                            {
                                                              "detail": "Bad Request",
                                                              "instance": "/api/v1/slot",
                                                              "status": 400,
                                                              "title": "Bad Request",
                                                              "timestamp": "2026-02-21T15:17:00.400065482Z"
                                                            }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Validation Error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ProblemDetail.class
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "Response Validation Request Exception",
                                            value = """
                                                            {
                                                               "detail": "slotCode: Slot code is required",
                                                               "instance": "/api/v1/slot",
                                                               "status": 400,
                                                               "title": "Validation Error",
                                                               "timestamp": "2026-02-21T16:46:23.205590012Z"
                                                            }
                                                    """
                                    )
                            }
                    )
            )
    })
    ResponseEntity<SlotCreatedResponse> createSlot(SlotCreateRequest request);
}
