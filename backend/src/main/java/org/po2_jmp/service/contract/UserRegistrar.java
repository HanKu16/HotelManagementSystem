package org.po2_jmp.service.contract;

import org.po2_jmp.request.UserRegistrationRequest;
import org.po2_jmp.response.UserRegistrationResponse;

public interface UserRegistrar {

    UserRegistrationResponse register(UserRegistrationRequest request);

}
