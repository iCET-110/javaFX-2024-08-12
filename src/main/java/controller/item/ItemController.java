package controller.item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dto.Item;
import dto.OrderDetail;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemController implements ItemService {

    private static ItemController instance;
    private ItemController(){}

    public static ItemController getInstance() {
        return instance==null?instance=new ItemController():instance;

    }

    @Override
    public boolean addItem(Item item) {
        String SQl = "INSERT INTO Item VALUES(?,?,?,?,?)";
        try {
            return CrudUtil.execute(
                    SQl,
                    item.getItemCode(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQty()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean deleteItem(String id) {
        try {
            return CrudUtil.execute("DELETE  FROM item WHERE itemCode=?", id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public ObservableList<Item> getAll() {
        String SQl = "SELECT * FROM item";
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = CrudUtil.execute(SQl);

            while (resultSet.next()) {
                itemObservableList.add(new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)
                ));
            }
            return itemObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateUpdate(Item item) {
        String SQL = "UPDATE item SET Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode=?";
        try {
            return CrudUtil.execute(
                    SQL,
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQty(),
                    item.getItemCode()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item searchItem(String itemCode) {
        String SQl = "SELECT * FROM item WHERE itemCode=?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQl, itemCode);
            while (resultSet.next()) {
                return new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean updateStock(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail: orderDetails){
           boolean updateStock = updateStock(orderDetail);
           if (!updateStock){
               return false;
           }
        }
        return true;
    }
    public boolean updateStock(OrderDetail orderDetails){
        String SQL = "UPDATE Item SET QtyOnHand=QtyOnHand-? WHERE ItemCode=?";
        try {
           return CrudUtil.execute(SQL,orderDetails.getQty(),orderDetails.getItemCode());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<String> getItemCodes(){
        ObservableList<String> itemCodes = FXCollections.observableArrayList();
        ObservableList<Item> all = getAll();

        all.forEach(item->{
            itemCodes.add(item.getItemCode());
        });
        return itemCodes;

    }


}
