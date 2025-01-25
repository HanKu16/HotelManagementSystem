package org.po2_jmp.controller.helper.contract;

import org.po2_jmp.controller.helper.RequestValidationResult;

public interface RequestValidator {

    RequestValidationResult validate(String request);

}
