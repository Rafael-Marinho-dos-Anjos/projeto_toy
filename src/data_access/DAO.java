package src.data_access;
import src.interfaces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.sql.*;

public class DAO {
    // Classe responsável por manipular o banco de dados
    // a partir de objetos do programa

    Connection con;
    String database = "bd_entregas";
    String user = "root";
    String psswrd = "5pror3t2";
    static DAO instance = null;

    public DAO() throws SQLException, ClassNotFoundException {
        if (instance != null) {
            // Obrigatoriedade do uso do método getInstance
            return ;
        }

        Class.forName("com.mysql.cj.jdbc.Driver");
        this.con = DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/" + this.database,
            this.user,
            this.psswrd);
    }

    public static DAO getInstance() throws SQLException, ClassNotFoundException {
        // Padrão singleton
        if (instance == null) {
            instance = new DAO();
        }

        return instance;
    }

    public ResultSet executeQuery(String query) throws SQLException {
        // Executar queries com retorno (SELECT)
        try {
            Statement stmt = con.createStatement();  
            ResultSet res = stmt.executeQuery(query);

            return res;
        }
        finally {}
    }

    public void executeQueryNR(String query) throws SQLException {
        // Executar queries sem retorno(INSERT, UPDATE, DELETE)
        try {
            Statement stmt = con.createStatement();  
            stmt.execute(query);
        }
        finally {}
    }

    public static String queryInsert(EntidadeInterface instancia) {
        // INSERT INTO tableName (col1, col2, ...) VALUES (val1, val2, ...);
        StringBuilder query = new StringBuilder();

        query.append("INSERT INTO ");
        query.append(instancia.getTableName());
        query.append(" (");

        boolean first = true;
        for (String col: instancia.getColumnNames()) {
            if (first) { first = false; }
            else { query.append(", "); }

            query.append(col);
        }

        query.append(") VALUES (");

        first = true;
        for (String val: instancia.getFormatedValues()) {
            if (first) { first = false; }
            else { query.append(", "); }

            query.append(val);
        }

        query.append(");");

        return query.toString();
    }

    public static String queryInsertAutoIncrement(EntidadeInterface instancia) {
        // INSERT INTO tableName (col1, col2, ...) VALUES (val1, val2, ...);
        StringBuilder query = new StringBuilder();

        query.append("INSERT INTO ");
        query.append(instancia.getTableName());
        query.append(" (");

        String[] key = instancia.getPrimaryKeyColumns();
        ArrayList<Integer> key_indexes = new ArrayList<Integer>();

        boolean first = true;
        int key_index = 0;
        for (String col: instancia.getColumnNames()) {
            boolean skip = false;

            for (String key_col: key) {
                if (col == key_col) {
                    skip = true;
                    key_indexes.add(key_index);
                }
            }

            if (!skip) {
                if (first) { first = false; }
                else { query.append(", "); }

                query.append(col);
            }

            key_index++;
        }

        query.append(") VALUES (");

        first = true;
        int col_id = 0;
        for (String val: instancia.getFormatedValues()) {
            boolean skip = false;
            if (key_indexes.contains(col_id)) {skip = true;}

            if (!skip) {
                if (first) { first = false; }
                else { query.append(", "); }

                query.append(val);
            }

            col_id++;
        }

        query.append(");");

        return query.toString();
    }

    public static String queryUpdate(EntidadeInterface instancia) {
        // UPDATE tableName SET col1 = val1, col2 = val2, ... WHERE condition;
        StringBuilder query = new StringBuilder();
        String[] pk = instancia.getPrimaryKeyColumns();
        String[] pkVals = instancia.getPrimaryKeyValues();

        query.append("UPDATE ");
        query.append(instancia.getTableName());
        query.append(" SET ");

        String[] columns = instancia.getColumnNames();
        String[] vals = instancia.getFormatedValues();

        boolean first = true;
        for (int i = 0; i < columns.length; i++) {
            if (!Arrays.asList(pk).contains(columns[i])) {
                if (first) { first = false; }
                else { query.append(", "); }

                query.append(columns[i]);
                query.append(" = ");
                query.append(vals[i]);
            }
        }

        query.append(" WHERE ");
        
        first = true;
        for (int i = 0; i < pk.length; i++) {
            if (first) { first = false; }
            else { query.append(" AND "); }

            query.append(pk[i]);
            query.append(" = ");
            query.append(pkVals[i]);
        }

        query.append(";");

        return query.toString();
    }

    public static String queryDelete(EntidadeInterface instancia) {
        // DELETE tableName WHERE condicao;
        StringBuilder query = new StringBuilder();

        String[] pk = instancia.getPrimaryKeyColumns();
        String[] pkVals = instancia.getPrimaryKeyValues();

        query.append("DELETE FROM ");
        query.append(instancia.getTableName());
        query.append(" WHERE ");

        boolean first = true;
        for (int i = 0; i < pk.length; i++) {
            if (first) { first = false; }
            else { query.append(" AND "); }

            query.append(pk[i]);
            query.append(" = ");
            query.append(pkVals[i]);
        }

        query.append(";");

        return query.toString();
    }

    public static String querySelect(EntidadeInterface instancia) {
        // O parâmetro instancia nesse exemplo é um objeto com apenas a chave primária preenchida
        // Exemplo instancia = Aluno(1, null, null)

        // SELECT col1, col2, ... FROM tableName WHERE condicao;
        StringBuilder query = new StringBuilder();
        String[] pk = instancia.getPrimaryKeyColumns();
        String[] pkVals = instancia.getPrimaryKeyValues();

        query.append("SELECT ");

        boolean first = true;
        for (String col: instancia.getColumnNames()) {
            if (first) { first = false; }
            else {query.append(", "); }

            query.append(col);
        }

        query.append(" FROM ");
        query.append(instancia.getTableName());
        query.append(" WHERE ");

        first = true;
        for (int i = 0; i < pk.length; i++) {
            if (first) { first = false; }
            else { query.append(" AND "); }

            query.append(pk[i]);
            query.append(" = ");
            query.append(pkVals[i]);
        }

        query.append(";");

        return query.toString();
    }

    public static String querySelectEquals(EntidadeInterface instancia, String coluna, String valor) {
        // O parâmetro instancia nesse exemplo é um objeto qualquer da tabela a ser pesquisada

        // SELECT col1, col2, ... FROM tableName WHERE coluna = 'valor';
        StringBuilder query = new StringBuilder();

        query.append("SELECT ");

        boolean first = true;
        for (String col: instancia.getColumnNames()) {
            if (first) { first = false; }
            else {query.append(", "); }

            query.append(col);
        }

        query.append(" FROM ");
        query.append(instancia.getTableName());
        query.append(" WHERE ");
        query.append(coluna);
        query.append(" = '");
        query.append(valor);
        query.append("';");

        return query.toString();
    }

    public static String querySelectEquals(EntidadeInterface instancia, String coluna, float valor) {
        // O parâmetro instancia nesse exemplo é um objeto qualquer da tabela a ser pesquisada

        // SELECT col1, col2, ... FROM tableName WHERE coluna = valor;
        StringBuilder query = new StringBuilder();

        query.append("SELECT ");

        boolean first = true;
        for (String col: instancia.getColumnNames()) {
            if (first) { first = false; }
            else {query.append(", "); }

            query.append(col);
        }

        query.append(" FROM ");
        query.append(instancia.getTableName());
        query.append(" WHERE ");
        query.append(coluna);
        query.append(" = ");
        query.append(String.valueOf(valor));
        query.append(";");

        return query.toString();
    }

    public static String querySelectHigherThan(EntidadeInterface instancia, String coluna, float valor) {
        // O parâmetro instancia nesse exemplo é um objeto qualquer da tabela a ser pesquisada

        // SELECT col1, col2, ... FROM tableName WHERE coluna > valor;
        StringBuilder query = new StringBuilder();

        query.append("SELECT ");

        boolean first = true;
        for (String col: instancia.getColumnNames()) {
            if (first) { first = false; }
            else {query.append(", "); }

            query.append(col);
        }

        query.append(" FROM ");
        query.append(instancia.getTableName());
        query.append(" WHERE ");
        query.append(coluna);
        query.append(" > ");
        query.append(String.valueOf(valor));
        query.append(";");

        return query.toString();
    }

    public static String querySelectHigherEqual(EntidadeInterface instancia, String coluna, float valor) {
        // O parâmetro instancia nesse exemplo é um objeto qualquer da tabela a ser pesquisada

        // SELECT col1, col2, ... FROM tableName WHERE coluna >= valor;
        StringBuilder query = new StringBuilder();

        query.append("SELECT ");

        boolean first = true;
        for (String col: instancia.getColumnNames()) {
            if (first) { first = false; }
            else {query.append(", "); }

            query.append(col);
        }

        query.append(" FROM ");
        query.append(instancia.getTableName());
        query.append(" WHERE ");
        query.append(coluna);
        query.append(" >= ");
        query.append(String.valueOf(valor));
        query.append(";");

        return query.toString();
    }

    public static String querySelectLowerThan(EntidadeInterface instancia, String coluna, float valor) {
        // O parâmetro instancia nesse exemplo é um objeto qualquer da tabela a ser pesquisada

        // SELECT col1, col2, ... FROM tableName WHERE coluna < valor;
        StringBuilder query = new StringBuilder();

        query.append("SELECT ");

        boolean first = true;
        for (String col: instancia.getColumnNames()) {
            if (first) { first = false; }
            else {query.append(", "); }

            query.append(col);
        }

        query.append(" FROM ");
        query.append(instancia.getTableName());
        query.append(" WHERE ");
        query.append(coluna);
        query.append(" < ");
        query.append(String.valueOf(valor));
        query.append(";");

        return query.toString();
    }

    public static String querySelectLowerEqual(EntidadeInterface instancia, String coluna, float valor) {
        // O parâmetro instancia nesse exemplo é um objeto qualquer da tabela a ser pesquisada

        // SELECT col1, col2, ... FROM tableName WHERE coluna <= valor;
        StringBuilder query = new StringBuilder();

        query.append("SELECT ");

        boolean first = true;
        for (String col: instancia.getColumnNames()) {
            if (first) { first = false; }
            else {query.append(", "); }

            query.append(col);
        }

        query.append(" FROM ");
        query.append(instancia.getTableName());
        query.append(" WHERE ");
        query.append(coluna);
        query.append(" <= ");
        query.append(String.valueOf(valor));
        query.append(";");

        return query.toString();
    }

    public static String querySelectLike(EntidadeInterface instancia, String coluna, String valor) {
        // O parâmetro instancia nesse exemplo é um objeto qualquer da tabela a ser pesquisada

        // SELECT col1, col2, ... FROM tableName WHERE coluna LIKE 'valor';
        StringBuilder query = new StringBuilder();

        query.append("SELECT ");

        boolean first = true;
        for (String col: instancia.getColumnNames()) {
            if (first) { first = false; }
            else {query.append(", "); }

            query.append(col);
        }

        query.append(" FROM ");
        query.append(instancia.getTableName());
        query.append(" WHERE ");
        query.append(coluna);
        query.append(" LIKE '");
        query.append(String.valueOf(valor));
        query.append("';");

        return query.toString();
    }
}