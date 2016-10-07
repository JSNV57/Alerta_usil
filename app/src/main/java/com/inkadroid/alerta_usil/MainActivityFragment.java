package com.inkadroid.alerta_usil;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivityFragment extends Fragment implements View.OnClickListener {
    private TabHost tabHost;
    private final static String FILE = "alarmas.txt";
    private EditText phone_config,msm_config;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1;
    private static final int FOTO = 2;
    private static Bitmap photoBit;
    private static ImageView photo;
    private static byte[] byteArray;
    private TextToSpeech tts;
    public MainActivityFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main_activity, container, false);
        rellenar(view);
        tabHost = (TabHost) view.findViewById(R.id.tabHost);
        //CONFIGURAR Tabhost
        tabHost.setup();
        //PRIMERA PESTAÑA
        TabHost.TabSpec spec = tabHost.newTabSpec("Tab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Voz Alerta");
        tabHost.addTab(spec);
        ImageButton sosButton;
        sosButton = (ImageButton) view.findViewById(R.id.sosButton);
        sosButton.setOnClickListener(this);
        //SEGUNDA PESTAÑA
        TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab2");
        spec1.setContent(R.id.tab2);
        spec1.setIndicator("SMS Alerta");
        tabHost.addTab(spec1);
        //TERCERA PESTAÑA
        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab3");
        spec2.setContent(R.id.tab3);
        spec2.setIndicator("Foto Alerta");
        tabHost.addTab(spec2);
        //CUARTA PESTAÑA CONFIGURACION
        TabHost.TabSpec spec3 = tabHost.newTabSpec("Tab4");
        spec3.setContent(R.id.tab4);
        spec3.setIndicator("Configuracion");
        tabHost.addTab(spec3);
        Button save_config;
        save_config = (Button) view.findViewById(R.id.save_config);
        save_config.setOnClickListener(this);
        phone_config = (EditText) view.findViewById(R.id.phone_config);
        msm_config = (EditText) view.findViewById(R.id.msm_config);
        return view;
    }
    @Override
    public void onClick(View view) {
        EditText phone_config , msm_config;
        switch (view.getId())
        {   //CONFIGURACION - BOTON save_config (SALVAR LA CONFIGURACION)
            case R.id.save_config:
                cargar();
                tabHost.setCurrentTab(0);
                break;
            case R.id.sosButton:
                /////////
                tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            int result = tts.setLanguage(Locale.getDefault());
                            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Log.e("TTS", "This Language is not supported");
                            }
                            speak("Diga Auxilio");

                        } else {
                            Log.e("TTS", "Initilization Failed!");
                        }
                    }
                });
                /////////
                Reconocimiento();
                break;
        }
    }
    /////////////////////////////////SINTETIZADOR DE VOZ /////////////////////////////////////////////
    private void speak(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);

        }else{
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
    /////////////////////////////////SINTETIZADOR DE VOZ /////////////////////////////////////////////

/////////////////////////////////////////////////////VOZ ALERTA/////////////////////////////////////////////////////////
    //Metodos de reconocimiento de voz
    private void Reconocimiento() {
        // Definición del intent para realizar en análisis del mensaje
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // Indicamos el modelo de lenguaje para el intent
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Definimos el mensaje que aparecerá
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga, " + "auxilio");
        // Lanzamos la actividad esperando resultados
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }


        //Recogemos los resultados del reconocimiento de voz
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            //El intent nos envia un ArrayList aunque en este caso solo
            //utilizaremos la pos.0
            ArrayList<String> matches = data.getStringArrayListExtra
                    (RecognizerIntent.EXTRA_RESULTS);
            //Separo el texto en palabras.
            String[] palabras = matches.get(0).toString().split(" ");
            Toast.makeText(getActivity(),palabras[0],Toast.LENGTH_LONG).show();
            palabras[0] = "Auxilio";
            //Si la primera palabra es LLAMAR
            if (palabras[0].equals("Auxilio")) {
                String numero = phone_config.getText().toString().trim();
                String mensaje = msm_config.getText().toString().trim();
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(numero, null,mensaje, null, null);
            }
        }
        // si el resultado del codigo es 100
        if (resultCode == 100) {
            Intent intent = getActivity().getIntent();
            getActivity().finish();
            startActivity(intent);
        }


    }
/////////////////////////////////////////////////////VOZ ALERTA/////////////////////////////////////////////////////////



/////////////////////////////////////////////////////CONFIGURACION/////////////////////////////////////////////////////////
    private void cargar() {
        try {
            OutputStreamWriter outSWMensaje = new OutputStreamWriter(getActivity().openFileOutput(FILE, Context.MODE_PRIVATE));
            // Por cada tiempo escrito en los EditText escribimos una línea
            // en el archivo de alarmas.
            outSWMensaje.write(phone_config.getText().toString() + "\n" + msm_config.getText().toString() + "\n");
            outSWMensaje.close();
            Toast.makeText(getActivity(), "Se registro correctamente",
                    Toast.LENGTH_LONG).show();
            tabHost.setCurrentTab(0);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "No se pudo crear el archivo de alarmas " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
    private  void rellenar(View view) {
        EditText phone_config = (EditText) view.findViewById(R.id.phone_config);
        EditText msm_config = (EditText) view.findViewById(R.id.msm_config);
        try {
            // Creamos un objeto InputStreamReader, que será el que nos permita
            // leer el contenido del archivo de texto.
            InputStreamReader archivo = new InputStreamReader(
                    getActivity().openFileInput(FILE));
            // Creamos un objeto buffer, en el que iremos almacenando el contenido
            // del archivo.
            BufferedReader br = new BufferedReader(archivo);
            // Por cada EditText leemos una línea y escribimos el contenido en el
            // EditText.
            String texto = br.readLine();
            phone_config.setText(texto);
            texto = br.readLine();
            msm_config.setText(texto);
            // Cerramos el flujo de lectura del archivo.
            br.close();
        } catch (Exception e) {
            Toast.makeText(getActivity(),"ERROR",Toast.LENGTH_LONG).show();
        }
    }
/////////////////////////////////////////////////////CONFIGURACION/////////////////////////////////////////////////////////


}
