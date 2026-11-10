package tn.esprit.autoloc.autolocapi.service;

public interface IWriteService<T, ID> {
    T create(T entity);
    T update(ID id, T entity);
    void deleteById(ID id);
}