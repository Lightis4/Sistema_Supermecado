
import java.util.ArrayList;
import java.util.List;

public class Estoque {
    private final List<Produto> produtos = new ArrayList<>();

    public boolean cadastrar(Produto produto) {
        if (produto.getCodigo() <= 0 ||
            produto.getNome().isBlank() ||
            produto.getPreco() <= 0 ||
            produto.getQuantidade() < 0 ||
            buscarProduto(produto.getCodigo()) != null) {
            return false;
        }

        produtos.add(produto);
        return true;
    }

    public List<Produto> listarProdutos() {
        return new ArrayList<>(produtos);
    }

    public Produto buscarProduto(int codigo) {
        for (Produto produto : produtos) {
            if (produto.getCodigo() == codigo) {
                return produto;
            }
        }

        return null;
    }
}
