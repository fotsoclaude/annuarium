package com.isj.Annuarium.webapp.service;
import com.isj.Annuarium.webapp.mapper.ActeMapper;
import com.isj.Annuarium.webapp.model.dto.ActeDto;
import com.isj.Annuarium.webapp.model.entities.Acte;
import com.isj.Annuarium.webapp.repository.ActeRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class ActeServiceImpl implements  IActe{
    @Autowired
    ActeRepository acteRepository;

    @Autowired
    ActeMapper acteMapper;

    @Override
    public int saveActe(ActeDto acteDto){
        if( !acteRepository.findActeByNumero(acteDto.getNumero()).isPresent() ) return acteRepository.save(acteMapper.toEntity(acteDto)).getId().intValue();
        else return 0;
    }

    @Override
    public ActeDto searchActeNumero(String numero){
        return acteMapper.toDto(acteRepository.findActeByNumero(numero).get());
    }

    @Override
    public List<ActeDto> listeActes(){
        return  acteRepository.findAll().stream().map(acteMapper::toDto).collect(Collectors.toList());
    }
    @Override
    public int deleteActe(String numero){
        acteRepository.deleteById(acteRepository.findActeByNumero(numero).get().getId());
        return 1;
    }

    @Override
    public List<ActeDto> searchActeByKeyword(String keyword){
        return acteRepository.findActeByNumeroOrNom(keyword,keyword).get().stream().map(acteMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ActeDto updateActe(ActeDto acteDto) {
        //rechercher l'entité qui correspond à l'acte que nous voulons update

        Acte acte = acteRepository.findActeByNumero(acteDto.getNumero()).get();

        acteMapper.copy(acteDto,acte);

        return acteMapper.toDto(acteRepository.save(acte));

    }

    @Override
    public byte[] exportReport (ActeDto acteDto) throws FileNotFoundException, JRException {
        List<ActeDto> actes = new ArrayList<>();
        actes.add(acteDto);
        //load file and compile it
        //File file = ResourceUtils.getFile("src/main/resources/actepdf.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/actepdf.jrxml"));
        // donne la source de donnée

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(actes);
        Map<String , Object> parameters = new HashMap<>();
        parameters.put("createBy" , "java Techie");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport , parameters , dataSource);
        byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);

        return data;
    }


}
