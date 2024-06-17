package estoque.cadastro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class CadastroPecaView extends JFrame {
    private JTextField nomeField;
    private JTextField descricaoField;
    private JTextField quantidadeField;

    public CadastroPecaView() {
        setTitle("Cadastro de Peça");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Nome:"));
        nomeField = new JTextField();
        add(nomeField);

        add(new JLabel("Descrição:"));
        descricaoField = new JTextField();
        add(descricaoField);

        add(new JLabel("Quantidade:"));
        quantidadeField = new JTextField();
        add(quantidadeField);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Peca peca = new Peca();
                peca.setNome(nomeField.getText());
                peca.setDescricao(descricaoField.getText());
                peca.setQuantidade(Integer.parseInt(quantidadeField.getText()));

                PecaDAO dao = new PecaDAO();
                dao.adicionarPeca(peca);

                JOptionPane.showMessageDialog(null, "Peça cadastrada com sucesso!");
            }
        });
        add(cadastrarButton);

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
        CadastroPecaView cadastroPecaView = new CadastroPecaView();
        cadastroPecaView.setVisible(true);
    }
}

class Peca {
    private int codigo;
    private String nome;
    private String descricao;
    private int quantidade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
}

class PecaDAO {
    private Connection connection;

    public PecaDAO() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estoque_pecas", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adicionarPeca(Peca peca) {
        String sql = "INSERT INTO Peca (nome, descricao, quantidade) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, peca.getNome());
            stmt.setString(2, peca.getDescricao());
            stmt.setInt(3, peca.getQuantidade());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
