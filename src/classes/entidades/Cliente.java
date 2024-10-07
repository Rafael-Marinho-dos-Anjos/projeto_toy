package src.classes.entidades;
import src.interfaces.*;

public class Cliente implements EntidadeInterface {
    // classe que implementa a entidade do banco de dados Cliente

    private String tableName = "tab_cliente";
    private int id;
    private String nome;
    private String nascimento;
    private String contato;

    public Cliente(int id, String nome, String nascimento, String contato) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
        this.contato = contato;
    }

    @Override
    public String getTableName() { return this.tableName; }

    @Override
    public String[] getColumnNames() {
        String[] colNames = {
            "id_cliente",
            "nome",
            "data_nascimento",
            "contato"
        };

        return colNames;
    }

    @Override
    public String[] getFormatedValues() {
        String[] values = new String[4];

        values[0] = String.valueOf(this.id);
        values[1] = "'" + this.nome + "'";
        values[2] = "'" + this.nascimento + "'";
        values[3] = "'" + this.contato + "'";

        return values;
    }

    @Override
    public String[] getPrimaryKeyColumns() {
        String[] colNames = {"id_cliente"};

        return colNames;
    }

    @Override
    public String[] getPrimaryKeyValues() {
        String[] values = new String[1];

        values[0] = String.valueOf(this.id);

        return values;
    }

    // GETTERS AND SETTERS
    public int getId () { return this.id; }
    public String getNome () { return this.nome; }
    public String getNascimento () { return this.nascimento; }
    public String getContato () { return this.contato; }

    public void setNome(String name) { this.nome = name; }
    public void setNascimento(String nascimento) { this.nascimento = nascimento; }
    public void setContato(String contato) { this.contato = contato; }
}