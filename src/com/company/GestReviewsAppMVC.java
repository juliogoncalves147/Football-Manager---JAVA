package com.company;

import Model.*;
import Controller.*;
import View.*;

public class GestReviewsAppMVC {
    public static final boolean leitura_friends = false;

    public static void main(String[] args){

        /*****CARREGAMENTO MODEL*****/
        InterfM model = new GestReviews();
        /*****CARREGAMENTO CONTROLLER*****/
        InterfC controller = new Controller(model);
        /*****CARREGAMENTO VIEW*****/
        InterfV view = new View(controller);
        /*****INICIO PROGRAMA*****/
        view.run();
    }
}
