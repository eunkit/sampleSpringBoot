package com.example.customerpoints.service.Impl;

import com.example.customerpoints.service.CustomerEndPointService;
import com.example.customerpoints.web.resource.UserData;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//This class is defined as business layer and implements CustomerEndPointService interface.
// The problem we're trying to solve here is
//A customer receives 2 points for every dollar spent over $100 in each transaction,
// plus 1 point for every dollar spent between $50 and $100 in each transaction.
//(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
// Calculate the points earned per month by a customer

@Service
@Component
public class CustomerEndPointServiceImpl implements CustomerEndPointService {
    @Override
    public Map<UUID, Map<String, Integer>> calculateCustomerPoints(List<UserData> data) throws Exception {

        Map<UUID, Map<String, Integer>> customerMonthDays = new HashMap<UUID, Map<String, Integer>>();
        //check if data is empty if yes throw exception
        if (data == null || data.isEmpty()) throw new RuntimeException("data is empty");

        for (data.forEach(i -> {
            Integer transactionAmount = i.getTransactionAmount().intValue();
            String month = i.getDate().getMonth().toString();
            Integer transactionPoints = 0;
            if (transactionAmount.intValue() > 100) {
                transactionPoints = (transactionAmount - 100) * 2 + 50 * 1;
            } else if (transactionAmount > 50 && transactionAmount <= 100) {
                transactionPoints = (transactionAmount - 50);
            }
            if (customerMonthDays.containsKey(i.getCustomerID())) {
                var temp = customerMonthDays.get(i.getCustomerID());
                if (temp.containsKey(month)) {
                    temp.put(month, temp.get(month) + transactionPoints);
                } else {
                    temp.put(month, transactionPoints);
                }
                customerMonthDays.replace(i.getCustomerID(), temp);

            } else {
                Map<String, Integer> temp = new HashMap<>();
                temp.put(month, transactionPoints);
                customerMonthDays.put(i.getCustomerID(), temp);
            }

        }); ; )
            return customerMonthDays;
    }
}
