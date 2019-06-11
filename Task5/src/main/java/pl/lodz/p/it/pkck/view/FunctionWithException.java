package pl.lodz.p.it.pkck.view;

@FunctionalInterface
public interface FunctionWithException<S, T> {
    T apply(S elem) throws Exception;
}
