package com.example.carparking.entry.doc;


import com.example.carparking.entry.application.dto.ParkingEntryResponse;
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

@Tag(name = "Entry", description = "Operations pertaining to vehicle entry")
public interface ListVehicleEntryControllerDocs {

    @Operation(summary = "Paginated List all vehicle entries")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Paged Listing completed",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = Page.class
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "Response All Entries Paginated",
                                            value = """
                                                            {
                                                              "content": [
                                                                {
                                                                  "id": 1,
                                                                  "vehicleNumber": "string",
                                                                  "entryTime": "2026-02-21T13:03:26.419119",
                                                                  "exitTime": null
                                                                },
                                                                {
                                                                  "id": 2,
                                                                  "vehicleNumber": "string2",
                                                                  "entryTime": "2026-02-02T13:03:26.419119",
                                                                  "exitTime": "2026-02-21T13:03:26.419119"
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
                                                              "instance": "/api/v1/parking/entry",
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
    ResponseEntity<Page<ParkingEntryResponse>> getAllEntries(Pageable pageable);

    @Operation(summary = "Paginated List all active vehicle entries")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Paged Listing actives completed",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = Page.class
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "Response All Active Entries Paginated",
                                            value = """
                                                            {
                                                              "content": [
                                                                {
                                                                  "id": 1,
                                                                  "vehicleNumber": "string",
                                                                  "entryTime": "2026-02-21T13:03:26.419119",
                                                                  "exitTime": null
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
                                                              "instance": "/api/v1/parking/entry/actives",
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
    ResponseEntity<Page<ParkingEntryResponse>> getActives(Pageable pageable);

    @Operation(summary = "Find vehicle entry by vehicle number")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Vehicle found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ParkingEntryResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Vehicle not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Response Not Found Exception",
                                            value = """
                                                            {
                                                              "detail": "Vehicle not found with the specified number",
                                                              "instance": "/api/v1/parking/entry/vehicle-number/{vehicleNumber}",
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
                                                              "instance": "/api/v1/parking/entry/vehicle-number/{vehicleNumber}",
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
    ResponseEntity<ParkingEntryResponse> findByVehicleNumber(String vehicleNumber);
}
