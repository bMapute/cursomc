package com.bmapute.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bmapute.cursomc.domain.Categoria;
import com.bmapute.cursomc.domain.Cidade;
import com.bmapute.cursomc.domain.Cliente;
import com.bmapute.cursomc.domain.Endereco;
import com.bmapute.cursomc.domain.Estado;
import com.bmapute.cursomc.domain.ItemPedido;
import com.bmapute.cursomc.domain.Pagamento;
import com.bmapute.cursomc.domain.PagamentoComBoleto;
import com.bmapute.cursomc.domain.PagamentoComCartao;
import com.bmapute.cursomc.domain.Pedido;
import com.bmapute.cursomc.domain.Produto;
import com.bmapute.cursomc.domain.enums.EstadoPagamento;
import com.bmapute.cursomc.domain.enums.TipoCliente;
import com.bmapute.cursomc.repositories.CategoriaRepository;
import com.bmapute.cursomc.repositories.CidadeRepository;
import com.bmapute.cursomc.repositories.ClienteRepository;
import com.bmapute.cursomc.repositories.EnderecoRepository;
import com.bmapute.cursomc.repositories.EstadoRepository;
import com.bmapute.cursomc.repositories.ItemPedidoRepository;
import com.bmapute.cursomc.repositories.PagamentoRepository;
import com.bmapute.cursomc.repositories.PedidoRepository;
import com.bmapute.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	EstadoRepository estadoRepository;
	
	@Autowired
	CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		
		Categoria cat1=new Categoria(null, "Informatica");
		Categoria cat2=new Categoria(null, "Escritorio");
		Categoria cat3=new Categoria(null, "Cama mesa e banho");
		Categoria cat4=new Categoria(null, "Electronicos");
		Categoria cat5=new Categoria(null, "Jardinagem");
		Categoria cat6=new Categoria(null, "Decoracao");
		Categoria cat7=new Categoria(null, "Perfumaria");
		
		Produto p1=new Produto(null, "Computador", 2000.00);
		Produto p2=new Produto(null, "Impressora", 8000.00);
		Produto p3=new Produto(null, "Mouse", 80.00);
		
		Estado est1=new Estado(null, "Minas Gerais");
		Estado est2=new Estado(null, "Sao Paulo");
		
		Cidade c1=new Cidade(null, "Uberlandia", est1);
		Cidade c2=new Cidade(null, "Sao Paulo", est2);
		Cidade c3=new Cidade(null, "Campinas", est2);
		
	
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
	categoriaRepository.save(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7));
	produtoRepository.save(Arrays.asList(p1,p2,p3));
	
	est1.getCidades().addAll(Arrays.asList(c1));
	est1.getCidades().addAll(Arrays.asList(c2,c3));
	
	estadoRepository.save(Arrays.asList(est1,est2));
	cidadeRepository.save(Arrays.asList(c1,c2,c3));
	
	Cliente cl1=new Cliente(null, "Maria Silva", "maria@gmail.com", "3444412121", TipoCliente.PESSOAFISICA);
		
	cl1.getTelefones().addAll(Arrays.asList("232333121","2411121"));
	
	Endereco e1=new Endereco(null, "Rua flores", "31", "Costa do sol ", "32111", cl1, c1);
	
	Endereco e2=new Endereco(null, "Rua das mafureiras", "313", "Bairro Central ", "32111", cl1, c2);
	
	cl1.getEnderecos().addAll(Arrays.asList(e1,e2));
	
	clienteRepository.save(Arrays.asList(cl1));
	enderecoRepository.save(Arrays.asList(e1,e2));
	
	SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy hh:mm");
	
	Pedido ped1=new Pedido(null, sdf.parse("30/09/2017 10:32"),cl1,e1);
	Pedido ped2=new Pedido(null, sdf.parse("31/12/2017 09:32"),cl1,e2);
	
	Pagamento pagto1=new PagamentoComCartao(null, EstadoPagamento.FINALIZADO,ped1,6);
	ped1.setPagamento(pagto1);
	
	Pagamento pagto2=new PagamentoComBoleto(null, EstadoPagamento.PENDENTE,ped2,sdf.parse("20/01/2018 00:00"),null);
	ped2.setPagamento(pagto2);
	
	cl1.getPedidos().addAll(Arrays.asList(ped1,ped2));
	
	pedidoRepository.save(Arrays.asList(ped1,ped2));
	pagamentoRepository.save(Arrays.asList(pagto1,pagto2));
	
	ItemPedido ip1=new ItemPedido(ped1,p1,0.00, 1, 2000.00);
	ItemPedido ip2=new ItemPedido(ped1,p3,0.00, 2, 80.00);
	ItemPedido ip3=new ItemPedido(ped2,p2,100.00, 1, 800.00);
	
	ped1.getItens().addAll(Arrays.asList(ip1,ip2));
	ped2.getItens().addAll(Arrays.asList(ip3));
	
	p1.getItens().addAll(Arrays.asList(ip1));
	p2.getItens().addAll(Arrays.asList(ip3));
	p3.getItens().addAll(Arrays.asList(ip2));
	
	itemPedidoRepository.save(Arrays.asList(ip1,ip2,ip3));
	}
}
