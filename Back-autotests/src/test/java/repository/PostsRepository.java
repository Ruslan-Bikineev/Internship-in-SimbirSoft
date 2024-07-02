package repository;

public interface PostsRepository<T> extends CrudRepository<T> {
    long getLastInsertId();
}