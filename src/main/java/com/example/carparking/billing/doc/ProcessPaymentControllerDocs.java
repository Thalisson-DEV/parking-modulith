package com.example.carparking.billing.doc;

import com.example.carparking.billing.application.dto.PaymentRequest;
import com.example.carparking.billing.application.dto.PaymentResponse;
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

@Tag(name = "Billing", description = "Operations pertaining to billing and payment")
public interface ProcessPaymentControllerDocs {

    @Operation(summary = "Process a payment for a parking entry")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment processed successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = PaymentResponse.class
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "Response Processed Payment",
                                            value = """
                                                            {
                                                              "billingId": "uuid-here",
                                                              "parkingEntryId": 1,
                                                              "amount": 10.50,
                                                              "paymentTime": "2026-02-21T16:29:32.329Z",
                                                              "paid": true
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
                                                              "instance": "/api/v1/billing/payment",
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
                                                               "detail": "billingId: Billing ID is required",
                                                               "instance": "/api/v1/billing/payment",
                                                               "status": 400,
                                                               "title": "Validation Error",
                                                               "timestamp": "2026-02-21T16:46:23.205590012Z"
                                                            }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Billing record not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Response Not Found Exception",
                                            value = """
                                                            {
                                                              "detail": "Billing record not found with the specified ID",
                                                              "instance": "/api/v1/billing/payment",
                                                              "status": 404,
                                                              "title": "Entity not found",
                                                              "timestamp": "2026-02-21T15:17:00.400065482Z"
                                                            }
                                                    """
                                    )
                            }
                    ))
    })
    ResponseEntity<PaymentResponse> processPayment(PaymentRequest request);
}
