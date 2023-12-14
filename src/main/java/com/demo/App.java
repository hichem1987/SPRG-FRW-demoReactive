package com.demo;

import reactor.core.publisher.Mono;

import java.time.Duration;

public class App
{
    public static void main( String[] args )
    {
        //getNonReactiveData();
        //getReactiveData();

        //multiTaches();
        multiTachesReactives();

    }

    private static void multiTaches() {
        DureeExecution dureeTotale = new DureeExecution("iteratif");
        dureeTotale.capturerHeureDebut();

        String resultat1 = resultatIntermediaire1().block();
        String resultat2 = resultatIntermediaire2().block();
        System.out.println("RESULTAT FINAL: " + resultat1 + " "+resultat2);

        dureeTotale.capturerHeureFin();
        dureeTotale.afficherLaDureeEnSecondes();
    }

    private static void multiTachesReactives() {


        DureeExecution dureeTotale = new DureeExecution("totale");
        dureeTotale.capturerHeureDebut();

        Mono<String> mono1 = resultatIntermediaire1();

        DureeExecution duree1 = new DureeExecution("Task 1");
        duree1.capturerHeureDebut();
        mono1.subscribe(
                e -> {
                    System.out.println(e);
                    duree1.capturerHeureFin();
                    duree1.afficherLaDureeEnSecondes();
                }
        );


        Mono<String> mono2 = resultatIntermediaire2();

        DureeExecution duree2 = new DureeExecution("Task 2");
        duree2.capturerHeureDebut();
        mono2.subscribe(
                e -> {
                    System.out.println(e);
                    duree2.capturerHeureFin();
                    duree2.afficherLaDureeEnSecondes();
                }
        );


        Mono<String> resultatFinal = mono1.zipWith(mono2)
                .map(
                        value -> {
                            return "RESULTAT FINAL: " + value.getT1() + "" + value.getT2();
                        }
                );
        resultatFinal.subscribe(
                e -> {
                    System.out.println(e);
                    dureeTotale.capturerHeureFin();
                    dureeTotale.afficherLaDureeEnSecondes();
                }
        );


        pause();
    }

    private static Mono<String> resultatIntermediaire2() {
        return Mono.just("Second computed data").delayElement(Duration.ofSeconds(5));
    }

    private static Mono<String> resultatIntermediaire1() {
        return Mono.just("First computed data").delayElement(Duration.ofSeconds(5));
    }

    private static void getReactiveData() {
        ReactiveData.stringNumbersFlux().subscribe(
                e -> {
                    System.out.println(e);
                }
        );
        pause();
    }

    private static void pause(){
        System.out.println("Pour arrêter le programme, appuyez une touche du clavier.");
        try {
            System.in.read();
        }
        catch(Exception e){
        }
    }

    private static void getNonReactiveData() {
        NonReactiveData.stringNumbersStream().forEach(
                e -> {
                    System.out.println(e);
                }
        );
    }
}
