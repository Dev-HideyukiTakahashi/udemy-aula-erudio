package br.com.udemy.erudio_springboot.mapper;

import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class ObjectMapper {

  private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

  public static <O, D> D parseObjects(O origin, Class<D> destination) {
    return mapper.map(origin, destination);
  }

  public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
    return origin.stream().map(o -> mapper.map(o, destination)).toList();
  }
}