package it.lessons.pizzeria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

import it.lessons.pizzeria.repository.PizzeRepository;
import jakarta.validation.Valid;
import it.lessons.pizzeria.model.Pizze;

@Controller
@RequestMapping("/pizze")
public class PizzeController {

	@Autowired
	private PizzeRepository pizzeRepo;
	
	@GetMapping 
	public String index(Model model,@RequestParam(name = "keyword", required = false) String keyword) {
		
		List<Pizze> allPizze;
		
		if(keyword != null && !keyword.isBlank()) {
					
			allPizze = pizzeRepo.findByNameContaining(keyword);
			model.addAttribute("keyword", keyword);
			
			} else {
				allPizze = pizzeRepo.findAll();
			}
		
		model.addAttribute("pizze", allPizze);
		
		return "pizze/index";
	}
	
	 @GetMapping("/show/{id}")
	 public String show(@PathVariable(name = "id") Integer id, Model model) {
	 
		 Optional<Pizze> pizzeOptional = pizzeRepo.findById(id);
		 
		 if(pizzeOptional.isPresent()) {
			 model.addAttribute("pizze", pizzeOptional.get());
		 }
		 
	 return "/pizze/show";
	 }
	 
	 @GetMapping("/create")
	 public String create(Model model) {
		 
		 model.addAttribute("pizza", new Pizze());
		 
		 return "pizze/create";
		 
	 }
	 
	 @PostMapping("/create")
	 public String store(@Valid @ModelAttribute("pizza") Pizze formPizze, 
			 BindingResult bindingResult, Model model, RedirectAttributes redirectiAttributes) {
		 
		 if(bindingResult.hasErrors()) {
			 return "/pizze/create";
		 } 
		
		 pizzeRepo.save(formPizze);
		
		 
		 return "redirect:/pizze";
	 }
	
}
