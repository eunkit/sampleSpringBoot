package com.example.customerpoints.service;

import com.example.customerpoints.web.resource.UserData;

import java.util.List;
import java.util.Map;
import java.util.UUID;

//Interface for business layer
public interface CustomerEndPointService {

    Map<UUID, Map<String, Integer>> calculateCustomerPoints(List<UserData> data) throws Exception;

}
