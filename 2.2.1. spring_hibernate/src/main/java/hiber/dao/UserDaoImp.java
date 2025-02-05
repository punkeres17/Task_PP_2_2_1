package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {


    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImp(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(final User user) {
        sessionFactory.getCurrentSession().save(user);
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        final String hqlQuery = "from User u join fetch u.car";
        final TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hqlQuery);
        return query.getResultList();
    }

    @Override
    public User getUserCar(final String model, final int series) {
        final String hqlQuery = "from User where car.model = :model and car.series = :series";
        final TypedQuery<User> query = sessionFactory
                .getCurrentSession()
                .createQuery(hqlQuery, User.class);
        query.setParameter("model", model);
        query.setParameter("series", series);

        return query.getSingleResult();
    }

}
