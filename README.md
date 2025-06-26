import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Categoria {
    private String nome;
    private List<Produto> produtos;

    public Categoria(String nome) {
        this.nome = nome;
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public String getNome() {
        return nome;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void listarProdutos() {
        System.out.println("Categoria: " + nome);
        for (Produto p : produtos) {
            System.out.println("  - " + p);
        }
    }
}

class Produto {
    private String nome;
    private double preco;
    private String codigo;

    public Produto(String codigo, String nome, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
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

    @Override
    public String toString() {
        return String.format("Código: %s | Produto: %s | Preço: R$ %.2f", codigo, nome, preco);
    }
}






public class SistemaVendas {
    private List<Categoria> categorias;

    public SistemaVendas() {
        categorias = new ArrayList<>();
        inicializarDados();
    }

    private void inicializarDados() {
        Categoria notebooks = new Categoria("Notebook");
        notebooks.adicionarProduto(new Produto("N001", "Notebook Dell", 3500.00));
        notebooks.adicionarProduto(new Produto("N002", "Notebook HP", 3200.00));
        notebooks.adicionarProduto(new Produto("N003", "Notebook Lenovo", 2800.00));
        notebooks.adicionarProduto(new Produto("N004", "Notebook Acer", 3000.00));
        notebooks.adicionarProduto(new Produto("N005", "Notebook Samsung", 3100.00));

        Categoria pcs = new Categoria("PC");
        pcs.adicionarProduto(new Produto("P001", "PC Gamer", 5000.00));
        pcs.adicionarProduto(new Produto("P002", "PC Escritório", 2500.00));
        pcs.adicionarProduto(new Produto("P003", "PC All-in-One", 4000.00));
        pcs.adicionarProduto(new Produto("P004", "PC Compacto", 2700.00));
        pcs.adicionarProduto(new Produto("P005", "PC Torre", 2900.00));

        Categoria servidores = new Categoria("Servidor");
        servidores.adicionarProduto(new Produto("S001", "Servidor Dell PowerEdge", 15000.00));
        servidores.adicionarProduto(new Produto("S002", "Servidor HP ProLiant", 14500.00));
        servidores.adicionarProduto(new Produto("S003", "Servidor IBM System x", 16000.00));
        servidores.adicionarProduto(new Produto("S004", "Servidor Lenovo ThinkSystem", 15500.00));
        servidores.adicionarProduto(new Produto("S005", "Servidor Supermicro", 14000.00));

        categorias.add(notebooks);
        categorias.add(pcs);
        categorias.add(servidores);
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Sistema de Vendas XYZ ===");
            System.out.println("1. Listar categorias e produtos");
            System.out.println("2. Simular venda");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    listarCategoriasEProdutos();
                    break;
                case 2:
                    simularVenda(scanner);
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private void listarCategoriasEProdutos() {
        for (Categoria categoria : categorias) {
            categoria.listarProdutos();
        }
    }

    private void simularVenda(Scanner scanner) {
        System.out.print("Digite o código do produto: ");
        String codigo = scanner.next();

        Produto produtoSelecionado = null;
        for (Categoria categoria : categorias) {
            for (Produto produto : categoria.getProdutos()) {
                if (produto.getCodigo().equalsIgnoreCase(codigo)) {
                    produtoSelecionado = produto;
                    break;
                }
            }
        }

        if (produtoSelecionado != null) {
            System.out.println("Produto selecionado: " + produtoSelecionado);
            System.out.println("Venda simulada com sucesso!");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SistemaVendas sistema = new SistemaVendas();
        sistema.mostrarMenu();
    }
}



