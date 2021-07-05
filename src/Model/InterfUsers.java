package Model;

import Exceptions.LinhaInvalidaException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public interface InterfUsers extends Serializable {
    int campos_user = 3;
    int campos_user_no_friends = 2;

    String getUser_id();
    InterfUsers clone();
    void add_review_to_list(String rev_id);
    boolean fez_reviews();
    List<String> getReviews_id();
    static InterfUsers parse(String input) throws LinhaInvalidaException {
        int aux = 0;
        int last_char = 0;
        for(;(last_char < input.length()) && aux != 2 ; last_char++)
            if((input.charAt(last_char)) == ';')
                aux++;

            String user_ignoring_friends = input.substring(0,last_char-1);
            String[] campos = user_ignoring_friends.split(";",campos_user_no_friends);
            return new User(campos[0],campos[1],"",new ArrayList<>());
    }
    static InterfUsers parse_with_friends(String input) throws LinhaInvalidaException{
        String[] partes = input.split(";");
        return new User(partes[0],partes[1],partes[2],new ArrayList<>());
    }
    static boolean parse_valido(String input){
        int count = 0;
        for(int i=0; i < input.length() && count != (InterfUsers.campos_user-1); i++)
        {    if(input.charAt(i) == ';')
            count++;
        }
        return count == (InterfUsers.campos_user-1);
    }
}
