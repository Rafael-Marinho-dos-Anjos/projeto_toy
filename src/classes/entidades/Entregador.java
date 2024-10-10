package src.classes.entidades;

import src.interfaces.EntidadeInterface;

public class Entregador implements EntidadeInterface {

    private String tableName = "tab_entregador";
    private int id;
    private String nome;
    private String documento;
    private String placa;

    public Entregador (
        int id,
        String nome,
        String documento,
        String placa
    ) {
        this.id = id;
        this.nome = nome;
        this.documento = documento;
        this.placa = placa;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    @Override
    public String[] getColumnNames() {
        String[] columns = {
            "id_entregador",
            "nome",
            "documento",
            "placa_veiculo"
        };

        return columns;
    }

    @Override
    public String[] getFormatedValues() {
        String[] values = new String[4];

        values[0] = String.valueOf(this.id);
        values[1] = "'" + this.nome + "'";
        values[2] = "'" + this.documento + "'";
        values[3] = "'" + this.placa + "'";

        return values;
    }

    @Override
    public String[] getPrimaryKeyColumns() {
        String[] columns = {"id_entregador"};

        return columns;
    }

    @Override
    public String[] getPrimaryKeyValues() {
        String[] values = new String[1];

        values[0] = String.valueOf(this.id);

        return values;
    }

    public void setNome (String nome) { this.nome = nome; }
    public void setDocumento (String documento) { this.documento = documento; }
    public void setPlaca (String placa) { this.placa = placa; }

    public int getId () { return this.id; }
    public String getNome () { return this.nome; }
    public String getDocumento () { return this.documento; }
    public String getPlaca () { return this.placa; }
}
