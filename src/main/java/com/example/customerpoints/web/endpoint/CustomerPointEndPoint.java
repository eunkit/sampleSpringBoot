package com.example.customerpoints.web.endpoint;

import com.example.customerpoints.web.resource.UserData;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//Creating an interface.

@RequestMapping(path = "/", produces = "application/json")
public interface CustomerPointEndPoint {

    @RequestMapping(method = RequestMethod.POST , path ="/customerPoints")
    public Map<UUID, Map<String, Integer>> findCustomerPointsFromData(@RequestBody @Valid List<UserData> data) throws Exception;

    @RequestMapping(method = RequestMethod.GET)
    public String getCustomerInfo();
}
