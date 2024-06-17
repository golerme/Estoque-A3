package estoque.main;

import java.awt.EventQueue;

import estoque.login.LoginView;

public class EstoqueMain {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginView loginView = new LoginView();
                    loginView.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
