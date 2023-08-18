package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = sf.openSession();
        session.beginTransaction();
        User item = new User();
        item.setId(userId);
        session.delete(item);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<User> result = session.createQuery("from User order by id asc", User.class)
                .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<User> query = session.createQuery(
                "from User as i where i.id = :fId", User.class);
        query.setParameter("fId", userId);
        Optional<User> result = query.uniqueResultOptional();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<User> query = session.createQuery(
                "from User as u where u.login like :fLogin", User.class);
        query.setParameter("fLogin", key);
        List<User> result = query.list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<User> query = session.createQuery(
                "from User as u where u.login=:fLogin", User.class);
        query.setParameter("fLogin", login);
        Optional<User> result = query.uniqueResultOptional();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
