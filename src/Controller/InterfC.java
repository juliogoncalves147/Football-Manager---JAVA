package Controller;

import Auxiliar.*;
import Exceptions.NaoExisteException;

import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InterfC extends Serializable {
    /**
     * Carrega ficheiros
     * @param bf Ficheiro business
     * @param rf Ficheiro reviews
     * @param uf Ficheiro users
     * @throws IOException Erros IO
     */
    void carrega_ficheiros(String bf,String rf,String uf) throws IOException;

    /**
     * Obtém informação guardada nas estatísticas
     * @return Lista de Strings formatadas para table
     */
    List<String> info_estatisticas();

    /**
     * Dados para apresentar Query 1
     * @return Dados query 1
     */
    Set<String> ids_negocios_nao_avaliados();//QUERY 1

    /**
     * Dados para apresentar Query 2
     * @param month Mês da query
     * @param year Ano da query
     * @return Dados query 2
     */
    AbstractMap.SimpleEntry<Integer,Integer> numeros_users_reviews_mes_especifico(int month, int year); //QUERY 2

    /**
     * Dados para apresentar Query 3
     * @param user_id ID do user
     * @return Dados query 3
     * @throws NaoExisteException Caso user não exista
     */
    AuxTripleQuery3and4 info_mensal_user(String user_id) throws NaoExisteException; //QUERY 3

    /**
     * Dados para apresentar Query 4
     * @param bus_id ID do business
     * @return Dados query 4
     * @throws NaoExisteException Caso business não exista
     */
    AuxTripleQuery3and4 info_mensal_bus(String bus_id) throws NaoExisteException; //QUERY 4

    /**
     * Dados para apresentar Query 5
     * @param user_id ID do user
     * @return Dados query 5
     * @throws NaoExisteException Caso user não exista
     */
    Set<AuxPairIdQuant> top_negocios_avaliados_por_user(String user_id) throws NaoExisteException; //QUERY 5

    /**
     * Dados para apresentar Query 6
     * @param top_x Top negócios
     * @return Dados query 6
     * @throws NaoExisteException Caso haja erros
     */
    Map<Integer, BoundedTreeSet<AuxTripleIdQuantQuant>> negocios_mais_avaliados_por_ano(int top_x) throws NaoExisteException; //QUERY 6

    /**
     * Dados para apresentar Query 7
     * @return Dados query 7
     * @throws NaoExisteException Caso haja erros
     */
    Map<String,BoundedTreeSet<AuxPairIdQuant>> negocios_top_3_por_cidade() throws NaoExisteException; //QUERY 7

    /**
     * Dados para apresentar Query 8
     * @param top_x Top negócios
     * @return Dados query 8
     * @throws NaoExisteException Caso haja erros
     */
    BoundedTreeSet<AuxPairIdQuant> top_x_users_gest_reviews(int top_x) throws NaoExisteException; //QUERY 8

    /**
     * Dados para apresentar Query 9
     * @param bus_id ID do negócio
     * @return Dados query 9
     * @throws NaoExisteException Caso negócio não exista
     */
    Set<AuxTripleQuery9> users_com_mais_reviews(String bus_id) throws NaoExisteException; //QUERY 9

    /**
     * Dados para apresentar Query 10
     * @return Dados query 10
     * @throws NaoExisteException Caso haja erros
     */
    Map<String,Map<String, List<AuxPairIdFloat>>> media_negocio_por_estado_cidade() throws NaoExisteException; //QUERY 10

    /**
     * Guardar Ficheiro Em Binário
     * @param file_name Nome Ficheiro
     * @throws IOException Erro de IO
     */
    void saveBinaryFile(String file_name) throws IOException; //GUARDAR DADOS

    /**
     * Carregar Dados de Ficheiro Binário
     * @param file_name Nome Ficheiro
     * @throws IOException Erro de IO
     * @throws ClassNotFoundException Erro de Class
     */
    void loadBinaryFile(String file_name) throws IOException, ClassNotFoundException; //CARREGAR DADOS
}
