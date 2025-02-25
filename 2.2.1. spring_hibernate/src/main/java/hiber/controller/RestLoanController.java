package hiber.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.javamentor.client.IncomeClient;

import java.util.List;
import java.util.Objects;

@RestController
public class RestLoanController {
    @Autowired
    private UserService userService;
    @Autowired
    private IncomeClient incomeClient;

    @Value("${loan.minimalIncome}")
    private int minimumIncome;
    @Value("${loan.minimumCarPrice}")
    private int minimumCarPrice;
    @Value("${loan.percentFromIncome}")
    private double percentOfIncome;
    @Value("${loan.percentFromCarPrice}")
    private double percentOfCar;
    @Value("${loan.url}")
    private String url;


    @RequestMapping("/loan/{userId}")
    public String getLoanSum(@PathVariable("userId") Long id) {
        List<User> jsonUsers = incomeClient.getUsersIncome(url, User.class);
        for (User user : jsonUsers) {
            if (Objects.equals(user.getId(), id)) {
                User userfromDb = userService.getUserById(id);

                int thisUserIncome = user.getIncome();
                int thisUserCarPrice = userfromDb.getCar().getPrice();
                if (thisUserIncome >= minimumIncome || thisUserCarPrice >= minimumCarPrice) {
                    return "Sum - " +
                            (Math.max(thisUserIncome * percentOfIncome * 12, thisUserCarPrice * percentOfCar));
                } else {
                    return "No loan";
                }
            }
        }
        return "Error";
    }
}
