package hiber.controller;

import hiber.dao.CarDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class CarsController {
    @Autowired
    CarDao carDao;

    @GetMapping("/cars")
    public String getCars(@RequestParam(value = "count", required = false) Integer count,
                          Model model) {
        model.addAttribute("cars", carDao.listCars(count));
        return "cars";
    }
}
