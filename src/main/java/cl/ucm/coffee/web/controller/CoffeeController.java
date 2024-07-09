package cl.ucm.coffee.web.controller;

import cl.ucm.coffee.persitence.entity.CoffeeEntity;
import cl.ucm.coffee.persitence.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coffee")
public class CoffeeController {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CoffeeEntity>> getCoffees() {
        List<CoffeeEntity> coffees = coffeeRepository.findAll();
        return ResponseEntity.ok(coffees);
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CoffeeEntity> saveCoffee(@RequestBody CoffeeEntity coffee) {
        CoffeeEntity savedCoffee = coffeeRepository.save(coffee);
        return ResponseEntity.ok(savedCoffee);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCoffee(@PathVariable int id) {
        coffeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
