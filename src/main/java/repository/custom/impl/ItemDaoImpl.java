package repository.custom.impl;

import javafx.collections.ObservableList;
import dto.Item;
import repository.custom.ItemDao;

public class ItemDaoImpl implements ItemDao {
    @Override
    public boolean save(Item item) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public ObservableList<Item> getAll() {
        return null;
    }

    @Override
    public boolean update(Item item) {
        return false;
    }

    @Override
    public Item search(String id) {
        return null;
    }

    @Override
    public Integer getQTY(String itemCode) {
        return null;
    }
}
