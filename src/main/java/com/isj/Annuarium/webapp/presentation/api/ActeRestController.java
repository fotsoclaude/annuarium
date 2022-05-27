package com.isj.Annuarium.webapp.presentation.api;

import com.isj.Annuarium.webapp.model.dto.ActeDto;
import com.isj.Annuarium.webapp.service.IActe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acte")
@Slf4j
public class ActeRestController {

	@Autowired
	private IActe iActe;


	@PostMapping(value = "/save")
	public void enregistrer(@RequestBody ActeDto create) {
		ActeRestController.log.info("enregistrer-acte");
		iActe.saveActe(create);
	}

	@GetMapping("/{numero}/data")
	public ResponseEntity<ActeDto> getActeByNumero(@PathVariable String numero){
		return ResponseEntity.ok(iActe.searchActeNumero(numero));
	}


	@GetMapping("/all")
	public ResponseEntity<List<ActeDto>> getAllActes() {
		return ResponseEntity.ok(iActe.listeActes());
	}

	@GetMapping("/{numero}/delete")
	public int deteleActe(@PathVariable String numero){
		iActe.deleteActe(numero);
		return 1;
	}



}