package scs.exe201.secondchanceshopbe.services;

import scs.exe201.secondchanceshopbe.models.dtos.response.OrderResponse;
import scs.exe201.secondchanceshopbe.models.entities.OrderEntity;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentLinkData;

public interface IPayOsService {
    CheckoutResponseData paymentLink(long orderId);
    PaymentLinkData getPaymentLinkData(long orderId);

    OrderResponse actionCancel(long orderCode);

    OrderResponse actionSuccess(long orderCode);
}
