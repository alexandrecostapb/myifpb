package br.com.myifpb;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.barteksc.pdfviewer.PDFView;

import java.util.List;

public class ActivityPDF extends AppCompatActivity {
    private PDFView pdfView;
    private ImageView imageVoltarPDF;
    private Button buttonDeletarPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pdf);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AppDatabase instance = AppDatabase.getInstance(getApplicationContext());
        List<Horario> list = instance.getHorarioDAO().list();

        imageVoltarPDF = findViewById(R.id.imageVoltarPDF);
        buttonDeletarPDF = findViewById(R.id.buttonDeletarPDF);

        imageVoltarPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        buttonDeletarPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ConstraintLayout constraintLayout = findViewById(R.id.constraintDialogDelete);
                    View viewDelete = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_delete_pdf, constraintLayout);

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPDF.this);
                    builder.setView(viewDelete);
                    final AlertDialog alertDialogDelete = builder.create();

                    Button buttonSIM = viewDelete.findViewById(R.id.buttonDialogSIM);
                    Button buttonNAO = viewDelete.findViewById(R.id.buttonDialogNAO);

                    buttonSIM.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                instance.getHorarioDAO().delete();
                                Toast.makeText(getApplicationContext(), "PDF deletado com sucesso!", Toast.LENGTH_SHORT).show();
                                finish();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Não foi possível deletar o PDF!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    buttonNAO.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                alertDialogDelete.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    if (alertDialogDelete.getWindow() != null) {
                        alertDialogDelete.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }

                    alertDialogDelete.show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Erro!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        try {
            Horario horario = list.get(0);
            pdfView = findViewById(R.id.pdfView);
            pdfView.fromBytes(horario.getImage()).load();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Não foi possível  arregar o PDF", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}