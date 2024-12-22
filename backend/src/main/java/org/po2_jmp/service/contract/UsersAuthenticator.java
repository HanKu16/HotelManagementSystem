package org.po2_jmp.service.contract;

import org.po2_jmp.request.UserAuthenticationRequest;
import org.po2_jmp.response.UserAuthenticationResponse;

public interface UsersAuthenticator {

    UserAuthenticationResponse authenticate(UserAuthenticationRequest request);

}
