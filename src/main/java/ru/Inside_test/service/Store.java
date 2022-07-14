package ru.Inside_test.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public interface Store {

    default Object patch(CrudRepository repository, int id, Object object) throws InvocationTargetException, IllegalAccessException {
        var current = repository.findById(id);
        if (!current.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        var methods = current.get().getClass().getDeclaredMethods();
        var namePerMethod = new HashMap<String, Method>();
        for (var method: methods) {
            var name = method.getName();
            if ((name.startsWith("get") || name.startsWith("set"))
                    && (!name.contains("setId") && !name.contains("getId"))) {
                namePerMethod.put(name, method);
            }
        }
        for (var name : namePerMethod.keySet()) {
            if (name.startsWith("get")) {
                var getMethod = namePerMethod.get(name);
                var setMethod = namePerMethod.get(name.replace("get", "set"));
                if (setMethod == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid properties mapping");
                }
                var newValue = getMethod.invoke(object);
                if (newValue != null) {
                    setMethod.invoke(current.get(), newValue);
                }
            }
        }
        repository.save(current.get());
        return current.get();
    }
}
