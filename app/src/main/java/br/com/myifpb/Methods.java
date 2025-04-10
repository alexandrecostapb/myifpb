package br.com.myifpb;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Methods {
    /**
     * Este método retorna uma lista de acessos do portal com as URL de cada um.
     * Usado para direcionar o usuário para a página selecionada que ele deseja ir.
     *
     * @return ArrayList com seis objetos de Acessos diferentes, cada qual correspondendo a um determinado site diferente.
     */
    public ArrayList<Acessos> listaDeAcessos() {
        ArrayList<Acessos> acessosArrayList = new ArrayList<>();

        Acessos acesso1 = new Acessos("SUAP", "https://suap.ifpb.edu.br/accounts/login/?next=/");
        acessosArrayList.add(acesso1);

        Acessos acesso2 = new Acessos("MOODLE", "https://presencial.ifpb.edu.br/login/index.php");
        acessosArrayList.add(acesso2);

        Acessos acesso3 = new Acessos("BIBLIOTECA", "https://biblioteca.ifpb.edu.br/");
        acessosArrayList.add(acesso3);

        Acessos acesso4 = new Acessos("PORTAL DO ESTUDANTE", "https://estudante.ifpb.edu.br/");
        acessosArrayList.add(acesso4);

        Acessos acesso5 = new Acessos("REPORSITÓRIO DIGITAL", "https://repositorio.ifpb.edu.br/");
        acessosArrayList.add(acesso5);

        Acessos acesso6 = new Acessos("EVENTOS", "https://eventos.ifpb.edu.br/");
        acessosArrayList.add(acesso6);

        return acessosArrayList;
    }

    /**
     * Este método serve para adicionar imagens no SlideModel (Carrossel de imagens) da página principal do aplicativo.
     *
     * @return ArrayList de três objetos de SlideModel com as imagens para serem adicionadas ao Carrossel.
     */
    public ArrayList<SlideModel> slideModels() {
        ArrayList<SlideModel> modelArray = new ArrayList<>();

        modelArray.add(new SlideModel(R.drawable.banner_portal_ifpb, ScaleTypes.FIT));
        modelArray.add(new SlideModel(R.drawable.banner_portal_do_estudante, ScaleTypes.FIT));
        modelArray.add(new SlideModel(R.drawable.banner_processos_seletivos, ScaleTypes.FIT));

        return modelArray;
    }

    /**
     * Este método serve para verificar o horário que o usuário está utilizando o aplicativo, usando outros métodos
     * que detectam o horário do dispositivo do usuário e, de acordo com o número do horário que é coletado, o método
     * processa a informação e o transforma em uma String com textos de comprimentação adequados para o horário que o
     * usuário se encontra.
     *
     * @return String de texto com comprimentos adequados para o horário que o usuário se encontra.
     */
    public String turno() {
        String turno = "";
        int hora = ZonedDateTime.now().getHour();
        if (hora >= 5 && hora < 12) {
            turno = "Bom dia,";
        } else if (hora >= 12 && hora < 18) {
            turno = "Boa tarde,";
        } else {
            turno = "Boa noite,";
        }
        return turno;
    }

    /**
     * Este método é usado para identificar a data que o usuário se econtra enquanto usa o aplicativo (dia e mês). Usando
     * outros métodos de identificação de data e fuso horários dos dispositivos dos usuários, ele coleta o número do dia
     * e do mês e, dependendo do número que é coletado, ele processa a informação e o converte para uma String de texto
     * com o nome do mês respectivo do número que fora coletado. Exemplo: 1 - Fevereiro, 5 - Maio.
     *
     * Nota: o dia não há conversão de texto porque não existe nomes para cada um dos dias do mês, mas o número do dia é
     * coletado e convertido em String para se juntar ao do mês. Dessa forma poderá ter uma String de texto da seguinte
     * forma: 24 de Maio.
     *
     * @return String de texto com a data atual do usuário no formato dia e mês.
     */
    public String dataNow() {
        String mes = "";
        int mesInt = ZonedDateTime.now().getMonthValue();
        switch (mesInt) {
            case 1:
                mes = "janeiro";
                break;
            case 2:
                mes = "fevereiro";
                break;
            case 3:
                mes = "março";
                break;
            case 4:
                mes = "abril";
                break;
            case 5:
                mes = "maio";
                break;
            case 6:
                mes = "junho";
                break;
            case 7:
                mes = "julho";
                break;
            case 8:
                mes = "agosto";
                break;
            case 9:
                mes = "setembro";
                break;
            case 10:
                mes = "outubro";
                break;
            case 11:
                mes = "novembro";
                break;
            case 12:
                mes = "dezembro";
                break;
            default:
                mes = "voot";
                break;
        }

        int dia = ZonedDateTime.now().getDayOfMonth();
        String data = dia + " de " + mes;

        return data;
    }
}
