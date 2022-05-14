package Pasta;

public class ComunicadoComecar extends Comunicado {
    private ComunicadoDeDados comunicadoDeDados;

    public int getPos() {
        return pos;
    }

    private int pos = 0;

    public ComunicadoDeDados getComunicadoDeDados() {
        return comunicadoDeDados;
    }

    public void setComunicadoDeDados(ComunicadoDeDados comunicadoDeDados) {
        this.comunicadoDeDados = comunicadoDeDados;
    }

    public ComunicadoComecar( ComunicadoDeDados comunicadoDeDados,int pos){
        this.comunicadoDeDados = comunicadoDeDados;
        this.pos = pos;
    }
}
