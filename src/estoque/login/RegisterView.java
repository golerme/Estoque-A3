package estoque.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

@SuppressWarnings("serial")
public class RegisterView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public RegisterView() {
        setTitle("Registro");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Usuário:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Senha:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton registerButton = new JButton("Registrar");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
        add(registerButton);

        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginView().setVisible(true);
                dispose();
            }
        });
        add(backButton);
    }

    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estoque_pecas", "root", "");
            String sql = "INSERT INTO Usuario (username, password, role) VALUES (?, ?, 'cliente')";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registrado com sucesso!");
            new LoginView().setVisible(true);
            dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RegisterView registerView = new RegisterView();
        registerView.setVisible(true);
    }
}
