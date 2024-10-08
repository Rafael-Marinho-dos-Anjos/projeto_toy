package src.classes.entidades;

import src.interfaces.EntidadeInterface;

public class Item implements EntidadeInterface {
    private String tablename = "tab_item";
    private int id;
    private String descricao;
    private float valor;

    public Item (int id, String descricao, float valor) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
    }

    @Override
    public String getTableName() { return this.tablename; }

    @Override
    public String[] getPrimaryKeyColumns() {
        String[] columns = {"id_item"};

        return columns;
    }

    @Override
    public String[] getPrimaryKeyValues() {
        String[] values = new String[1];

        values[0] = String.valueOf(this.id);

        return values;
    }

    @Override
    public String[] getColumnNames() {
        String[] columns = {
            "id_item",
            "descricao",
            "valor_und"
        };

        return columns;
    }

    @Override
    public String[] getFormatedValues() {
        String[] values = new String[3];

        values[0] = String.valueOf(this.id);
        values[1] = "'" + this.descricao + "'";
        values[2] = String.valueOf(this.valor);

        return values;
    }

    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setValor(float valor) { this.valor = valor; }

    public int getId() { return this.id; }
    public String getDescricao() { return this.descricao; }
    public float getValor() { return this.valor; }
}