package com.epf.rentmanager.model;
import java.time.LocalDate;
import java.util.Date;


public record Client (long id, String nom, String prenom, String email, LocalDate naissance) {
    public Client () {
        this(0, null, null, null, null);
    }

    public Client(long id, String nom, String prenom, String email, LocalDate naissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;
    }

    @Override
    public String toString(){
        return "Informations sur le client : " +
                ", ID : " + id +
                ", Nom : " + nom +
                ", Prénom : " + prenom +
                ", Email : " + email +
                ", Date de naissance : " + naissance;
    }



}