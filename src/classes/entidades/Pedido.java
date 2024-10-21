package src.classes.entidades;

import src.interfaces.EntidadeInterface;

public class Pedido implements EntidadeInterface {

    private String tableName = "tab_pedido";
    private int id;
    private int id_cliente;
    private int id_entregador;
    private String data_pedido;
    private String data_finalizado;
    private String endereco;

    public Pedido (
        int id,
        int id_cliente,
        int id_entregador,
        String data_pedido,
        String data_finalizado,
        String endereco
    ) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_entregador = id_entregador;
        this.data_pedido = data_pedido;
        this.data_finalizado = data_finalizado;
        this.endereco  = endereco;
    }

    public Pedido (
        int id,
        int id_cliente,
        String data_pedido,
        String endereco
    ) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_entregador = 0;
        this.data_pedido = data_pedido;
        this.data_finalizado = "NULL";
        this.endereco  = endereco;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    @Override
    public String[] getColumnNames() {
        String[] columns = {
            "id_pedido",
            "id_cliente",
            "id_entregador",
            "data_pedido",
            "data_finalizado",
            "endereco"
        };

        return columns;
    }

    @Override
    public String[] getFormatedValues() {
        String[] values = new String[6];

        values [0] = String.valueOf(this.id);
        values [1] = String.valueOf(this.id_cliente);

        if (this.id_entregador == 0) {
            values [2] = "NULL";
        }
        else {
            values [2] = String.valueOf(this.id_entregador);
        }
        
        values [3] = "'" + this.data_pedido + "'";
        values [4] = this.data_finalizado;
        values [5] = "'" + this.endereco + "'";

        return values;
    }

    @Override
    public String[] getPrimaryKeyColumns() {
        String[] columns = {"id_pedido"};

        return columns;
    }

    @Override
    public String[] getPrimaryKeyValues() {
        String[] values = new String[1];

        values[0] = String.valueOf(this.id);

        return values;
    }
    
    public void setDataFinalizado (String data) { this.data_finalizado = data; }
    public void setEntregador (Entregador entregador) { this.id_entregador = entregador.getId(); }
}
