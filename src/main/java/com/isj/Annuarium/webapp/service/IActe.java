package com.isj.Annuarium.webapp.service;

import com.isj.Annuarium.webapp.model.dto.ActeDto;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public interface IActe {

    int saveActe(ActeDto acteDto);
    ActeDto searchActeNumero(String numero);
    List<ActeDto> listeActes();

    int deleteActe(String numero);

    List<ActeDto> searchActeByKeyword(String keyword);

    ActeDto updateActe(ActeDto acteDto);

    byte[] exportReport(ActeDto acteDto) throws FileNotFoundException, JRException;
}
