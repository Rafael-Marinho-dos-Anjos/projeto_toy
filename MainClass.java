import java.sql.SQLException;

import src.classes.entidades.Item;
import src.classes.entidades.Pedido;
import src.data_access.DAO;
import src.control.Funcoes;

public class MainClass {
    public static void main (String[] args) throws SQLException, ClassNotFoundException {
        // DAO dao = DAO.getInstance();
        DAO dao = DAO.getInstance();

        // Cliente cliente = new Cliente(4, "Samuel", "2000-01-01", "contato");

        // // Lista de itens no pedido
        // Item[] itens = {
        //     new Item(23, "hamburger", (float) 10),
        //     new Item(23, "hamburger", (float) 10),
        //     new Item(23, "hamburger", (float) 10),
        //     new Item(24, "refrigerante", (float) 10)
        // };

        // // Criando pedido através da função feita para realizar pedido
        // Pedido pedido = realizarPedido(
        //     cliente,
        //     itens,
        //     "rua 01",
        //     dao
        // );

        // // Listando o conteúdo do pedido para print no terminal
        // String[] colunas = pedido.getColumnNames();
        // String[] valores = pedido.getFormatedValues();

        // for (int i = 0; i <= 5; i++) {
        //     System.out.println(colunas[i] + " -> " + valores[i]);
        // }

        // Pedido pedido = new Pedido(16, 0, null, null);
        // System.out.println(Funcoes.valorPedido(pedido, dao));

        Item[] itens = Funcoes.cardapio(dao);
        System.out.println();
    }
}
