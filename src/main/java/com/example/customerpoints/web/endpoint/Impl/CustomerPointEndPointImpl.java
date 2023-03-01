package com.example.customerpoints.web.endpoint.Impl;

import com.example.customerpoints.service.CustomerEndPointService;
import com.example.customerpoints.web.endpoint.CustomerPointEndPoint;
import com.example.customerpoints.web.resource.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.UUID;


//This class implements the customer endpoint interface and is also defined as rest controller.
//This class has exposed endpoints so this API can be called.
@RestController
@Component
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class CustomerPointEndPointImpl implements CustomerPointEndPoint {

    @Inject
    private CustomerEndPointService customerEndPointService;

    @Override
    public Map<UUID, Map<String, Integer>> findCustomerPointsFromData(List<UserData> data) throws Exception {
        //Call the business layer and return the data
        return customerEndPointService.calculateCustomerPoints(data);
    }

    @Override
    public String getCustomerInfo() {
        return "Use Postman to pass data and get customer Info, use POST and /customerPoints Url. The sample data is in " +
                "sample_data.txt file";
    }
}
