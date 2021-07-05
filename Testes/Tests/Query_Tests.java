package Tests;

import Controller.*;
import Exceptions.NaoExisteException;
import Model.*;
import Auxiliar.*;

import javax.net.ssl.ManagerFactoryParameters;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;


public class Query_Tests {

        private InterfM model;
        private InterfC controller;

        public Query_Tests(){
            this.model = new GestReviews();
        }

        public String exec() throws IOException {

            model.init_GestReviews("Input/business_full.csv", "Input/reviews_1M.csv", "Input/users_full.csv");
            this.controller = new Controller(model);
            StringBuilder sb = new StringBuilder();
            for(int i = 1 ; i <= 10 ; i++)
                 sb.append(querie_exec(i)).append("\n");
            return sb.toString();
        }

        public String querie_exec(int i){
            MemoryMXBean gb = ManagementFactory.getMemoryMXBean();
            long memAntes, memDepois;
            double maxT, somaT, minT, time;
            int monthMax, yearMax, monthMin, yearMin;
            String sMax = "";
            StringBuilder sb = new StringBuilder();

            maxT = Double.MIN_VALUE;
            minT = Double.MAX_VALUE;
            somaT = 0D;
            monthMax = yearMax = 0;
            yearMin = monthMin = 3000;

            gb.gc();
            memAntes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            switch (i) {
                case 1:
                    for (int j = 0; j < 10; j++) {
                        Crono.start();
                        controller.ids_negocios_nao_avaliados();
                        time = Crono.stop();
                        somaT += time;
                        if (maxT < time) maxT = time;
                        if (minT > time) minT = time;
                    }
                    memDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                    sb.append(" Query 1 (10x) |");
                    sb.append(" Máximo: " + maxT);
                    sb.append(" | Médio: " + somaT / 10);
                    sb.append(" | Mínimo: " + minT);
                    sb.append(" | Memoria: " + (memDepois - memAntes));
                    break;
                case 2:
                    int execs2 = 0;

                    for (int monthActual = 1; monthActual <= 12; monthActual++) {
                        for (int yearActual = 2005; yearActual <= 2022; yearActual++) {
                            execs2++;
                            Crono.start();
                            controller.numeros_users_reviews_mes_especifico(monthActual, yearActual);
                            time = Crono.stop();
                            somaT += time;
                            if (maxT < time) {
                                monthMax = monthActual;
                                yearMax = yearActual;
                                maxT = time;
                            }
                            if(minT > time){
                                monthMin = monthActual;
                                yearMin = yearActual;
                                minT = time;
                            }
                        }
                    }
                    memDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                    sb.append(" Query 2       |");
                    sb.append(" Máximo: " + maxT + " Mês/Ano " + yearMax+"/"+monthMax);
                    sb.append(" | Médio: " + somaT / execs2);
                    sb.append(" | Mínimo: " + minT + " Mês/Ano " + yearMin+"/"+monthMin);
                    sb.append(" | Memoria: " + (memDepois - memAntes));
                    break;
                case 3:
                    for (String t : model.getAllUsers()) {
                        Crono.start();
                        try {
                            controller.info_mensal_user(t);
                        } catch (NaoExisteException e) {
                            System.out.println(e.getMessage());
                        }
                        time = Crono.stop();
                        if (maxT < time) {
                            maxT = time;
                            sMax = t;
                        }
                    }
                    memDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                    sb.append(" Query 3 (10x) |");
                    sb.append(" Máximo: " + maxT);
                    sb.append(" | Médio: " + somaT / 10);
                    sb.append(" | Mínimo: " + minT);
                    sb.append(" | User mais lento: " + sMax);
                    sb.append(" | Memoria: " + (memDepois - memAntes));
                    break;
                case 4:
                    for (String t : model.getAllBusiness()) {
                        Crono.start();
                        try {
                            controller.info_mensal_bus(t);
                        } catch (NaoExisteException e) {
                            System.out.println(e.getMessage());
                        }
                        time = Crono.stop();
                        if (maxT < time) {
                            maxT = time;
                            sMax = t;
                        }
                    }
                    memDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                    sb.append(" Query 4 (10x) |");
                    sb.append(" Máximo: " + maxT);
                    sb.append(" | Médio: " + somaT / 10);
                    sb.append(" | Mínimo: " + minT);
                    sb.append(" | Business mais lento: " + sMax);
                    sb.append(" | Memoria: " + (memDepois - memAntes));
                    break;
                case 5:
                    for (String t : model.getAllUsers()) {
                        Crono.start();
                        try {
                            controller.top_negocios_avaliados_por_user(t);
                        } catch (NaoExisteException e) {
                            System.out.println(e.getMessage());
                        }
                        time = Crono.stop();
                        if (maxT < time) {
                            maxT = time;
                            sMax = t;
                        }
                    }
                    memDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                    sb.append(" Query 5       | O user mais lento foi " + sMax + " | Demorou " + maxT);
                    sb.append(" | Memoria: " + (memDepois - memAntes));
                    break;
                case 6:
                    for (int j = 0; j < 10; j++) {
                        Crono.start();
                        try {
                            controller.negocios_mais_avaliados_por_ano(20);
                        } catch (NaoExisteException e) {
                            System.out.println(e.getMessage());
                        }
                        time = Crono.stop();
                        somaT += time;
                        if (maxT < time) maxT = time;
                        if (minT > time) minT = time;
                    }
                    memDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                    sb.append(" Query 6 (10x) |");
                    sb.append(" Máximo: " + maxT);
                    sb.append(" | Médio: " + somaT / 10);
                    sb.append(" | Mínimo: " + minT);
                    sb.append(" | Memoria: " + (memDepois - memAntes));
                    break;
                case 7:
                    for (int j = 0; j < 10; j++) {
                        Crono.start();
                        try {
                            controller.negocios_top_3_por_cidade();
                        } catch (NaoExisteException e) {
                            System.out.println(e.getMessage());
                        }
                        time = Crono.stop();
                        somaT += time;
                        if (maxT < time) maxT = time;
                        if (minT > time) minT = time;
                    }
                    memDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                    sb.append(" Query 7 (10x) |");
                    sb.append(" Máximo: " + maxT);
                    sb.append(" | Médio: " + somaT / 10);
                    sb.append(" | Mínimo: " + minT);
                    sb.append(" | Memoria: " + (memDepois - memAntes));
                    break;
                case 8:
                    for (int j = 0; j < 10; j++) {
                        Crono.start();
                        try {
                            controller.top_x_users_gest_reviews(147);
                        } catch (NaoExisteException e) {
                            System.out.println(e.getMessage());
                        }
                        time = Crono.stop();
                        somaT += time;
                        if (maxT < time) maxT = time;
                        if (minT > time) minT = time;
                    }
                    memDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                    sb.append(" Query 8 (10x) |");
                    sb.append(" Máximo: " + maxT);
                    sb.append(" | Médio: " + somaT / 10);
                    sb.append(" | Mínimo: " + minT);
                    sb.append(" | Memoria: " + (memDepois - memAntes));
                    break;
                case 9:
                    for (String s : model.getAllBusiness()) {
                        Crono.start();
                        try {
                            controller.users_com_mais_reviews(s);
                        } catch (NaoExisteException e) {
                            System.out.println(e.getMessage());
                        }
                        time = Crono.stop();
                        if (maxT < time) {
                            maxT = time;
                            sMax = s;
                        }
                    }
                    memDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                    sb.append(" Query 9       | O Negócio mais lento foi " + sMax + " | Demorou " + maxT);
                    sb.append(" | Memoria: " + (memDepois - memAntes));
                    break;
                case 10:
                    for (int j = 0; j < 10; j++) {
                        Crono.start();
                        try {
                            controller.media_negocio_por_estado_cidade();
                        } catch (NaoExisteException e) {
                            System.out.println(e.getMessage());
                        }
                        time = Crono.stop();
                        somaT += time;
                        if (maxT < time) maxT = time;
                        if (minT > time) minT = time;
                    }
                    memDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                    sb.append(" Query 10 (10x)|");
                    sb.append(" Máximo: " + maxT);
                    sb.append(" | Médio: " + somaT / 10);
                    sb.append(" | Mínimo: " + minT);
                    sb.append(" | Memoria: " + (memDepois - memAntes));
                    break;
            }
            return sb.toString();
        }
}


