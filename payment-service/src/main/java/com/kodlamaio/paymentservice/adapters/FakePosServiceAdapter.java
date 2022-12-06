package com.kodlamaio.paymentservice.adapters;

import com.kodlamaio.common.util.exceptions.BusinessException;
import com.kodlamaio.paymentservice.business.abstracts.PosService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class FakePosServiceAdapter implements PosService {
    @Override
    public void pay() {
        int randomNumber = new Random().nextInt(10);
        if (randomNumber < 5) {
            throw new BusinessException("Invalid payment");
        }
    }
}
