package co.com.sofka.products.api.buy;

import co.com.sofka.products.model.buy.Buy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class RouterRestBuy {
@Bean
@RouterOperations(
        {
                @RouterOperation(
                        path = "/api/buy",
                        produces = {
                                MediaType.APPLICATION_JSON_VALUE
                        },
                        method = RequestMethod.POST,
                        beanClass = HandlerBuy.class,
                        beanMethod = "listenPostAddBuyUseCase",
                        operation = @Operation(
                                operationId = "listenPostAddBuyUseCase",
                                responses = {
                                        @ApiResponse(
                                                responseCode = "200",
                                                description = "successful operation",
                                                content = @Content(schema = @Schema(
                                                        implementation = String.class
                                                ))
                                        ),
                                },
                                requestBody = @RequestBody(
                                        content = @Content(schema = @Schema(
                                                implementation = Buy.class
                                        ))
                                )
                        )
                ),
                @RouterOperation(
                        path = "/api/buys",
                        produces = {
                                MediaType.APPLICATION_JSON_VALUE
                        },
                        method = RequestMethod.GET,
                        beanClass = HandlerBuy.class,
                        beanMethod = "listenFindAllBuysUseCase",
                        operation = @Operation(
                                operationId = "listenFindAllBuysUseCase",
                                responses = {
                                        @ApiResponse(
                                                responseCode = "200",
                                                description = "successful operation",
                                                content = @Content(schema = @Schema(
                                                        implementation = Buy.class
                                                ))
                                        )
                                }
                        )
                )
        }
)
public RouterFunction<ServerResponse> routerFunctionBuy(HandlerBuy handler) {
    return route(POST("/api/buy"), handler::listenPostAddBuyUseCase)
            .andRoute(GET("/api/buys"), handler::listenFindAllBuysUseCase);
    }
}
