package repository;

public interface UserRepository<T> extends CrudRepository<T> {
    long getLastInsertId();
}
