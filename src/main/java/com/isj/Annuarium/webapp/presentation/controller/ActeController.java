package com.isj.Annuarium.webapp.presentation.controller;

import com.isj.Annuarium.webapp.model.dto.ActeDto;
import com.isj.Annuarium.webapp.service.IActe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

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
		return "enregistrer";
	}

	@GetMapping("/edition")
	public String updateActe(@RequestParam(name = "numero") String numero,Model model) {
		ActeDto acteDto = iActe.searchActeNumero(numero);
		return "editer";
	}

	@GetMapping("/rechercher")
	public String found(Model model) {
		//appel de la couche service pour avoir la liste des actes
		List<ActeDto> acteDtos = iActe.listeActes();
		model.addAttribute("Result",acteDtos);
		return "rechercher";
	}

	@GetMapping("/rechercherA")
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

	//Télécharger l'acte sous format PDF

	@RequestMapping("/pdf")

	public void getReportsinPDF(HttpServletResponse response) throws JRException, IOException {

		//Compiled report-
		InputStream jasperStream = (InputStream) this.getClass().getResourceAsStream("/actepdf.jrxml");

		//Adding attribute names
		Map params = new HashMap<>();
		params.put("stid","stid");
		params.put("name","name");
		params.put("programme","programme");

		// Fetching the student from the data database.
		final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(iActe.listeActes());

		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, source);

		response.setContentType("application/x-pdf");
		response.setHeader("Content-disposition", "inline; filename=actenaissance.pdf");

		final ServletOutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	}
	@GetMapping("/report")
	public ResponseEntity<byte[]> generateReport(@RequestParam(value = "numero") String numero) throws FileNotFoundException, JRException {
		ActeDto acteDto = iActe.searchActeNumero(numero);
		final byte[] data = iActe.exportReport(acteDto);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=actepdf.pdf");
		return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(data);
	}

}