import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class SistemaVendas {
    private List<Categoria> categorias;
    private Users usuarioLogado;
    private int minStockAlert = 1;
    private List<String> historicoVendas = new ArrayList<>();
    private List<String> historicoCompras = new ArrayList<>();

    public SistemaVendas() {
        categorias = new ArrayList<>();
        inicializarDados();
    }

    private void inicializarDados() {
        Categoria notebooks = new Categoria("Notebook");
        notebooks.adicionarProduto(new Products("N001", "Notebook Dell", 3500.00));
        notebooks.adicionarProduto(new Products("N002", "Notebook HP", 3200.00));
        notebooks.adicionarProduto(new Products("N003", "Notebook Lenovo", 2800.00));
        notebooks.adicionarProduto(new Products("N004", "Notebook Acer", 3000.00));
        notebooks.adicionarProduto(new Products("N005", "Notebook Samsung", 3100.00));

        Categoria pcs = new Categoria("PC");
        pcs.adicionarProduto(new Products("P001", "PC Gamer", 5000.00));
        pcs.adicionarProduto(new Products("P002", "PC Escritório", 2500.00));
        pcs.adicionarProduto(new Products("P003", "PC All-in-One", 4000.00));
        pcs.adicionarProduto(new Products("P004", "PC Compacto", 2700.00));
        pcs.adicionarProduto(new Products("P005", "PC Torre", 2900.00));

        Categoria servidores = new Categoria("Servidor");
        servidores.adicionarProduto(new Products("S001", "Servidor Dell PowerEdge", 15000.00));
        servidores.adicionarProduto(new Products("S002", "Servidor HP ProLiant", 14500.00));
        servidores.adicionarProduto(new Products("S003", "Servidor IBM System x", 16000.00));
        servidores.adicionarProduto(new Products("S004", "Servidor Lenovo ThinkSystem", 15500.00));
        servidores.adicionarProduto(new Products("S005", "Servidor Supermicro", 14000.00));

        categorias.add(notebooks);
        categorias.add(pcs);
        categorias.add(servidores);
    }

    public void mostrarMenu() {
        if (!login()) {
            System.out.println("Login falhou. Encerrando o sistema...");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n=== Sistema de Vendas XYZ ===");
            System.out.println("Usuário: " + usuarioLogado.getName() + " | Perfil: " + usuarioLogado.getClass().getSimpleName());
            System.out.println("1. Listar categorias e produtos");
            if (usuarioLogado instanceof Manager) {
                System.out.println("2. Definir mínimo de estoque para alerta");
                System.out.println("3. Relatórios");
            } else if (usuarioLogado instanceof Seller) {
                System.out.println("2. Simular venda");
                System.out.println("3. Relatórios");
            } else if (usuarioLogado instanceof Buyer) {
                System.out.println("2. Comprar produtos para estoque");
                System.out.println("3. Relatórios");
            }
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1:
                    listarCategoriasEProdutos();
                    break;
                case 2:
                    if (usuarioLogado instanceof Manager) {
                        definirMinimoEstoque(scanner);
                    } else if (usuarioLogado instanceof Seller) {
                        simularVenda(scanner);
                    } else if (usuarioLogado instanceof Buyer) {
                        comprarParaEstoque(scanner);
                    }
                    break;
                case 3:
                    emitirRelatorios();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Login ===");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        try (BufferedReader br = new BufferedReader(new FileReader("data.json"))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();
            String emailArq = extrairValor(json, "email");
            String senhaArq = extrairValor(json, "password");
            String nomeArq = extrairValor(json, "name");
            String isAdminStr = extrairValor(json, "isAdmin");
            boolean isAdmin = isAdminStr.equals("true");
            if (email.equals(emailArq) && senha.equals(senhaArq)) {
                if (isAdmin) {
                    usuarioLogado = new Manager(nomeArq, emailArq, senhaArq);
                } else {
                    usuarioLogado = new Seller(nomeArq, emailArq, senhaArq); // ou Buyer
                }
                return true;
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de usuários: " + e.getMessage());
        }
        return false;
    }

    private String extrairValor(String json, String chave) {
        String aspas = "\\\"";
        String busca = aspas + chave + aspas + ":";
        int idx = json.indexOf(busca);
        if (idx == -1) return "";
        int start = json.indexOf(':', idx) + 1;
        while (start < json.length() && (json.charAt(start) == ' ' || json.charAt(start) == '"')) start++;
        int end = start;
        while (end < json.length() && json.charAt(end) != ',' && json.charAt(end) != '}' && json.charAt(end) != '"') end++;
        return json.substring(start, end).replace("\"", "").trim();
    }

    private void listarCategoriasEProdutos() {
        int minAlert = minStockAlert;
        if (usuarioLogado instanceof Manager) {
            minAlert = ((Manager) usuarioLogado).getMinStockAlert();
        }
        for (Categoria categoria : categorias) {
            categoria.listarProdutos(minAlert);
        }
    }

    private void simularVenda(Scanner scanner) {
        System.out.print("Digite o código do produto: ");
        String codigo = scanner.next();
        Products produtoSelecionado = null;
        Categoria categoriaSelecionada = null;
        for (Categoria categoria : categorias) {
            produtoSelecionado = categoria.buscarProdutoPorCodigo(codigo);
            if (produtoSelecionado != null) {
                categoriaSelecionada = categoria;
                break;
            }
        }
        if (produtoSelecionado != null) {
            System.out.print("Quantidade a vender: ");
            int qtd = scanner.nextInt();
            if (produtoSelecionado.getQuantidade() >= qtd) {
                produtoSelecionado.removerEstoque(qtd);
                System.out.println("Venda realizada! Estoque atual: " + produtoSelecionado.getQuantidade());
                historicoVendas.add(String.format("%s;%s;%d;%.2f;%s", produtoSelecionado.getCodigo(), produtoSelecionado.getNome(), qtd, produtoSelecionado.getPreco()*qtd, usuarioLogado.getName()));
                if (produtoSelecionado.getQuantidade() <= minStockAlert) {
                    System.out.println("[ALERTA] Estoque baixo para este produto!");
                }
            } else {
                System.out.println("Estoque insuficiente para a venda!");
            }
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private void definirMinimoEstoque(Scanner scanner) {
        System.out.print("Defina o mínimo de estoque para alerta: ");
        int min = scanner.nextInt();
        scanner.nextLine();
        minStockAlert = min;
        if (usuarioLogado instanceof Manager) {
            ((Manager) usuarioLogado).setMinStockAlert(min);
        }
        System.out.println("Mínimo de estoque para alerta definido como: " + min);
    }

    private void comprarParaEstoque(Scanner scanner) {
        System.out.print("Digite o código do produto para compra: ");
        String codigo = scanner.next();
        Products produtoSelecionado = null;
        for (Categoria categoria : categorias) {
            produtoSelecionado = categoria.buscarProdutoPorCodigo(codigo);
            if (produtoSelecionado != null) break;
        }
        if (produtoSelecionado != null) {
            System.out.print("Quantidade a comprar: ");
            int qtd = scanner.nextInt();
            produtoSelecionado.adicionarEstoque(qtd);
            historicoCompras.add(String.format("%s;%s;%d;%.2f;%s", produtoSelecionado.getCodigo(), produtoSelecionado.getNome(), qtd, produtoSelecionado.getPreco()*qtd, usuarioLogado.getName()));
            System.out.println("Compra realizada! Estoque atual: " + produtoSelecionado.getQuantidade());
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private void emitirRelatorios() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Relatórios ===");
        System.out.println("1. Estoque atual");
        if (usuarioLogado instanceof Manager || usuarioLogado instanceof Seller) {
            System.out.println("2. Vendas realizadas");
        }
        if (usuarioLogado instanceof Manager || usuarioLogado instanceof Buyer) {
            System.out.println("3. Compras realizadas");
        }
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        int op = scanner.nextInt();
        switch (op) {
            case 1:
                System.out.println("\n--- Estoque Atual ---");
                listarCategoriasEProdutos();
                break;
            case 2:
                if (usuarioLogado instanceof Manager || usuarioLogado instanceof Seller) {
                    System.out.println("\n--- Relatório de Vendas ---");
                    for (String venda : historicoVendas) {
                        String[] dados = venda.split(";");
                        System.out.printf("Produto: %s | Código: %s | Quantidade: %s | Total: R$ %s | Vendedor: %s\n", dados[1], dados[0], dados[2], dados[3], dados[4]);
                    }
                } else {
                    System.out.println("Acesso negado.");
                }
                break;
            case 3:
                if (usuarioLogado instanceof Manager || usuarioLogado instanceof Buyer) {
                    System.out.println("\n--- Relatório de Compras ---");
                    for (String compra : historicoCompras) {
                        String[] dados = compra.split(";");
                        System.out.printf("Produto: %s | Código: %s | Quantidade: %s | Total: R$ %s | Comprador: %s\n", dados[1], dados[0], dados[2], dados[3], dados[4]);
                    }
                } else {
                    System.out.println("Acesso negado.");
                }
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }
}
