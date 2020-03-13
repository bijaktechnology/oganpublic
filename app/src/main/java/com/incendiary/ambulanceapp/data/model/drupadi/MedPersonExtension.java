package com.incendiary.ambulanceapp.data.model.drupadi;

public class MedPersonExtension {

    public static String getActualStatus(MedPerson medPerson){
        if(medPerson.getStatus().equalsIgnoreCase("A")){
            return "Ada";
        }
        return "Sibuk";
    }
}
