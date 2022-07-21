package com.cmplete.reggiedemo.dto;

import com.cmplete.reggiedemo.entity.OrderDetail;
import com.cmplete.reggiedemo.entity.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
