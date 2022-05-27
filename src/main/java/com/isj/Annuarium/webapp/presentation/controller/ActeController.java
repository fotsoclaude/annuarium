package com.isj.Annuarium.webapp.presentation.controller;

import com.isj.Annuarium.webapp.model.dto.ActeDto;
import com.isj.Annuarium.webapp.service.IActe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@Slf4j
public class ActeController {

	@Autowired
	IActe iActe;

	@GetMapping("/")
	public String pageAccueil(Model model) {
		return "index";
	}
	@GetMapping("/listeactes")
	public String Listing(Model model) {
		//appel de la couche service pour avoir la liste des actes

		List<ActeDto> acteDtos = iActe.listeActes();
		model.addAttribute("acteDtos",acteDtos);

		return "list";
	}

	@GetMapping("/enregistrer")
	public String Saving(Model model) {
		ActeDto acteDto = new ActeDto();
		acteDto.setNumero("CM");
		model.addAttribute("acteDto", acteDto);
		return "enregistrer";
	}

	@GetMapping("/edition")
	public String updateActe(@RequestParam(name = "numero") String numero,Model model) {
		ActeDto acteDto = iActe.searchActeNumero(numero);
		model.addAttribute("acteDto", acteDto);
		return "editer";
	}

	@GetMapping("/rechercher")
	public String found(Model model) {
		//appel de la couche service pour avoir la liste des actes

		List<ActeDto> acteDtos = iActe.listeActes();
		model.addAttribute("Result",acteDtos);

		return "rechercher";
	}

	@PostMapping("/rechercherA")
	public String found(@RequestParam(name="nom") String nom, Model model) {
		List<ActeDto> acteDto = iActe.searchActeByKeyword(nom);
		model.addAttribute("Result",acteDto);
		return "rechercher";
	}

	@GetMapping("/detail")
	public String pagedetail(@RequestParam(name = "numero") String numero, Model model) {
		ActeDto acteDto = iActe.searchActeNumero(numero);
		model.addAttribute("thisActe",acteDto);
		return "detail";
	}
	@GetMapping("/suppression")
	public String supprimer(@RequestParam(name = "numero") String numero, Model model) {
		int res = iActe.deleteActe(numero);
		return "redirect:/listeactes";
	}

	@PostMapping("/enregistreracte")
	public String enregistrerActe(@ModelAttribute ActeDto acteDto,Model model) {
		iActe.saveActe(acteDto);
		return "redirect:/listeactes";
	}

	@PostMapping("/editer")
	public String editionForm(@ModelAttribute ActeDto acteDto,Model model) {
		iActe.updateActe(acteDto);
		return "redirect:/listeactes";
	}

}