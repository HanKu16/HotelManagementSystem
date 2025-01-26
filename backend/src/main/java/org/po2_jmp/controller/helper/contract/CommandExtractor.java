package org.po2_jmp.controller.helper.contract;

import java.util.Optional;

/**
 * Interface for extracting a command from a given request.
 */
public interface CommandExtractor {

    /**
     * Attempts to extract a command from the provided request string.
     * @param request the input request string, which should contain a command
     * @return an {@link Optional} containing the extracted command if found,
     *         or an empty {@link Optional} if no command could be extracted
     */
    Optional<String> extract(String request);

}
