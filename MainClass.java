import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.ResultSet;
import src.classes.entidades.Cliente;
import src.classes.entidades.Item;
import src.classes.entidades.Pedido;
import src.data_access.DAO;

public class MainClass {
    public static void main (String[] args) throws SQLException, ClassNotFoundException {
        // DAO dao = DAO.getInstance();
        DAO dao = DAO.getInstance();

        Cliente cliente = new Cliente(4, "Samuel", "2000-01-01", "contato");

        // Lista de itens no pedido
        Item[] itens = {
            new Item(23, "hamburger", (float) 10),
            new Item(24, "refrigerante", (float) 10)
        };

        // Criando pedido através da função feita para realizar pedido
        Pedido pedido = realizarPedido(
            cliente,
            itens,
            "rua 01",
            dao
        );

        // Listando o conteúdo do pedido para print no terminal
        String[] colunas = pedido.getColumnNames();
        String[] valores = pedido.getFormatedValues();

        for (int i = 0; i <= 5; i++) {
            System.out.println(colunas[i] + " -> " + valores[i]);
        }
    }

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
}
