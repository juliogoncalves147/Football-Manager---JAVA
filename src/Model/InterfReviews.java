package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface InterfReviews extends Serializable {
    int campos_review = 9;

    String getReview_id();
    String getBusiness_id();
    String getUser_id();
    float getStars();
    int getFunny();
    int getUseful();
    int getCool();
    int getMonth();
    int getYear();
    InterfReviews clone();

    static InterfReviews parse(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String[] campos = input.split(";",InterfReviews.campos_review);
        return new Review(campos[0], campos[1], campos[2]
                , Float.parseFloat(campos[3]), Integer.parseInt(campos[4]), Integer.parseInt(campos[5])
                , Integer.parseInt(campos[6]), LocalDateTime.parse(campos[7],formatter), campos[8]);

    }
    /**
     * Verifica se review é válida
     * @param input Linha de Texto
     * @return Resultado
     */
    static boolean parse_valido(String input){
        int count = 0;

        for(int i=0; i < input.length(); i++)
        {
            if(input.charAt(i) == ';')
                count++;
        }
        return (count >= ((InterfReviews.campos_review)-1));
    }
}
