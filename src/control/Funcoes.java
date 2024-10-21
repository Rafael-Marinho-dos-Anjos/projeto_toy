package src.control;

import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import src.classes.entidades.Cliente;
import src.classes.entidades.Entregador;
import src.classes.entidades.Item;
import src.classes.entidades.Pedido;
import src.data_access.DAO;

public class Funcoes {
    
    public static Pedido realizarPedido(Cliente cliente, Item[] itens, String endereco, DAO dao) throws SQLException {
        // Data e hora atual
        String data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        
        // Criando um pedido novo
        Pedido pedido = new Pedido(
            0,
            cliente.getId(),
            data,
            endereco
        );

        // Contrução da query para verificar o id do pedido
        // A pesquisa consiste em verificar o id do pedido mais recente do cliente
        String colData = "data_pedido";
        String colIdCliente = "id_cliente";
        String queryId = "SELECT " + pedido.getPrimaryKeyColumns()[0] + " FROM " + pedido.getTableName() + " WHERE " + colIdCliente + " = " + pedido.getFormatedValues()[1] + " ORDER BY " + colData + " DESC LIMIT 1;";

        // Criando o novo pedido utilizando auto increment
        String query_pedido = DAO.queryInsertAutoIncrement(pedido);
        dao.executeQueryNR(query_pedido);

        // Verificando o id do pedido que foi inserido
        ResultSet res = dao.executeQuery(queryId);
        
        res.next();
        int id = res.getInt(1);
        
        // Criando um novo objeto pedido com o id  atualizado
        pedido = new Pedido(
            id,
            cliente.getId(),
            data,
            endereco
        );

        // Adicionando os itens ao pedido
        for (Item item : itens) {
            String query = "INSERT INTO rel_item_pedido VALUES (" + String.valueOf(pedido.getPrimaryKeyValues()[0]) + ", " + String.valueOf(item.getPrimaryKeyValues()[0]) + ");";
            dao.executeQueryNR(query);
        }

        return pedido;
    }

    public static float valorPedido(Pedido pedido, DAO dao) throws SQLException {
        String query = "SELECT SUM(i.valor_und) FROM\n" + //
                        "\ttab_pedido AS p\n" + //
                        "\t\tINNER JOIN\n" + //
                        "\trel_item_pedido AS r\n" + //
                        "\t\tON p.id_pedido = r.id_pedido\n" + //
                        "        INNER JOIN\n" + //
                        "\ttab_item AS i\n" + //
                        "\t\tON r.id_item = i.id_item\n" + //
                        "\tWHERE p.id_pedido = " + pedido.getPrimaryKeyValues()[0] + ";";
        
        ResultSet res = dao.executeQuery(query);

        res.next();

        float valor = res.getFloat(1);

        return valor;
    }

    public static Item[] cardapio(DAO dao) throws SQLException {
        String query = "SELECT * FROM tab_item;";
        ResultSet res = dao.executeQuery(query);

        ArrayList<Item> itensList = new ArrayList<>();

        while (res.next()) {
            Item item = new Item(
                res.getInt(1),
                res.getString(2),
                res.getFloat(3)
            );

            itensList.add(item);
        }

        Item[] itens = new Item[itensList.size()];

        for (int i = 0; i < itensList.size(); i++) {
            itens[i] = itensList.get(i);
        }

        return itens;
    }

    public static void finalizarPedido(Pedido pedido, DAO dao) throws SQLException {
        String data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        pedido.setDataFinalizado(data);

        String query = DAO.queryUpdate(pedido);
        dao.executeQueryNR(query);
    }

    public static void pedidoEmEntrega(Pedido pedido, Entregador entregador, DAO dao) throws SQLException {
        pedido.setEntregador(entregador);

        String query = DAO.queryUpdate(pedido);
        dao.executeQueryNR(query);
    }

    public static Pedido[] pedidosEmEspera(DAO dao) throws SQLException {
        String query = "SELECT * FROM tab_pedido WHERE id_entregador is NULL and data_finalizado is NULL;";
        ResultSet res = dao.executeQuery(query);

        ArrayList<Pedido> pedidosList = new ArrayList<>();

        while (res.next()) {
            Pedido pedido = new Pedido(
                res.getInt(1),
                res.getInt(2),
                res.getString(3),
                res.getString(4)
            );

            pedidosList.add(pedido);
        }

        Pedido[] pedidos = new Pedido[pedidosList.size()];

        for (int i = 0; i < pedidosList.size(); i++) {
            pedidos[i] = pedidosList.get(i);
        }

        return pedidos;
    }
}
