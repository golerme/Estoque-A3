module EstoquePecas {
    requires java.sql;
    requires java.desktop;

    exports estoque.cadastro;
    exports estoque.cliente;
    exports estoque.ordemservico;
    exports estoque.pedido;
    exports estoque.faturamento;
    exports estoque.consulta;
    exports estoque.baixa;
}
