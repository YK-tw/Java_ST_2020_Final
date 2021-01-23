package by.training.finalp.dao.mysql;

import java.sql.Connection;

public abstract class BaseDAO {
    protected Connection connection;

    public void setConnection(final Connection gotConnection) {
        connection = gotConnection;
    }
}
