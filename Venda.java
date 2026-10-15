
public class Venda {

    public String realizarVenda(Estoque estoque, int codigo, int quantidade) {
        Produto produto = estoque.buscarProduto(codigo);

        if (produto == null) {
            return "Produto não encontrado.";
        }

        if (quantidade <= 0) {
            return "Informe uma quantidade maior que zero.";
        }

        if (!produto.retirarEstoque(quantidade)) {
            return "Estoque insuficiente para essa venda.";
        }

        double total = produto.getPreco() * quantidade;

        return String.format(
            java.util.Locale.forLanguageTag("pt-BR"),
            "Venda realizada com sucesso!%n%n" +
            "Produto: %s%nQuantidade: %d%nTotal: R$ %.2f",
            produto.getNome(), quantidade, total
        );
    }
}
