package repository;

public interface CommentRepository<T> extends CrudRepository<T> {
    long getLastInsertId();
}
