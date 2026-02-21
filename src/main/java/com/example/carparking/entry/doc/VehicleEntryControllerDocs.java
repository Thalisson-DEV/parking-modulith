package com.example.carparking.entry.doc;

import com.example.carparking.entry.application.dto.ParkingEntryRequest;
import com.example.carparking.entry.application.dto.ParkingEntryResponse;
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

@Tag(name = "Entry", description = "Operations pertaining to vehicle entry")
public interface VehicleEntryControllerDocs {

    @Operation(summary = "Request a Vehicle Entry")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Vehicle entry created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ParkingEntryResponse.class
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "Response Created Vehicle Entry",
                                            value = """
                                                            {
                                                              "id": 1,
                                                              "vehicleNumber": "string",
                                                              "entryTime": "2026-02-21T16:29:32.329Z",
                                                              "exitTime": null
                                                            }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Generic bad request",
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
                                                              "instance": "/api/v1/parking/entry",
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
                                                               "detail": "vehicleNumber: Vehicle number is required",
                                                               "instance": "/api/v1/parking/entry",
                                                               "status": 400,
                                                               "title": "Validation Error",
                                                               "timestamp": "2026-02-21T16:46:23.205590012Z"
                                                            }
                                                    """
                                    )
                            }
                    )
            )
    }
    )
    ResponseEntity<ParkingEntryResponse> vehicleEntry(ParkingEntryRequest request);
}
