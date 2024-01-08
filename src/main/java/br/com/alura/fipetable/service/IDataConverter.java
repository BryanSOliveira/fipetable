package br.com.alura.fipetable.service;

import java.util.List;

public interface IDataConverter {
  <T> T getData(String json, Class<T> clazz);

  <T> List<T> getList(String json, Class<T> clazz);
}
