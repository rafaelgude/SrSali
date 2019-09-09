package br.com.srsali.srsali.utils;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DaoUtils {

    public static <T> T find(final CrudRepository<T, Long> repository, final long id, final String mensagem) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, mensagem));
    }
    
}
