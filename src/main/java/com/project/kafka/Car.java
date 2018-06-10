package com.project.kafka;

/**
 * Created by rayanegouda on 10/06/2018.
 */
public class Car {

    int id;
    String nom;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Car(int id, String nom) {

        this.id = id;
        this.nom = nom;
    }
}
