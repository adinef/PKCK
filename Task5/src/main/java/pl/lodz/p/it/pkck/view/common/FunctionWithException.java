package pl.lodz.p.it.pkck.view.common;

@FunctionalInterface
public interface FunctionWithException<T, S> {
    S apply(T elem) throws Exception;
}
