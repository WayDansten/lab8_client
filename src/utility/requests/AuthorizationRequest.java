package utility.requests;

import utility.auxiliary.UserData;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthorizationRequest extends Request {
    private final UserData userData;
    private final boolean registrationFlag;
    public AuthorizationRequest(String login, String password, boolean flag) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-224");
            String pepper = "e>1H:x0@Zm";
            byte[] hash = md.digest((password + pepper).getBytes(StandardCharsets.UTF_8));
            password = String.format("%032x", new BigInteger(1, hash));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Несуществующий алгоритм хеширования");
        }
        userData = new UserData(login, password);
        this.registrationFlag = flag;
    }
    @Override
    public String[] extract() {
        return new String[]{userData.login(), userData.password()};
    }

    public boolean getRegistrationFlag() {
        return registrationFlag;
    }

    public UserData getUserData() {
        return userData;
    }
}
