package co.edu.unbosque.horaclass.user.util;

public final class UserCorreoUtil {

    private UserCorreoUtil() {
    }

    public static String resolveCorreo(String username, String correo) {
        if (correo != null && !correo.isBlank()) {
            return correo.trim();
        }
        if (username != null && username.contains("@")) {
            return username.trim();
        }
        if (username != null && !username.isBlank()) {
            return username.trim().toLowerCase() + "@unbosque.edu.co";
        }
        return null;
    }
}
