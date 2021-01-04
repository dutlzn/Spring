package com.demo.generic;

public interface GenericFactory<T,N> {
    T nextObject();
    N nextNumber();
}
