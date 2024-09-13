package controller.order;

import model.OrderDetail;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {
    public boolean addOrderDetail(List<OrderDetail> orderDetails){
        for (OrderDetail orderDetail:orderDetails) {
            return addOrderDetail(orderDetail);
        }
        return false;
    }
    public boolean addOrderDetail(OrderDetail orderDetails){
        String SQL = "INSERT INTO orderdetail VALUES(?,?,?,?)";
        try {
            Object execute = CrudUtil.execute(SQL,
                    orderDetails.getOrderId(),
                    orderDetails.getItemCode(),
                    orderDetails.getQty(),
                    orderDetails.getDiscount()
            );
            if (execute!=null){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
