package com.espweb.chronos.presentation.utils;

import com.espweb.chronos.domain.model.EnumTipo;
import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.model.Assunto;
import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.model.Disciplina;
import com.espweb.chronos.presentation.model.Exercicio;
import com.espweb.chronos.presentation.model.Material;
import com.espweb.chronos.presentation.model.Overview;
import com.espweb.chronos.presentation.model.Revisao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ChartUtil {

    public static Map<String, Map<EnumTipo, Long>> mapArtefatoFromCronogramas(List<Cronograma> cronogramas) {
        Map<String, Map<EnumTipo, Long>> map = new HashMap<>();
        cronogramas.forEach(cronograma -> {
            List<Artefato> artefatos =  cronograma.getDisciplinas().stream().map(Disciplina::getAssuntos).flatMap(List::stream).map(Assunto::getArtefatos).flatMap(List::stream).collect(toList());
            map.put(cronograma.getTitulo(), artefatos.stream().collect(Collectors.groupingBy(Artefato::getTipo, Collectors.counting())));
        });
        return map;
    }

    public static Map<String, Map<EnumTipo, Long>> mapArtefatoFromDisciplinas(List<Disciplina> disciplinas) {
        Map<String, Map<EnumTipo, Long>> map = new HashMap<>();
        disciplinas.forEach(disciplina -> {
            List<Artefato> artefatos =  disciplina.getAssuntos().stream().map(Assunto::getArtefatos).flatMap(List::stream).collect(toList());
            map.put(disciplina.getNome(), artefatos.stream().collect(Collectors.groupingBy(Artefato::getTipo, Collectors.counting())));
        });
        return map;
    }

    public static Overview buildOverview(Cronograma cronograma) {
        List<Disciplina> disciplinas = cronograma.getDisciplinas();
        List<Artefato> artefatos =  disciplinas.stream().map(Disciplina::getAssuntos).flatMap(List::stream).map(Assunto::getArtefatos).flatMap(List::stream).collect(toList());

        Map<EnumTipo, List<Artefato>> tipoArtefatoMap = artefatos.stream().collect(Collectors.groupingBy(Artefato::getTipo));

        Map<Material.Escopo, Integer> minutosPorEscopo = tipoArtefatoMap.getOrDefault(EnumTipo.MATERIAL, Collections.emptyList()).stream().map(artefato -> (Material) artefato).collect(Collectors.groupingBy(Material::getEscopo, Collectors.summingInt(Material::getMinutos)));
        int somaMateriais = minutosPorEscopo.values().stream().mapToInt(integer -> integer).sum();


        List<Artefato> exercicios = tipoArtefatoMap.getOrDefault(EnumTipo.EXERCICIO, Collections.emptyList());
        int acertosExercicios = exercicios.stream().map(artefato -> (Exercicio) artefato).mapToInt(Exercicio::getAcertos).sum();
        int totalExercicios = exercicios.stream().map(artefato -> (Exercicio) artefato).mapToInt(Exercicio::getQuantidade).sum();


        Map<Revisao.Escopo, Long> quantidadePorEscopo = tipoArtefatoMap.getOrDefault(EnumTipo.REVISAO, Collections.emptyList()).stream().map(artefato -> (Revisao) artefato).collect(Collectors.groupingBy(Revisao::getEscopo, Collectors.counting()));

        Overview overview = new Overview();
        overview.setDisciplinas(disciplinas.size());
        overview.setAssuntos((int) disciplinas.stream().map(Disciplina::getAssuntos).mapToLong(List::size).sum());
        overview.setLivros(minutosPorEscopo.getOrDefault(Material.Escopo.LIVROS, 0));
        overview.setIntArtPost(minutosPorEscopo.getOrDefault(Material.Escopo.DIVERSOS, 0));
        overview.setVideoAulas(minutosPorEscopo.getOrDefault(Material.Escopo.VIDEO_AULA, 0));
        overview.setAcertos(acertosExercicios);
        overview.setTotalExercicios(totalExercicios);
        overview.setTotalMateriais(somaMateriais);
        overview.setDiarias(quantidadePorEscopo.getOrDefault(Revisao.Escopo.DIARIA, 0L).intValue());
        overview.setSemanais(quantidadePorEscopo.getOrDefault(Revisao.Escopo.SEMANAL, 0L).intValue());
        overview.setQuinzenais(quantidadePorEscopo.getOrDefault(Revisao.Escopo.QUINZENAL, 0L).intValue());
        overview.setMensais(quantidadePorEscopo.getOrDefault(Revisao.Escopo.MENSAL, 0L).intValue());
        return overview;
    }
}
