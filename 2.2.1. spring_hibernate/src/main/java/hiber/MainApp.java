package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        Car car1 = new Car("audi", 80);
        Car car2 = new Car("mers", 63);
        Car car3 = new Car("bmw", 5);
        Car car4 = new Car("lada", 9);

        userService.add(new User(car1, "User1", "Lastname1", "user1@mail.ru"));
        userService.add(new User(car2, "User2", "Lastname2", "user2@mail.ru"));
        userService.add(new User(car3, "User3", "Lastname3", "user3@mail.ru"));
        userService.add(new User(car4, "User4", "Lastname4", "user4@mail.ru"));

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println();
        }

        List<User> usersAndCars = userService.getUserByCar("audi", 80);

        for (User user : usersAndCars) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println(user.getCar());
        }

        context.close();
    }
}
