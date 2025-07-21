package com.outsera.worstmovie.converter;

import jakarta.persistence.AttributeConverter;

public class BooleanTypeConversor implements AttributeConverter<Boolean, String>  {
    /**
     * Converte o valor armazenado no atributo da entidade
     * na representação de dados a ser armazenada no banco de dados.
     *
     * @param attribute valor do atributo da entidade a ser convertido
     * @return os dados convertidos a serem armazenados na coluna do banco de dados
     */
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) {
            return "S";
        }
        return attribute ? "S" : "N";
    }

    /**
     * Converte os dados armazenados na coluna do banco de dados no valor
     * a ser armazenado no atributo da entidade.
     * Observe que é responsabilidade do escritor do conversor especificar
     * o tipo <code>dbData</code> correto para a coluna correspondente
     * para uso pelo driver JDBC: ou seja, não se espera que provedores
     * de persistência façam tal conversão de tipo.
     *
     * @param dbData os dados da coluna do banco de dados a serem convertidos
     * @return valor convertido a ser armazenado no atributo da entidade
     */
    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return true;
        }
        return "S".equals(dbData);
    }
}
