package com.inkadroid.alerta_usil;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.google.android.gms.maps.GoogleMap;

import java.util.Arrays;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private UiLifecycleHelper uiHelper;
    //SE LLAMA CUANDO SE REALIZA CAMBIOS DE SESION
    private Session.StatusCallback statusCallback = new SessionStatusCallback();

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState sessionState, Exception e) {
        }
    }
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState sessionState, Exception e) {
            //OnSessionStateChange
            onSessionStateChange(session, sessionState, e);
        }
    };
    //SE LLAMA CUANDO SE REALIZA CAMBIOS DE SESION

    /*
    private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this)
                    .setPermissions(Arrays.asList("public_profile"))
                    .setCallback(statusCallback));

        }
    }
*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }

    //VERIFICACION DE CREDENCIALES DE FACEBOOK , EXITO : LLAMAR A LA ACTIVIDAD MainActivity

    private void onSessionStateChange(Session session, SessionState state, Exception e) {
        if (state.isOpened()) {
            Toast.makeText(getContext(),"CREDENCIALES CORRECTAS",Toast.LENGTH_LONG).show();
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);
            getActivity().finish();
        }
    }

    //VERIFICACION DE CREDENCIALES DE FACEBOOK , EXITO : LLAMAR A LA ACTIVIDAD MainActivity

    //CONSTRUCTOR DE LA ACTIVIDAD : MainActivity

    public LoginFragment() {
        // Required empty public constructor
    }
    //CONSTRUCTOR DE LA ACTIVIDAD : MainActivity


    //CONSTRUCTOR DEL FRAGMENTO
    /*
    Durante el inicio de sesión básico, la aplicación recibe acceso al perfil público y la lista de amigos de una persona.
    Para acceder a información adicional del perfil o publicar contenido en Facebook en nombre de esa persona debes solicitar
    los permisos necesarios:
    Aquí se obtiene user_status. Podemos pasar estos permisos al botón LoginButton o a una interfaz personalizada
     para el inicio de sesión y la administración de permisos.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
        authButton.setFragment(this);
        authButton.setReadPermissions(Arrays.asList("public_profile"));
        return view;
    }
    //CONSTRUCTOR DEL FRAGMENTO



    @Override
    public void onResume() {
        super.onResume();
        Session session = Session.getActiveSession();
        if (session != null && (session.isClosed() || session.isClosed())) {
            onSessionStateChange(session, session.getState(), null);
        }
        uiHelper.onResume();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }


}

