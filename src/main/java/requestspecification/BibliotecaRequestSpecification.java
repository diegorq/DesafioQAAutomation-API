package requestspecification;

import core.PropertyLoader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class BibliotecaRequestSpecification {

    private static final String BASEURI = PropertyLoader.Service.BASEURI.getValue();

    public RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setConfig(
                        new RestAssuredConfig()
                                .sslConfig(
                                        new SSLConfig().relaxedHTTPSValidation()
                                )
                                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()
                                )
                )
                .setContentType(JSON)
                .setBaseUri(BASEURI)
                .build();
    }


}

