package tn.esprit.autoloc.autolocapi.service;

import java.util.List;

public interface IReadService<T, ID> {
    T findById(ID id);
    List<T> findAll();
    boolean check(T entity, boolean isNew);
}