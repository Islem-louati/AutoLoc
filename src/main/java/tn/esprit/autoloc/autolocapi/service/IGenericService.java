package tn.esprit.autoloc.autolocapi.service;

public interface IGenericService<T, ID> extends IReadService<T, ID>, IWriteService<T, ID> {}