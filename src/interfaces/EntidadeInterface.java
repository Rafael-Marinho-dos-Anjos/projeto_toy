package src.interfaces;

public interface EntidadeInterface {
    // Interface que implementa o comportamento de uma classe entidade do banco de dados

    // Método que retorna o nome da tabela
    public String getTableName();

    // Método que retorna os nomes de todas as colunas da tabela
    public String[] getColumnNames();

    // Método que retorna todos os valores formatados (convertidos para String e adicionado aspas simples quando necessário)
    // IMPORTANTE: OS VALORES DEVEM SER INFORMADOS NA MESMA ORDEM DAS COLUNAS DO MÉTODO ANTERIOR
    public String[] getFormatedValues();

    // Método que informa quais colunas são chave primária da tabela
    public String[] getPrimaryKeyColumns();

    // Método que informa os valores das colunas chave
    public String[] getPrimaryKeyValues();
}