package com.example.carparking.allocation.doc;

import com.example.carparking.allocation.application.dto.SlotCreatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

@Tag(name = "Allocation", description = "Operations pertaining to slot management")
public interface ListSlotControllerDocs {

    @Operation(summary = "Paginated list all parking slots")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Paged listing completed",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = Page.class
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "Response All Slots Paginated",
                                            value = """
                                                            {
                                                              "content": [
                                                                {
                                                                  "id": 1,
                                                                  "slotCode": "A1"
                                                                },
                                                                {
                                                                  "id": 2,
                                                                  "slotCode": "B2"
                                                                }
                                                              ],
                                                              "page": {
                                                                "size": 1,
                                                                "number": 1,
                                                                "totalElements": 2,
                                                                "totalPages": 1
                                                              }
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
            )
    })
    ResponseEntity<Page<SlotCreatedResponse>> getAllSlots(Pageable pageable);

    @Operation(summary = "Paginated list all available parking slots")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Paged listing of available slots completed",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = Page.class
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "Response All Available Slots Paginated",
                                            value = """
                                                            {
                                                              "content": [
                                                                {
                                                                  "id": 1,
                                                                  "slotCode": "A1"
                                                                }
                                                              ],
                                                              "page": {
                                                                "size": 1,
                                                                "number": 1,
                                                                "totalElements": 1,
                                                                "totalPages": 1
                                                              }
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
                                                              "instance": "/api/v1/slot/available",
                                                              "status": 400,
                                                              "title": "Bad Request",
                                                              "timestamp": "2026-02-21T15:17:00.400065482Z"
                                                            }
                                                    """
                                    )
                            }
                    )
            )
    })
    ResponseEntity<Page<SlotCreatedResponse>> getAvailableSlots(Pageable pageable);

    @Operation(summary = "Find parking slot by slot code")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Slot found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SlotCreatedResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Slot not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Response Not Found Exception",
                                            value = """
                                                            {
                                                              "detail": "Slot not found with the specified code",
                                                              "instance": "/api/v1/slot/slot-code/{slotCode}",
                                                              "status": 404,
                                                              "title": "Entity not found",
                                                              "timestamp": "2026-02-21T15:17:00.400065482Z"
                                                            }
                                                    """
                                    )
                            }
                    )),
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
                                                              "instance": "/api/v1/slot/slot-code/{slotCode}",
                                                              "status": 400,
                                                              "title": "Bad Request",
                                                              "timestamp": "2026-02-21T15:17:00.400065482Z"
                                                            }
                                                    """
                                    )
                            }
                    )
            )
    })
    ResponseEntity<SlotCreatedResponse> findBySlotCode(String slotCode);
}
