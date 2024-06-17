package estoque.consulta;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class ConsultaEstoqueView extends JFrame {
private JTable table;
private DefaultTableModel tableModel;

public ConsultaEstoqueView() {
    setTitle("Consulta de Estoque");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    tableModel = new DefaultTableModel();
    tableModel.addColumn("Código");
    tableModel.addColumn("Nome");
    tableModel.addColumn("Descrição");
    tableModel.addColumn("Quantidade");

    table = new JTable(tableModel);
    add(new JScrollPane(table), BorderLayout.CENTER);

    carregarDados();
}

private void carregarDados() {
    try {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estoque_pecas", "root", "");
        String sql = "SELECT * FROM Peca";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            row.add(rs.getInt("codigo"));
            row.add(rs.getString("nome"));
            row.add(rs.getString("descricao"));
            row.add(rs.getInt("quantidade"));
            tableModel.addRow(row);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


public static void main(String[] args) {
    ConsultaEstoqueView consultaEstoqueView = new ConsultaEstoqueView();
    consultaEstoqueView.setVisible(true);
}
}
