import java.sql.SQLException;
import java.sql.ResultSet;
import src.classes.entidades.Cliente;
import src.classes.entidades.Item;
import src.data_access.DAO;

public class MainClass {
    public static void main (String[] args) throws SQLException, ClassNotFoundException {
        // DAO dao = DAO.getInstance();
        DAO dao = DAO.getInstance();

        String[] descricoes = {
            "sanduba",
            "refri",
            "batata"
        };
        float[] valores = {
            (float) 10.0,
            (float) 7.50,
            (float) 5.99
        };

        for (int i = 0; i < 3; i++) {
            Item item = new Item(
                0,
                descricoes[i],
                valores[i]
            );

            String query = DAO.queryInsertAutoIncrement(item);
            dao.executeQueryNR(query);
        }
    }
}
