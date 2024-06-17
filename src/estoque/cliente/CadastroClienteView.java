package estoque.cliente;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class CadastroClienteView extends JFrame {
    private JTextField nomeField;
    private JTextField enderecoField;
    private JTextField telefoneField;

    public CadastroClienteView() {
        setTitle("Cadastro de Cliente");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Nome:"));
        nomeField = new JTextField();
        add(nomeField);

        add(new JLabel("Endereço:"));
        enderecoField = new JTextField();
        add(enderecoField);

        add(new JLabel("Telefone:"));
        telefoneField = new JTextField();
        add(telefoneField);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cliente cliente = new Cliente();
                cliente.setNome(nomeField.getText());
                cliente.setEndereco(enderecoField.getText());
                cliente.setTelefone(telefoneField.getText());

                ClienteDAO dao = new ClienteDAO();
                dao.adicionarCliente(cliente);

                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
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
        CadastroClienteView cadastroClienteView = new CadastroClienteView();
        cadastroClienteView.setVisible(true);
    }
}

class Cliente {
    private int id;
    private String nome;
    private String endereco;
    private String telefone;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

class ClienteDAO {
    private Connection connection;

    public ClienteDAO() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estoque_pecas", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adicionarCliente(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nome, endereco, telefone) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getTelefone());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
