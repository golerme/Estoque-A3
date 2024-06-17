package estoque.faturamento;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class FaturamentoView extends JFrame {
    private JTextField osIdField;
    private JTextField valorServicoField;
    private JTextField valorPecasField;

    public FaturamentoView() {
        setTitle("Controle de Faturamento");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("ID da OS:"));
        osIdField = new JTextField();
        add(osIdField);

        add(new JLabel("Valor do Serviço:"));
        valorServicoField = new JTextField();
        add(valorServicoField);

        add(new JLabel("Valor das Peças:"));
        valorPecasField = new JTextField();
        add(valorPecasField);

        JButton faturarButton = new JButton("Faturar");
        faturarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int osId = Integer.parseInt(osIdField.getText());
                double valorServico = Double.parseDouble(valorServicoField.getText());
                double valorPecas = Double.parseDouble(valorPecasField.getText());

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estoque_pecas", "root", "");
                    String sql = "UPDATE OrdemServico SET valor_servico = ?, valor_pecas = ?, status = 'concluida' WHERE codigo = ?";
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    stmt.setDouble(1, valorServico);
                    stmt.setDouble(2, valorPecas);
                    stmt.setInt(3, osId);
                    stmt.executeUpdate();

                    atualizarEstoque(osId, connection);

                    JOptionPane.showMessageDialog(null, "Faturamento registrado com sucesso!");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(faturarButton);
    
        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); 
            }
        });
        add(backButton);
        }

    private void atualizarEstoque(int osId, Connection connection) throws SQLException {
        String sql = "SELECT peca_id, quantidade FROM PecaOrdemServico WHERE os_id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, osId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int pecaId = rs.getInt("peca_id");
            int quantidade = rs.getInt("quantidade");

            String sqlUpdate = "UPDATE Peca SET quantidade = quantidade - ? WHERE codigo = ?";
            PreparedStatement stmtUpdate = connection.prepareStatement(sqlUpdate);
            stmtUpdate.setInt(1, quantidade);
            stmtUpdate.setInt(2, pecaId);
            stmtUpdate.executeUpdate();
        }
    }

    public static void main(String[] args) {
        FaturamentoView faturamentoView = new FaturamentoView();
        faturamentoView.setVisible(true);
    }
}
