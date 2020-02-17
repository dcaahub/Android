package com.example.appatmconsultoria.ui.sobre;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appatmconsultoria.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

/**
 * A simple {@link Fragment} subclass.
 */
public class SobreFragment extends Fragment {


    public SobreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Element versao = new Element();
        versao.setTitle("Versão 1,0");

        String descricao = " Nostra risus sapien aenean a accumsan aliquam sodales, dapibus netus " +
                "tincidunt tellus quisque accumsan, aliquet vitae lacus platea bibendum sit.";

        View view = new AboutPage(getActivity())
                .setImage(R.drawable.logo)
                .setDescription(descricao)

                .addGroup("Entre em contato")
                .addEmail("atendimento@email.com.br", "Envie um email")
                .addWebsite("www.google.com", "Acesse nosso site")

                .addGroup("Redes Sociais")
                .addFacebook("Google", "Facebook")
                .addInstagram("Google", "Instagram")
                .addTwitter("Google", "Twitter")
                .addYoutube("Google", "Youtube")

                .addItem(versao)

                .create();

        return view;

        //return inflater.inflate(R.layout.fragment_sobre, container, false);
    }

}
