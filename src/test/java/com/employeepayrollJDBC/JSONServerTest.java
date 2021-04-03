package com.employeepayrollJDBC;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JSONServerTest {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 4000;
    }

    public JSONServerEmpData[] getEmplist(){
        Response response = RestAssured.get("/employees");
        System.out.println("Data in json is : " + response.asString());
        JSONServerEmpData[] restAssureEmpData = new Gson().fromJson(response.asString(), JSONServerEmpData[].class);
        return restAssureEmpData;
    }

    public Response addEmployeeToJsonServer(JSONServerEmpData restAssureEmpData) {
        String employee = new Gson().toJson(restAssureEmpData);
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Content-Type", "application/json");
        requestSpecification.body(employee);
        return requestSpecification.post("/employees");
    }

    @Test
    public void givenEmployeeshouldRetrieveRecords(){
        JSONServerEmpData[] restAssureEmpData=getEmplist();
        System.out.println(restAssureEmpData);
        Assert.assertEquals(8,restAssureEmpData.length);
    }

    @Test
    public void whenNewEmployeeisAddedShouldreturn201ResponseCode(){
        JSONServerEmpData[] jsonServerEmpData=getEmplist();

        JSONServerEmpData jsonServerEmpData1=new JSONServerEmpData(9,"Suresh",8000);

        Response response=addEmployeeToJsonServer(jsonServerEmpData1);
        int statusCode= response.statusCode();
        Assert.assertEquals(201,statusCode);
    }
}
