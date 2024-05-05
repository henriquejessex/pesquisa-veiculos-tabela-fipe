package com.automoveis.tabelafipe.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.automoveis.tabelafipe.model.Dados;
import com.automoveis.tabelafipe.model.DadosAutomoveis;
import com.automoveis.tabelafipe.model.Modelos;
import com.automoveis.tabelafipe.service.ConsumoApi;
import com.automoveis.tabelafipe.service.ConverteDados;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu(){
        var menu = """
                *** OPÇÔES ***
                - Carro
                - Moto
                - Caminhão

                Digite uma das opções para pesquisar: 
                """;
        System.out.println(menu);
        var opcao = leitura.nextLine();

        String endereco = null;

        if (opcao.toLowerCase().contains("carr")) {
            endereco =  URL_BASE + "carros/marcas";
        }else if (opcao.toLowerCase().contains("mot")) {
            endereco = URL_BASE + "motos/marcas";
        }else if (opcao.toLowerCase().contains("camin")) {
            endereco = URL_BASE + "caminhoes/marcas";
        }

        var json = consumo.obterDados(endereco);

        //System.out.println(json);

        // obtendo lista de marcas
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
            .sorted(Comparator.comparing(Dados::codigo))
            .forEach(System.out::println);

        // Buscar por modelos de marca especifica
        System.out.println("\nInforme o código da marca para consulta: ");
        var codigoMarca = leitura.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";

        json = consumo.obterDados(endereco);
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos dessa marca:");

        modeloLista.modelos().stream()
            .sorted(Comparator.comparing(Dados::codigo))
            .forEach(System.out::println);

        
        // Buscando veiculo por nome
        System.out.println("\nDigite um trecho do nome do veiculo a ser buscado: ");
        var nomeVeiculo = leitura.nextLine();
        
        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
            .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
            .collect(Collectors.toList());
        modelosFiltrados.forEach(System.out::println);

        // obtendo modelos por ano
        System.out.println("\nDigite o código do modelo: ");
        var codigoModelo = leitura.nextLine();

        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endereco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<DadosAutomoveis> listaVeiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            DadosAutomoveis Veiculo = conversor.obterDados(json, DadosAutomoveis.class);
            listaVeiculos.add(Veiculo);
        }

        System.out.println("\nVeiculos filtrados por ano: ");
        listaVeiculos.forEach(System.out::println);
    }
}
