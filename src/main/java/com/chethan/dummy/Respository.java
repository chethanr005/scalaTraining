package com.chethan.dummy;

import java.util.List;

/**
 * Created by Chethan on Nov 04, 2022.
 */

public interface Respository<E> {
    E getById(int id);

    List<E> getAll();

    void insert(E obj);

    void update(E obj);

    void delete(int id);


}
