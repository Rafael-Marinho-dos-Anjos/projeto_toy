import java.sql.SQLException;
import java.sql.ResultSet;
import src.classes.entidades.Cliente;
import src.data_access.DAO;

public class MainClass {
    public static void main (String[] args) throws SQLException, ClassNotFoundException {
        Cliente cliente = new Cliente(0, "Novo CLiente", "2000-01-01", "81 99999-9999");

        String query = DAO.queryInsertAutoIncrement(cliente);
        System.out.println(query);
    }
}
