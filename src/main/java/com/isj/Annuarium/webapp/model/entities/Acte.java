package com.isj.Annuarium.webapp.model.entities;

import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
public class Acte {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String numero;

    private String nom;
    private String prenom;
    private String dob;
    private String lieuNaissance;
    private String nomPrenomPere;
    private String nomPrenomMere;

}
