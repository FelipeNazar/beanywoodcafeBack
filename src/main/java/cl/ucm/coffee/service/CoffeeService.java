package cl.ucm.coffee.service;

import cl.ucm.coffee.persitence.entity.CoffeeEntity;
import cl.ucm.coffee.persitence.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    public List<CoffeeEntity> getAllCoffees() {
        return coffeeRepository.findAll();
    }

    public CoffeeEntity saveCoffee(CoffeeEntity coffee) {
        return coffeeRepository.save(coffee);
    }
}
