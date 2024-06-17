package estoque.pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

@SuppressWarnings("serial")
public class PedidoCompraView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField quantidadeField;
    private JTextField codigoField;

    public PedidoCompraView() {
        setTitle("Pedido de Compra");
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

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Código da Peça:"));
        codigoField = new JTextField();
        panel.add(codigoField);
        panel.add(new JLabel("Quantidade:"));
        quantidadeField = new JTextField();
        panel.add(quantidadeField);
        JButton adicionarButton = new JButton("Adicionar ao Pedido");
        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo = Integer.parseInt(codigoField.getText());
                int quantidade = Integer.parseInt(quantidadeField.getText());
                adicionarPecaAoPedido(codigo, quantidade);
            }
        });
        panel.add(adicionarButton);
        add(panel, BorderLayout.SOUTH);
    }
    
    

    private void adicionarPecaAoPedido(int codigo, int quantidade) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estoque_pecas", "root", "");
            String sql = "SELECT * FROM Peca WHERE codigo = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("codigo"));
                row.add(rs.getString("nome"));
                row.add(rs.getString("descricao"));
                row.add(quantidade);
                tableModel.addRow(row);
            } else {
                JOptionPane.showMessageDialog(null, "Peça não encontrada!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PedidoCompraView pedidoCompraView = new PedidoCompraView();
        pedidoCompraView.setVisible(true);
    }
}