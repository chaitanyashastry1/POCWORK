package com.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

public class ApiUtils {

        public String getOTP(String uri, String mode, String auth){
        RestAssured.baseURI = uri;
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization",auth)
                .param("medium",mode)
                .when()
                .get()
                .then()
                .extract().response();
        HashMap data = new HashMap();
        data= response.jsonPath().getJsonObject("data");
        String otp = data.get("Requested_OTP").toString();
        return otp;
    }
        public String getMobileOTP(String apiURI){
                return "Hello";
        }
}
