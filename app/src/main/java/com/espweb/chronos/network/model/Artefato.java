package com.espweb.chronos.network.model;

import com.google.gson.annotations.SerializedName;

public class Artefato {
    @SerializedName("0")
    private Material payloadMaterial;

    @SerializedName("material")
    private Material responseMaterial;

    @SerializedName("1")
    private Revisao payloadRevisao;

    @SerializedName("revisao")
    private Revisao responseRevisao;

    @SerializedName("2")
    private Exercicio payloadExercicio;

    @SerializedName("exercicio")
    private Exercicio responseExercicio;

    public Artefato() {
    }

    public Artefato(Material payloadMaterial) {
        this.payloadMaterial = payloadMaterial;
    }

    public Artefato(Revisao payloadRevisao) {
        this.payloadRevisao = payloadRevisao;
    }

    public Artefato(Exercicio payloadExercicio) {
        this.payloadExercicio = payloadExercicio;
    }

    public Material getPayloadMaterial() {
        return payloadMaterial;
    }

    public void setPayloadMaterial(Material payloadMaterial) {
        this.payloadMaterial = payloadMaterial;
    }

    public Material getResponseMaterial() {
        return responseMaterial;
    }

    public void setResponseMaterial(Material responseMaterial) {
        this.responseMaterial = responseMaterial;
    }

    public Revisao getPayloadRevisao() {
        return payloadRevisao;
    }

    public void setPayloadRevisao(Revisao payloadRevisao) {
        this.payloadRevisao = payloadRevisao;
    }

    public Revisao getResponseRevisao() {
        return responseRevisao;
    }

    public void setResponseRevisao(Revisao responseRevisao) {
        this.responseRevisao = responseRevisao;
    }

    public Exercicio getPayloadExercicio() {
        return payloadExercicio;
    }

    public void setPayloadExercicio(Exercicio payloadExercicio) {
        this.payloadExercicio = payloadExercicio;
    }

    public Exercicio getResponseExercicio() {
        return responseExercicio;
    }

    public void setResponseExercicio(Exercicio responseExercicio) {
        this.responseExercicio = responseExercicio;
    }
}
