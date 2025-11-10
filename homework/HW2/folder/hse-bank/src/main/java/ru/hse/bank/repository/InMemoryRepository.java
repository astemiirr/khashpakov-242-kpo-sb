package ru.hse.bank.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class InMemoryRepository<T, ID> implements Repository<T, ID> {
    protected final Map<ID, T> storage = new HashMap<>();

    @Override
    public T save(T entity) {
        ID id = getId(entity);
        storage.put(id, entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void delete(ID id) {
        storage.remove(id);
    }

    protected abstract ID getId(T entity);
}