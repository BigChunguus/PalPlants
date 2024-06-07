package com.example.palplants.Utility;

import pojosbotanica.Usuario;

public interface VerifyUserLoginCallback {
    // Método para informar sobre el resultado de la verificación de inicio de sesión de usuario
    void onVerifyUserLoginResult(Usuario usuario);
}
