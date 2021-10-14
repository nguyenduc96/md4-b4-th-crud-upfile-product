package com.nguyenduc.service;

import java.util.List;

public interface IGeneralService<T> {
    List<T> findAll();

    void save(T t);

    void delete(Long id);

    List<T> findByName(String name);

    T findById(Long id);
}
