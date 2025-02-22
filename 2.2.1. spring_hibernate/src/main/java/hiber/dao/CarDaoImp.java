package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CarDaoImp implements CarDao {
    @Value("${maxCar}")
    private int maxCar;

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @Override
    public List<Car> listCars(Integer numberOfCars) {
        TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery("from Car", Car.class);
        if (numberOfCars == null || numberOfCars >= maxCar) {
            numberOfCars = maxCar;
        }
        query.setMaxResults(numberOfCars);
        return query.getResultList();
    }
}
