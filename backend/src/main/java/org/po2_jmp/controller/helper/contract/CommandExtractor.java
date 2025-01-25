package org.po2_jmp.controller.helper.contract;

import java.util.Optional;

public interface CommandExtractor {

    Optional<String> extract(String request);

}
