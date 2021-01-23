package by.training.finalp.dao;

public interface TransactionFactory {
    Transaction createTransaction();

    void close();
}