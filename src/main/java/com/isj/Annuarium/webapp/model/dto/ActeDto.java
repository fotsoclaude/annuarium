package com.isj.Annuarium.webapp.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.FixedValue;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ActeDto {
    private     String numero;
    private     String nom;
    private     String prenom;
    private     String dob;
    private     String lieuNaissance;
    private     String nomPrenomPere;
    private     String nomPrenomMere;


}
