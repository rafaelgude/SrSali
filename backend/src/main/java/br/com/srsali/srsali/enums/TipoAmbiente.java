package br.com.srsali.srsali.enums;

public enum TipoAmbiente {
    SALA_AULA,
    LABORATORIO_INFORMATICA;
    
    public static TipoAmbiente toEnum(String tipoAmbiente) {
        if (tipoAmbiente == null || tipoAmbiente.isBlank())
            return null;
        
        var ordinal = Integer.parseInt(tipoAmbiente);
        for (TipoAmbiente x : TipoAmbiente.values())
            if (x.ordinal() == ordinal)
                return x;
        
        throw new IllegalArgumentException("Tipo de Ambiente inválido: " + tipoAmbiente);
    }
}
