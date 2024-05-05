package com.automoveis.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAutomoveis(@JsonAlias("Valor") String valor,
                            @JsonAlias("Marca") String marca,
                            @JsonAlias("Modelo") String modelo,
                            @JsonAlias("AnoModelo") Integer anoModelo,
                            @JsonAlias("Combustivel") String tipoCombustivel) {

}
