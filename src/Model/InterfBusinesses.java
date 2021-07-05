package Model;

import Exceptions.LinhaInvalidaException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface InterfBusinesses extends Serializable {
    int campos_business = 5;
    String getBusiness_id();
    InterfBusinesses clone();
    void update_info_from_review(float review_stars,int ano,String review_id);
    boolean foi_avaliado();
    String getCity();
    String getState();
    String getName();
    int getNum_reviews();
    float media_stars();
    Map<Integer, List<String>> getReviews_por_ano();

    static boolean parse_valido(String input){
        int count = 0;

        for(int i=0; i < input.length(); i++)
        {
            if(input.charAt(i) == ';')
                count++;
        }
        return (count == (InterfBusinesses.campos_business-1));
    }
    static InterfBusinesses parse(String input) throws LinhaInvalidaException {
        String[] campos = input.split(";");
        if(campos.length == (InterfBusinesses.campos_business))
            return new Business(campos[0],campos[1],campos[2]
                    ,campos[3],campos[4],0,new HashMap<>());
        else return new Business(campos[0],campos[1],campos[2]
                ,campos[3],"",0,new HashMap<>());
    }
}
