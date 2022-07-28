package br.com.lex4crypto.monolito.models;

public enum CriptoMoeda {

    BITCOIN(1),
    ETHEREUM(2),
    CARDANO(3),
    SOLANA(4);

    private int identificador;

    CriptoMoeda(int identificador) {
        this.identificador = identificador;
    }

    public int getIdentificador() {
        return identificador;
    }

    public static CriptoMoeda nomeMoeda(int codigo){
        for(CriptoMoeda moeda : CriptoMoeda.values()) {
            if(moeda.getIdentificador() == codigo){
                return moeda;
            }
        }
        throw new IllegalArgumentException("Moeda inválida");
    }
}
