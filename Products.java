public class Products {
    private String nome;
    private double preco;
    private String codigo;
    private int quantidade; // quantidade em estoque

    public Products(String codigo, String nome, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = 10; // valor inicial padrão
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void adicionarEstoque(int qtd) {
        this.quantidade += qtd;
    }

    public boolean removerEstoque(int qtd) {
        if (this.quantidade >= qtd) {
            this.quantidade -= qtd;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Código: %s | Produto: %s | Preço: R$ %.2f | Estoque: %d", codigo, nome, preco, quantidade);
    }
}