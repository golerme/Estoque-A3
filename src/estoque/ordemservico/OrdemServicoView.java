package estoque.ordemservico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

@SuppressWarnings("serial")
public class OrdemServicoView extends JFrame {
    private JTextField clienteIdField;
    private JTextArea descricaoArea;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField codigoPecaField;
    private JTextField quantidadeField;

    public OrdemServicoView() {
        setTitle("Abertura de Ordem de Serviço");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel clientePanel = new JPanel(new GridLayout(2, 2));
        clientePanel.add(new JLabel("ID do Cliente:"));
        clienteIdField = new JTextField();
        clientePanel.add(clienteIdField);
        clientePanel.add(new JLabel("Descrição do Serviço:"));
        descricaoArea = new JTextArea();
        clientePanel.add(descricaoArea);
        add(clientePanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Código da Peça");
        tableModel.addColumn("Quantidade");

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel pecaPanel = new JPanel(new GridLayout(3, 2));
        pecaPanel.add(new JLabel("Código da Peça:"));
        codigoPecaField = new JTextField();
        pecaPanel.add(codigoPecaField);
        pecaPanel.add(new JLabel("Quantidade:"));
        quantidadeField = new JTextField();
        pecaPanel.add(quantidadeField);
        JButton adicionarButton = new JButton("Adicionar Peça");
        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (codigoPecaField.getText().isEmpty() || quantidadeField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos da peça.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    int codigo = Integer.parseInt(codigoPecaField.getText());
                    int quantidade = Integer.parseInt(quantidadeField.getText());
                    adicionarPecaNaOS(codigo, quantidade);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Código da Peça e Quantidade devem ser números.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
           
            JButton backButton = new JButton("Voltar");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false); 
                }
            });
            add(backButton);
            }
        });
        pecaPanel.add(adicionarButton);
        add(pecaPanel, BorderLayout.SOUTH);

        JButton abrirOSButton = new JButton("Abrir OS");
        abrirOSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirOrdemServico();
            }
        });
        add(abrirOSButton, BorderLayout.SOUTH);
    }

    private void adicionarPecaNaOS(int codigo, int quantidade) {
        Vector<Object> row = new Vector<>();
        row.add(codigo);
        row.add(quantidade);
        tableModel.addRow(row);
        codigoPecaField.setText("");
        quantidadeField.setText("");
    }

    private void abrirOrdemServico() {
        String clienteIdText = clienteIdField.getText();
        String descricao = descricaoArea.getText();

        if (clienteIdText.isEmpty() || descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int clienteId = Integer.parseInt(clienteIdText);

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estoque_pecas", "root", "");
            String sql = "INSERT INTO OrdemServico (cliente_id, descricao, status) VALUES (?, ?, 'aberta')";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, clienteId);
            stmt.setString(2, descricao);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int osId = rs.getInt(1);
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    int codigoPeca = (int) tableModel.getValueAt(i, 0);
                    int quantidade = (int) tableModel.getValueAt(i, 1);

                    String sqlPeca = "INSERT INTO PecaOrdemServico (os_id, peca_id, quantidade) VALUES (?, ?, ?)";
                    PreparedStatement stmtPeca = connection.prepareStatement(sqlPeca);
                    stmtPeca.setInt(1, osId);
                    stmtPeca.setInt(2, codigoPeca);
                    stmtPeca.setInt(3, quantidade);
                    stmtPeca.executeUpdate();
                }
            }

            JOptionPane.showMessageDialog(this, "Ordem de Serviço aberta com sucesso!");
            limparCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID do Cliente deve ser um número.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir Ordem de Serviço: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        clienteIdField.setText("");
        descricaoArea.setText("");
        tableModel.setRowCount(0);
    }

    public static void main(String[] args) {
        OrdemServicoView ordemServicoView = new OrdemServicoView();
        ordemServicoView.setVisible(true);
    }
}
