import java.util.ArrayList;
import java.util.List;


class Categoria {
    private String nome;
    private List<Products> productsList;

    public Categoria(String nome) {
        this.nome = nome;
        this.productsList = new ArrayList<>();
    }

    public void adicionarProduto(Products product) {
        productsList.add(product);
    }

    public String getNome() {
        return nome;
    }

    public List<Products> getProdutos() {
        return productsList;
    }

    public void listarProdutos(int minStockAlert) {
        System.out.println("Categoria: " + nome);
        for (Products p : productsList) {
            String alerta = (p.getQuantidade() <= minStockAlert) ? " [ESTOQUE BAIXO]" : "";
            System.out.println("  - " + p + alerta);
        }
    }

    public Products buscarProdutoPorCodigo(String codigo) {
        for (Products p : productsList) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }
}

