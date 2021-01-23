package by.training.finalp.service;

import by.training.finalp.dao.Transaction;

public abstract class ServiceImpl implements Service {
    protected Transaction transaction = null;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
