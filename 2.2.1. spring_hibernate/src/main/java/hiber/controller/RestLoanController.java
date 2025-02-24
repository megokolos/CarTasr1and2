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

import java.util.List;

@RestController
public class RestLoanController {
    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${loan.minimalIncome}")
    private int minimumIncome;
    @Value("${loan.minimumCarPrice}")
    private int minimumCarPrice;
    @Value("${loan.percentFromIncome}")
    private double percentOfIncome;
    @Value("${loan.percentFromCarPrice}")
    private double percentOfCar;

    @RequestMapping("/loan/{userId}")
    public String getLoanSum(@PathVariable("userId") Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://66055cd12ca9478ea1801f2e.mockapi.io/api/users/income";
        String json = restTemplate.getForObject(url, String.class);
        try {
            Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
            System.out.println("Jackson найден!");
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка: Jackson не найден!");
        }
        if (objectMapper == null) {
            return "no jackson!";
        }
        JsonNode node = null;
        try {
            node = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<User> jsonUsers = null;
        try {
            jsonUsers = objectMapper.readValue(node.toString(), new TypeReference<List<User>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        for (User user : jsonUsers){
            if (user.getId() == id){
                User userfromDb = userService.getUserById(id);

            int thisUserIncome = user.getIncome();
            int thisUserCarPrice = userfromDb.getCar().getPrice();
            if (thisUserIncome>=minimumIncome || thisUserCarPrice>=minimumCarPrice) {
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
