package com.nisal.iceflame.dto;

import com.nisal.iceflame.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutRequest {

    private Long userId;

    private Long addressId;
    private PaymentMethod paymentMethod;

}
