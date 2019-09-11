package br.com.srsali.srsali.utils;

import org.springframework.data.repository.CrudRepository;

import br.com.srsali.srsali.exceptions.ObjectNotFoundException;

public class DaoUtils {

    public static <T> T find(final CrudRepository<T, Integer> repository, final int id, final String mensagem) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(mensagem));
    }
    
}
