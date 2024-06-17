package estoque.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import estoque.cadastro.CadastroPecaView;
import estoque.consulta.ConsultaEstoqueView;
import estoque.baixa.BaixaEstoqueView;
import estoque.pedido.PedidoCompraView;
import estoque.ordemservico.OrdemServicoView;
import estoque.cliente.CadastroClienteView;

@SuppressWarnings("serial")
public class MainView extends JFrame {
    public MainView(String role) {
        setTitle("Sistema de Controle de Estoque e Ordens de Serviço");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        JButton cadastroPecasButton = new JButton("Cadastro de Peças");
        cadastroPecasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroPecaView().setVisible(true);
            }
        });

        JButton consultaEstoqueButton = new JButton("Consulta de Estoque");
        consultaEstoqueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaEstoqueView().setVisible(true);
            }
        });

        JButton baixaEstoqueButton = new JButton("Solicitação de Baixa de Estoque");
        baixaEstoqueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BaixaEstoqueView().setVisible(true);
            }
        });

        JButton pedidoCompraButton = new JButton("Criação de Pedido de Compras");
        pedidoCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PedidoCompraView().setVisible(true);
            }
        });

        JButton cadastroClientesButton = new JButton("Cadastro de Clientes");
        cadastroClientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroClienteView().setVisible(true);
            }
        });

        JButton ordemServicoButton = new JButton("Abertura de Ordem de Serviço");
        ordemServicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrdemServicoView().setVisible(true);
            }
        });

        // Adiciona botões baseados no papel do usuário
        if (role.equals("admin")) {
            add(cadastroPecasButton);
            add(baixaEstoqueButton);
            add(pedidoCompraButton);
        }

        add(consultaEstoqueButton);
        add(cadastroClientesButton);
        add(ordemServicoButton);
    }

    public static void main(String[] args) {
        // Apenas para fins de teste
        MainView mainView = new MainView("admin");
        mainView.setVisible(true);
    }
}
