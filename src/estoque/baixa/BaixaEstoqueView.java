package estoque.baixa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class BaixaEstoqueView extends JFrame {
    private JTextField codigoField;
    private JTextField quantidadeField;

    public BaixaEstoqueView() {
        setTitle("Baixa de Estoque");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Código da Peça:"));
        codigoField = new JTextField();
        add(codigoField);

        add(new JLabel("Quantidade:"));
        quantidadeField = new JTextField();
        add(quantidadeField);

        JButton baixarButton = new JButton("Baixar");
        baixarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo = Integer.parseInt(codigoField.getText());
                int quantidade = Integer.parseInt(quantidadeField.getText());

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estoque_pecas", "root", "");
                    String sql = "UPDATE Peca SET quantidade = quantidade - ? WHERE codigo = ?";
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    stmt.setInt(1, quantidade);
                    stmt.setInt(2, codigo);
                    stmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Baixa de estoque realizada com sucesso!");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(baixarButton);
    
    JButton backButton = new JButton("Voltar");
    backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false); 
        }
    });
    add(backButton);
    }

    public static void main(String[] args) {
        BaixaEstoqueView baixaEstoqueView = new BaixaEstoqueView();
        baixaEstoqueView.setVisible(true);
    }
}
