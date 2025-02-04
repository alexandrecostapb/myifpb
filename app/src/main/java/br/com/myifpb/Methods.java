package br.com.myifpb;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Methods {
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

        Acessos acesso6 = new Acessos("REPORSITÓRIO DIGITAL", "https://repositorio.ifpb.edu.br/");
        acessosArrayList.add(acesso6);

        Acessos acesso11 = new Acessos("EVENTOS", "https://eventos.ifpb.edu.br/");
        acessosArrayList.add(acesso11);

        return acessosArrayList;
    }

    public ArrayList<SlideModel> slideModels() {
        ArrayList<SlideModel> modelArray = new ArrayList<>();

        modelArray.add(new SlideModel(R.drawable.banner_portal_ifpb, ScaleTypes.FIT));
        modelArray.add(new SlideModel(R.drawable.banner_portal_do_estudante, ScaleTypes.FIT));
        modelArray.add(new SlideModel(R.drawable.banner_processos_seletivos, ScaleTypes.FIT));

        return modelArray;
    }

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
