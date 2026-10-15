
import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {
    private final Estoque estoque = new Estoque();
    private final Venda venda = new Venda();

    private final JTextField campoCodigo = new JTextField();
    private final JTextField campoNome = new JTextField();
    private final JTextField campoPreco = new JTextField();
    private final JTextField campoQuantidade = new JTextField();

    private final JTextField codigoVenda = new JTextField();
    private final JTextField quantidadeVenda = new JTextField();

    private final DefaultListModel<String> modeloEstoque =
            new DefaultListModel<>();
    private final JList<String> listaEstoque = new JList<>(modeloEstoque);

    public Main() {
        setTitle("Supermercado Java");
        setSize(650, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(245, 247, 250));

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Cadastrar produto", criarPainelCadastro());
        abas.addTab("Estoque", criarPainelEstoque());
        abas.addTab("Realizar venda", criarPainelVenda());

        add(abas);
        atualizarEstoque();
    }

    private JPanel criarPainelCadastro() {
        JPanel painel = new JPanel(new GridLayout(5, 2, 10, 12));
        painel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        painel.add(new JLabel("Código:"));
        painel.add(campoCodigo);

        painel.add(new JLabel("Nome do produto:"));
        painel.add(campoNome);

        painel.add(new JLabel("Preço (R$):"));
        painel.add(campoPreco);

        painel.add(new JLabel("Quantidade:"));
        painel.add(campoQuantidade);

        JButton cadastrar = new JButton("Cadastrar produto");
        JButton limpar = new JButton("Limpar campos");

        cadastrar.addActionListener(e -> cadastrarProduto());
        limpar.addActionListener(e -> limparCampos());

        painel.add(cadastrar);
        painel.add(limpar);

        return painel;
    }

    private JPanel criarPainelEstoque() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel(
                "PRODUTOS CADASTRADOS", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JButton atualizar = new JButton("Atualizar estoque");
        atualizar.addActionListener(e -> atualizarEstoque());

        painel.add(titulo, BorderLayout.NORTH);
        painel.add(new JScrollPane(listaEstoque), BorderLayout.CENTER);
        painel.add(atualizar, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelVenda() {
        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 15));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 35, 30, 35));

        painel.add(new JLabel("Código do produto:"));
        painel.add(codigoVenda);

        painel.add(new JLabel("Quantidade:"));
        painel.add(quantidadeVenda);

        JButton vender = new JButton("Finalizar venda");
        JButton limpar = new JButton("Limpar campos");

        vender.addActionListener(e -> realizarVenda());
        limpar.addActionListener(e -> {
            codigoVenda.setText("");
            quantidadeVenda.setText("");
        });

        painel.add(vender);
        painel.add(limpar);

        return painel;
    }

    private void cadastrarProduto() {
        try {
            int codigo = Integer.parseInt(campoCodigo.getText().trim());
            String nome = campoNome.getText().trim();
            double preco = Double.parseDouble(
                    campoPreco.getText().trim().replace(",", "."));
            int quantidade = Integer.parseInt(
                    campoQuantidade.getText().trim());

            Produto produto = new Produto(codigo, nome, preco, quantidade);

            if (estoque.cadastrar(produto)) {
                JOptionPane.showMessageDialog(
                        this, "Produto cadastrado com sucesso!");
                limparCampos();
                atualizarEstoque();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Dados inválidos ou código já cadastrado.",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this, "Preencha os campos numéricos corretamente.");
        }
    }

    private void realizarVenda() {
        try {
            int codigo = Integer.parseInt(codigoVenda.getText().trim());
            int quantidade = Integer.parseInt(
                    quantidadeVenda.getText().trim());

            String resultado = venda.realizarVenda(
                    estoque, codigo, quantidade);

            JOptionPane.showMessageDialog(this, resultado);
            atualizarEstoque();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this, "Digite um código e uma quantidade válidos.");
        }
    }

    private void atualizarEstoque() {
        modeloEstoque.clear();

        for (Produto produto : estoque.listarProdutos()) {
            modeloEstoque.addElement(produto.toString());
        }

        if (modeloEstoque.isEmpty()) {
            modeloEstoque.addElement("Nenhum produto cadastrado.");
        }
    }

    private void limparCampos() {
        campoCodigo.setText("");
        campoNome.setText("");
        campoPreco.setText("");
        campoQuantidade.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main janela = new Main();
            janela.setVisible(true);
        });
    }
}