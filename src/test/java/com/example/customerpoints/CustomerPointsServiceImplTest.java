package com.example.customerpoints;

import com.example.customerpoints.service.Impl.CustomerEndPointServiceImpl;
import com.example.customerpoints.web.resource.UserData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class CustomerPointsServiceImplTest {

    @InjectMocks
    private CustomerEndPointServiceImpl service;

    private List<UserData> testSuiteCustomerDataSetup(String path) {

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> {
            try {
                return LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                return LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        }).create();

        JsonReader reader = new JsonReader(new StringReader(path));
        reader.setLenient(true);
        return gson.fromJson(reader, new TypeToken<List<UserData>>() {
        }.getType());
    }

    @Test
    void calculateCustomerPoints() throws Exception {
        String data = "[{\"customerID\": \"12115efe-ad9c-11ed-afa1-0242ac120002\",\"transactionAmount\": 120.5,\"date\": \"2022-12-13\"},{\"customerID\": \"12115efe-ad9c-11ed-afa1-0242ac120002\",\"transactionAmount\": 120.5,    \"date\": \"2023-02-13\"  },  {    \"customerID\": \"12115efe-ad9c-11ed-afa1-0242ac120002\",    \"transactionAmount\": 120.4,    \"date\": \"2023-02-13\"  },  {    \"customerID\": \"12115efe-ad9c-11ed-afa1-0242ac120002\",    \"transactionAmount\": 50.9,   \"date\": \"2023-01-13\"  },  {    \"customerID\": \"12115efe-ad9c-11ed-afa1-0242ac120002\",    \"transactionAmount\": 59,    \"date\": \"2023-01-13\"  },  {    \"customerID\": \"12115efe-ad9c-11ed-afa1-0242ac120002\",    \"transactionAmount\": 59,    \"date\": \"2023-01-14\"  }, {    \"customerID\": \"12115efe-ad9c-11ed-afa1-0242ac120021\",    \"transactionAmount\": 120.4,    \"date\": \"2023-02-12\"  },  {    \"customerID\": \"12115efe-ad9c-11ed-afa1-0242ac120021\",   \"transactionAmount\": 100,    \"date\": \"2023-01-12\"  },  {    \"customerID\": \"12115efe-ad9c-11ed-afa1-0242ac120021\",    \"transactionAmount\": 49,    \"date\": \"2023-01-12\"  },  {    \"customerID\": \"12115efe-ad9c-11ed-afa1-0242ac120021\",    \"transactionAmount\": 103,    \"date\": \"2023-01-12\"  },  {    \"customerID\": \"12115efe-ad9c-11ed-afa1-0242ac120021\",    \"transactionAmount\": 103, \"date\": \"2023-01-12\"  }]";
        var testData = testSuiteCustomerDataSetup(data);
        String expected = "{12115efe-ad9c-11ed-afa1-0242ac120002={JANUARY=18, DECEMBER=90, FEBRUARY=180}, 12115efe-ad9c-11ed-afa1-0242ac120021={JANUARY=162, FEBRUARY=90}}";
        assertThat(service.calculateCustomerPoints(testData).toString()).isEqualTo(expected.toString());
    }

    @Test
    void calculateCustomerPoints_throws() {
        String data = "[]";
        var testData = testSuiteCustomerDataSetup(data);

        assertThatThrownBy(() -> service.calculateCustomerPoints(testData)).isInstanceOf(RuntimeException.class);
    }

}
