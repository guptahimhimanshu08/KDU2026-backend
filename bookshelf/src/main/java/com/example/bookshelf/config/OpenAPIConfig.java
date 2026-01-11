@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI libraryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Library Management API")
                        .description("API for managing books in the library")
                        .version("v1")
                        .contact(new Contact()
                                .name("Library Team")
                                .email("support@library.com")
                        )
                );
    }
}
