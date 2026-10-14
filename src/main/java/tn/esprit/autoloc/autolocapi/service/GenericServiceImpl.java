package tn.esprit.autoloc.autolocapi.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class GenericServiceImpl<T, ID> implements IGenericService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    protected GenericServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    // Méthodes abstraites demandées par l'énoncé
    protected abstract String getEntityName();

    protected abstract ID getId(T entity);

    protected abstract void setFields(T source, T target);

    // Validation par défaut : les services concrets la redéfinissent
    @Override
    public boolean check(T entity, boolean isNew) {
        return entity != null;
    }

    @Override
    public T create(T entity) {
        if (getId(entity) != null) {
            throw new IllegalArgumentException("L'identifiant doit être vide lors de la création");
        }
        if (!check(entity, true)) {
            throw new IllegalArgumentException("Les données de " + getEntityName() + " sont invalides");
        }
        return repository.save(entity);
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(getEntityName() + " introuvable avec l'id " + id));
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T update(ID id, T entity) {
        T existing = findById(id);
        if (!check(entity, false)) {
            throw new IllegalArgumentException("Les données de " + getEntityName() + " sont invalides");
        }
        setFields(entity, existing);
        return repository.save(existing);
    }

    @Override
    public void deleteById(ID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(getEntityName() + " introuvable avec l'id " + id);
        }
        repository.deleteById(id);
    }
}