package br.com.myifpb;

public class Acessos {
    private String titulo;
    private String url;

    public Acessos(String titulo, String url) {
        this.titulo = titulo;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
