package Tests;

import Controller.*;
import Model.*;
import Auxiliar.*;
import java.io.IOException;

public class Load_Tests {

    private InterfM model;
    private InterfC controller;

    public Load_Tests(){
        this.model = new GestReviews();
    }

    public String exec() throws IOException {
        long memAntes, memDepois;
        double time;
        StringBuilder sb = new StringBuilder();

        memAntes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        Crono.start();
        model.init_GestReviews("Input/business_full.csv", "Input/reviews_1M.csv", "Input/users_full.csv");
        this.controller = new Controller(model);
        time = Crono.stop();
        memDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        sb.append(" Carregar ficheiros default : ");
        sb.append(" Tempo: " + time);
        sb.append(" Memoria: " + (memDepois - memAntes));

        return sb.toString();
    }
}