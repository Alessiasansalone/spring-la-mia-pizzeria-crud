package it.lessons.pizzeria.repository;

import it.lessons.pizzeria.model.Pizze;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzeRepository extends JpaRepository<Pizze, Integer> {

	public List<Pizze> findByNameContaining(String name);
}
