package com.automoveis.tabelafipe.service;

import java.util.List;

public interface IConverteDados {

    <T> T obterDados(String json, Class<T> Class);
    <T> List<T> obterLista(String json, Class<T> classe);

}
