package br.com.myifpb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Methods methods = new Methods();
    private ImageSlider imageSlider;
    private TextView textViewData, textTurno;
    private ImageView suapImageView, moodleImageView, bibliotecaImageView, repositorioImageView,
            portalEstudanteImageView, eventosImageView, imagePeriodicos, imageEditora, imageSaber, dialogImageIllustration, dialogImageHorario,
            dialogHorarioDismiss;
    private Uri uri;
    private Button horarioButton, buscarHorarioButton, buscarHorarioButton2, adicionarHorarioButton, buttonSobre;
    private String fotoEmString = "";
    private TextView dialogText2;
    private TextView dialogSubtext1;
    private byte[] fotoEmByte = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AppDatabase instance = AppDatabase.getInstance(getApplicationContext());

        textViewData = findViewById(R.id.textData);
        textViewData.setText(methods.dataNow());

        textTurno = findViewById(R.id.textTurno);
        textTurno.setText(methods.turno());

        imageSlider = findViewById(R.id.imageSliderNotices);
        imageSlider.setImageList(methods.slideModels(), ScaleTypes.FIT);

        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                String url = null;
                if (i == 0) {
                    url = "https://www.ifpb.edu.br/";
                } else if (i == 1) {
                    url = "https://estudante.ifpb.edu.br/";
                } else if (i == 2) {
                    url = "https://estudante.ifpb.edu.br/processoseletivo/";
                } else {
                    Toast.makeText(getApplicationContext(), "Opção inválida", Toast.LENGTH_SHORT).show();
                }
                // iniciarWebViewActivity(url);
                abrirUrl(url);
            }
        });

        suapImageView = findViewById(R.id.imageSuap);
        moodleImageView = findViewById(R.id.imageMoodle);
        bibliotecaImageView = findViewById(R.id.imageBiblioteca);
        repositorioImageView = findViewById(R.id.imageRepositorio);
        portalEstudanteImageView = findViewById(R.id.imagePortalEstudante);
        eventosImageView = findViewById(R.id.imageEventos);
        imagePeriodicos = findViewById(R.id.imagePeriodicos);
        imageEditora = findViewById(R.id.imageEditora);
        imageSaber = findViewById(R.id.imageSaber);

        buttonSobre = findViewById(R.id.buttonSobre);
        buttonSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(getApplicationContext(), ActivitySobre.class));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Não foi possível abrir a tela de 'Sobre'!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        horarioButton = findViewById(R.id.buttonHorario);
        horarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Horario> listaDeHorarios = instance.getHorarioDAO().list();
                    if (listaDeHorarios.isEmpty()) {
                        ConstraintLayout constraintLayout = findViewById(R.id.constraintDialog);
                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_horario, constraintLayout);

                        adicionarHorarioButton = view.findViewById(R.id.dialogButtonAdicionarHorario);
                        adicionarHorarioButton.setVisibility(View.INVISIBLE);
                        adicionarHorarioButton.setEnabled(false);

                        buscarHorarioButton2 = view.findViewById(R.id.dialogButtonBuscarHorario2);
                        buscarHorarioButton2.setVisibility(View.INVISIBLE);
                        buscarHorarioButton2.setEnabled(false);

                        dialogImageHorario = view.findViewById(R.id.dialogImageHorario);
                        dialogImageHorario.setEnabled(false);
                        dialogImageHorario.setVisibility(View.INVISIBLE);

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setView(view);
                        final AlertDialog alertDialogHorario = builder.create();

                        buscarHorarioButton = view.findViewById(R.id.dialogButtonBuscarHorario);

                        dialogHorarioDismiss = view.findViewById(R.id.dialogHorarioDissmis);
                        dialogImageIllustration = view.findViewById(R.id.dialogImageIllustration);
                        dialogText2 = view.findViewById(R.id.dialogText2);
                        dialogSubtext1 = view.findViewById(R.id.dialogSubtext1);


                        dialogHorarioDismiss.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    alertDialogHorario.dismiss();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    finish();
                                }
                            }
                        });

                        adicionarHorarioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    Horario horario = new Horario(fotoEmByte);
                                    instance.getHorarioDAO().insert(horario);
                                    alertDialogHorario.dismiss();
                                    Toast.makeText(view.getContext(), "Documento salvo com sucesso!", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(view.getContext(), "Não foi possível salvar este documento!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        buscarHorarioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                                    intent.setType("application/pdf");
                                    startActivityForResult(Intent.createChooser(intent, "Select PDF"), 2);
                                } catch (Exception e) {
                                    Toast.makeText(view.getContext(), "Não foi possível acessar a galeria", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        buscarHorarioButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                                    intent.setType("application/pdf");
                                    startActivityForResult(Intent.createChooser(intent, "Select PDF"), 2);
                                } catch (Exception e) {
                                    Toast.makeText(view.getContext(), "Não foi possível acessar a galeria", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        if (alertDialogHorario.getWindow() != null) {
                            alertDialogHorario.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        }

                        alertDialogHorario.show();
                    } else {
                        try {
                            startActivity(new Intent(MainActivity.this, ActivityPDF.class));
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Erro ao abrir a tela do PDF!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Não foi possível abrir a tela dos Horários!", Toast.LENGTH_SHORT).show();
                    instance.getHorarioDAO().delete();
                }
            }
        });

        suapImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://suap.ifpb.edu.br/accounts/login/?next=/";
                // iniciarWebViewActivity(url);
                abrirUrl(url);
            }
        });
        moodleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://presencial.ifpb.edu.br/login/index.php";
                // iniciarWebViewActivity(url);
                abrirUrl(url);
            }
        });
        bibliotecaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://biblioteca.ifpb.edu.br/";
                // iniciarWebViewActivity(url);
                abrirUrl(url);
            }
        });
        portalEstudanteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://estudante.ifpb.edu.br/";
                // iniciarWebViewActivity(url);
                abrirUrl(url);
            }
        });
        repositorioImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://repositorio.ifpb.edu.br/";
                // iniciarWebViewActivity(url);
                abrirUrl(url);
            }
        });
        eventosImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://eventos.ifpb.edu.br/";
                //iniciarWebViewActivity(url);
                abrirUrl(url);
            }
        });
        imagePeriodicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://periodicos.ifpb.edu.br/";
                // iniciarWebViewActivity(url);
                abrirUrl(url);
            }
        });

        imageEditora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://editora.ifpb.edu.br/";
                // iniciarWebViewActivity(url);
                abrirUrl(url);
            }
        });

        imageSaber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://suap.ifpb.edu.br/bi/";
                //iniciarWebViewActivity(url);
                abrirUrl(url);
            }
        });
    }

    public void iniciarWebViewActivity(String url) {
        Intent intent = new Intent(getApplicationContext(), WebViewMainActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    public void abrirUrl(String url) {
        uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 2 && resultCode == MainActivity.RESULT_OK && resultData != null) {
            try {
                Uri uri = resultData.getData();
                if (uri != null) {
                    try {
                        ParcelFileDescriptor fileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
                        if (fileDescriptor != null) {
                            renderPdf(fileDescriptor);
                            fotoEmByte = convertUriToByteArray(uri);
                        }
                    } catch (IOException e) {
                        Log.e("PDF", "Erro ao abrir o arquivo PDF", e);
                    }
                }
                dialogSubtext1.setVisibility(View.INVISIBLE);
                dialogText2.setText("Deseja salvar este documento?");

                buscarHorarioButton.setEnabled(false);
                buscarHorarioButton.setVisibility(View.INVISIBLE);

                buscarHorarioButton2.setVisibility(View.VISIBLE);
                buscarHorarioButton2.setEnabled(true);

                adicionarHorarioButton.setEnabled(true);
                adicionarHorarioButton.setVisibility(View.VISIBLE);

                dialogImageIllustration.setVisibility(View.INVISIBLE);
                dialogImageIllustration.setEnabled(false);

                dialogImageHorario.setVisibility(View.VISIBLE);
                dialogImageHorario.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Imagem recuperada da galeria!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Nenhuma imagem selecionada!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void renderPdf(ParcelFileDescriptor fileDescriptor) {
        try {
            PdfRenderer pdfRenderer = new PdfRenderer(fileDescriptor);
            PdfRenderer.Page page = pdfRenderer.openPage(0);

            int width = page.getWidth();
            int height = page.getHeight();

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(getColor(R.color.white));
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

            dialogImageHorario.setImageBitmap(bitmap);

            byte[] fotoEmBytes;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            fotoEmBytes = byteArrayOutputStream.toByteArray();
            fotoEmString = Base64.getEncoder().encodeToString(fotoEmBytes);

            page.close();
            pdfRenderer.close();

        } catch (IOException e) {
            Log.e("PDF", "Erro ao renderizar PDF", e);
        }
    }

    private byte[] convertUriToByteArray(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, length);
        }

        return byteBuffer.toByteArray();
    }
}