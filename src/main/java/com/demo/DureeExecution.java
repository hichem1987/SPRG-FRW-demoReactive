package com.demo;

public class DureeExecution {

    private long startTime;
    private long endTime;
    private String taskName;

    public DureeExecution(String taskName) {
        this.taskName = taskName;
    }

    public void capturerHeureDebut(){
        startTime = System.nanoTime();
    }

    public void capturerHeureFin(){
        endTime = System.nanoTime();
    }


    private long calculerDuree(){
        long duration = endTime - startTime;
        return duration;
    }

    public void afficherLaDureeEnNanosecondes(){
        System.out.println("Durée d'exécution: " + calculerDuree() + " nanosecondes");
    }
    public void afficherLaDureeEnSecondes(){
        System.out.println("Durée d'exécution tache <"+taskName+"> : " + (calculerDuree() / 1_000_000_000.0) + " secondes");
    }
}