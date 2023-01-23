package co.com.sofka.products.api.product;

import co.com.sofka.products.model.product.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
public class RouterRestProduct {
@Bean
@RouterOperations(
        {
                @RouterOperation(
                        path = "/api/product",
                        produces = {
                                MediaType.APPLICATION_JSON_VALUE
                        },
                        method = RequestMethod.POST,
                        beanClass = HandlerProduct.class,
                        beanMethod = "listenPostAddProductUseCase",
                        operation = @Operation(
                                operationId = "listenPostAddProductUseCase",
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
                                                implementation = Product.class
                                        ))
                                )
                        )
                ),
                @RouterOperation(
                        path = "/api/products",
                        produces = {
                                MediaType.APPLICATION_JSON_VALUE
                        },
                        method = RequestMethod.GET,
                        beanClass = HandlerProduct.class,
                        beanMethod = "listenFindAllProductsUseCase",
                        operation = @Operation(
                                operationId = "listenFindAllProductsUseCase",
                                responses = {
                                        @ApiResponse(
                                                responseCode = "200",
                                                description = "successful operation",
                                                content = @Content(schema = @Schema(
                                                        implementation = Product.class
                                                ))
                                        ),
                                        @ApiResponse(responseCode = "404", description = "No product could be found in the search by pagination.")

                                },
                                parameters = {
                                        @Parameter(in = ParameterIn.QUERY, name = "page"),
                                        @Parameter(in = ParameterIn.QUERY, name = "size")
                                }
                        )
                ),
                @RouterOperation(
                        path = "/api/product/{id}",
                        produces = {
                                MediaType.APPLICATION_JSON_VALUE
                        },
                        method = RequestMethod.GET,
                        beanClass = HandlerProduct.class,
                        beanMethod = "listenFindByIdProductsUseCase",
                        operation = @Operation(
                                operationId = "listenFindByIdProductsUseCase",
                                responses = {
                                        @ApiResponse(
                                                responseCode = "200",
                                                description = "successful operation",
                                                content = @Content(schema = @Schema(
                                                        implementation = Product.class
                                                ))
                                        ),
                                        @ApiResponse(responseCode = "404", description = "No product found in the search for id.")

                                },
                                parameters = {
                                        @Parameter(in = ParameterIn.PATH, name = "id"),
                                }
                        )
                ),
                @RouterOperation(
                        path = "/api/product/{id}",
                        produces = {
                                MediaType.APPLICATION_JSON_VALUE
                        },
                        method = RequestMethod.PUT,
                        beanClass = HandlerProduct.class,
                        beanMethod = "listenUpdateProductUseCase",
                        operation = @Operation(
                                operationId = "listenUpdateProductUseCase",
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
                                                implementation = Product.class
                                        ))
                                ),
                                parameters = {
                                        @Parameter(in = ParameterIn.PATH, name = "id"),
                                }
                        )
                ),
                @RouterOperation(
                        path = "/api/product/{id}",
                        produces = {
                                MediaType.APPLICATION_JSON_VALUE
                        },
                        method = RequestMethod.DELETE,
                        beanClass = HandlerProduct.class,
                        beanMethod = "listenDeleteProductUseCase",
                        operation = @Operation(
                                operationId = "listenDeleteProductUseCase",
                                responses = {
                                        @ApiResponse(
                                                responseCode = "200",
                                                description = "successful operation",
                                                content = @Content(schema = @Schema(
                                                        implementation = Product.class
                                                ))
                                        ),
                                        @ApiResponse(responseCode = "404", description = "No product found in the search for id.")
                                },
                                parameters = {
                                        @Parameter(in = ParameterIn.PATH, name = "id"),
                                }
                        )
                )
        }
)
public RouterFunction<ServerResponse> routerFunctionProduct(HandlerProduct handler) {
    return route(POST("/api/product"), handler::listenPostAddProductUseCase)
            .andRoute(GET("/api/products"), handler::listenFindAllProductsUseCase)
            .andRoute(PUT("/api/product/{id}"), handler::listenUpdateProductUseCase)
            .andRoute(DELETE("/api/product/{id}"), handler::listenDeleteProductUseCase)
            .andRoute(GET("/api/product/{id}"), handler::listenFindByIdProductsUseCase);
    }
}
